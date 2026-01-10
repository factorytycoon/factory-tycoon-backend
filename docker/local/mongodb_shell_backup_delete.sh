#!/bin/bash

# MongoDB 백업 정리 스크립트
# 사용법: sudo ./cleanup_mongo_backups.sh [보관일수]

# 권한 확인
if [ "$EUID" -ne 0 ]; then
    echo "이 스크립트는 sudo 권한이 필요합니다."
    echo "사용법: sudo $0 [보관일수]"
    exit 1
fi

# 설정 변수
BACKUP_DIR="/mnt/cathdd_backups"
DEFAULT_KEEP_DAYS=7  # 기본 7일 보관
LOG_FILE="/home/spc/mongo_backup_cleanup.log"

# 인자가 있으면 사용, 없으면 기본값 사용
KEEP_DAYS=${1:-$DEFAULT_KEEP_DAYS}

# 로그 함수
log_message() {
    echo "$(date '+%Y-%m-%d %H:%M:%S') - $1" | tee -a "$LOG_FILE"
}

# 스크립트 시작
log_message "=== MongoDB 백업 정리 시작 ==="
log_message "백업 디렉토리: $BACKUP_DIR"
log_message "보관 기간: ${KEEP_DAYS}일"

# 백업 디렉토리 존재 확인
if [ ! -d "$BACKUP_DIR" ]; then
    log_message "오류: 백업 디렉토리가 존재하지 않습니다: $BACKUP_DIR"
    exit 1
fi

# 정리 전 상태 확인
BEFORE_COUNT=$(find "$BACKUP_DIR" -maxdepth 1 -type d -name "dump-*" | wc -l)
BEFORE_SIZE=$(du -sh "$BACKUP_DIR" | cut -f1)
log_message "정리 전: ${BEFORE_COUNT}개 백업, 총 크기: $BEFORE_SIZE"

# 오래된 백업 찾기 및 삭제
DELETED_COUNT=0
while IFS= read -r -d '' backup_dir; do
    if [ -d "$backup_dir" ]; then
        backup_name=$(basename "$backup_dir")
        log_message "삭제 시도: $backup_name"
        
        # 강제 삭제를 위해 chmod로 권한 변경 후 삭제
        if chmod -R 755 "$backup_dir" 2>/dev/null && rm -rf "$backup_dir" 2>/dev/null; then
            log_message "✅ 삭제 완료: $backup_name"
            ((DELETED_COUNT++))
        else
            log_message "❌ 삭제 실패: $backup_name"
        fi
    fi
done < <(find "$BACKUP_DIR" -maxdepth 1 -type d -name "dump-*" -mtime +$KEEP_DAYS -print0)

# 정리 후 상태 확인
AFTER_COUNT=$(find "$BACKUP_DIR" -maxdepth 1 -type d -name "dump-*" | wc -l)
AFTER_SIZE=$(du -sh "$BACKUP_DIR" | cut -f1)
log_message "정리 후: ${AFTER_COUNT}개 백업, 총 크기: $AFTER_SIZE"
log_message "삭제된 백업 수: $DELETED_COUNT"

# 요약 정보
if [ $DELETED_COUNT -gt 0 ]; then
    log_message "✅ 백업 정리 완료: ${DELETED_COUNT}개 삭제됨"
else
    log_message "ℹ️  삭제할 오래된 백업이 없습니다"
fi

log_message "=== MongoDB 백업 정리 완료 ==="