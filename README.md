# Factory Tycoon Backend

공장 관리 시뮬레이션의 백엔드 API 서버

## 기술 스택

- Java 17
- Spring Boot 3.5.8
- MariaDB (JPA + Flyway)
- Redis
- Docker

## 주요 기능

- **사용자 인증**: JWT 기반 인증/인가
- **공장 관리**: 공장 생성, 조회, 관리
- **설비 관리**: 설비 등록 및 모니터링
- **작업 지시**: 작업 주문 생성 및 추적
- **재고 관리**: 원자재 및 생산품 재고 관리
- **센서 데이터**: IoT 센서 데이터 수집 및 분석
- **알람**: 공장 이벤트 알림
- **예측 분석**: 생산량 및 수요 예측

## 실행 방법

### 로컬 실행

```bash
# 환경 변수 설정 (.env 파일 생성)
# MARIADB_URL, MARIADB_USERNAME, MARIADB_PASSWORD
# REDIS_HOST, REDIS_PORT
# JWT_SECRET_KEY, JWT_ACCESS_TTL_MS, JWT_REFRESH_TTL_MS

# 로컬 DB 구성
$ cd ./docker/local
$ docker compose up -d

# 빌드 및 실행
$ ./gradlew bootRun
```

## API 문서

서버 실행 후 Swagger UI에서 확인:
```
http://localhost:8080/swagger-ui.html
```

## 프로젝트 구조

```
src/main/java/com/factory/tycoon/
├── alarm/          # 알람 관리
├── auth/           # 인증/인가
├── factory/        # 공장 관리
├── equipment/      # 설비 관리
├── workorder/      # 작업 지시
├── inventory/      # 재고 관리
├── order/          # 주문 관리
├── sensor/         # 센서 관리
├── sensordata/     # 센서 데이터
├── sensoranalysis/ # 센서 데이터 분석
├── prediction/     # 예측 분석
└── global/         # 공통 설정
```
