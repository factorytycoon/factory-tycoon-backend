# Factory Tycoon Backend

공장 관리 시뮬레이션의 백엔드 API 서버

## 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.5.8 |
| Database | MariaDB 10.11, MongoDB 8.0 |
| Cache | Redis 7.4 |
| ORM | Spring Data JPA |
| Migration | Flyway |
| Auth | JWT (jjwt 0.11.5) |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Mail | Spring Boot Mail + Thymeleaf |
| Build | Gradle |
| Container | Docker |

## 주요 기능

| 기능 | 설명 |
|------|------|
| 🔐 사용자 인증 | JWT 기반 인증/인가, 이메일 인증 |
| 🏭 공장 관리 | 공장 생성, 조회, 상태 관리 |
| ⚙️ 설비 관리 | 설비 등록 및 모니터링 |
| 📋 작업 지시 | 작업 주문(Work Order) 생성 및 추적 |
| 📦 재고 관리 | 원자재 및 생산품 재고 관리 |
| 📊 센서 데이터 | IoT 센서 데이터 수집 및 분석 |
| 🔔 알람 | 공장 이벤트 알림 |
| 📈 예측 분석 | 생산량 및 수요 예측 |
| 📅 스케줄 관리 | 생산 일정 관리 |
| 🛒 주문 관리 | 고객 주문 처리 |

## 시작하기

### 사전 요구사항

- Java 17+
- Docker & Docker Compose
- Gradle (또는 Gradle Wrapper 사용)

### 환경 변수 설정

프로젝트 루트에 `.env` 파일을 생성하고 다음 환경 변수를 설정하세요:

```properties
# Database
MARIADB_URL=jdbc:mysql://localhost:3306/factory-tycoon
MARIADB_USERNAME=user
MARIADB_PASSWORD=1234

# Redis
REDIS_HOST=localhost
REDIS_PORT=6379

# JWT
JWT_SECRET_KEY=your-secret-key-here
JWT_ACCESS_TTL_MS=3600000
JWT_REFRESH_TTL_MS=86400000

# Mail (선택)
SPRING_MAIL_HOST=smtp.gmail.com
SPRING_MAIL_USERNAME=your-email@gmail.com
SPRING_MAIL_PASSWORD=your-app-password
```

### 로컬 실행

```bash
# 1. 로컬 데이터베이스 실행 (MariaDB, MongoDB, Redis)
cd docker/local
docker compose up -d

# 2. 애플리케이션 빌드 및 실행
cd ../..
./gradlew bootRun
```

### Docker 빌드 및 실행

```bash
# Docker 이미지 빌드
docker build -t factory-tycoon-backend .

# 컨테이너 실행
docker run -p 8080:8080 \
  -e MARIADB_URL=jdbc:mysql://host.docker.internal:3306/factory-tycoon \
  -e MARIADB_USERNAME=user \
  -e MARIADB_PASSWORD=1234 \
  -e REDIS_HOST=host.docker.internal \
  -e REDIS_PORT=6379 \
  -e JWT_SECRET_KEY=your-secret-key \
  -e JWT_ACCESS_TTL_MS=3600000 \
  -e JWT_REFRESH_TTL_MS=86400000 \
  factory-tycoon-backend
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
├── auth/           # 인증/인가 (JWT)
├── config/         # 공통 설정
├── email/          # 이메일 서비스
├── equipment/      # 설비 관리
├── factory/        # 공장 관리
├── factorystatus/  # 공장 상태 관리
├── inventory/      # 재고 관리
├── order/          # 주문 관리
├── prediction/     # 예측 분석
├── schedule/       # 스케줄 관리
├── sensor/         # 센서 관리
├── sensoranalysis/ # 센서 데이터 분석
├── sensordata/     # 센서 데이터
├── user/           # 사용자 관리
└── workorder/      # 작업 지시
```

## 데이터베이스 스키마

Flyway를 사용한 마이그레이션으로 관리됩니다.

| 테이블 | 설명 |
|--------|------|
| factory | 공장 정보 |
| factory_status | 공장 상태 |
| equipment | 설비 정보 |
| sensor | 센서 정보 |
| sensor_data | 센서 수집 데이터 |
| sensor_analysis | 센서 분석 결과 |
| alarm | 알람 이벤트 |
| orders | 고객 주문 |
| workorder | 작업 지시 |
| schedule | 생산 스케줄 |
| user | 사용자 정보 |
| user_status | 사용자 상태 |
| prediction | 예측 데이터 |
| inventory | 재고 정보 |
| emailauth | 이메일 인증 |

## 인프라 구성 (Docker Compose)

`docker/local/docker-compose.yml`에서 다음 서비스들을 제공합니다:

| 서비스 | 포트 | 설명 |
|--------|------|------|
| MariaDB | 3306 | 관계형 데이터베이스 |
| MongoDB | 27017 | NoSQL 데이터베이스 |
| Redis | 6379 | 캐시 및 세션 저장소 |
| mariadb-backup | - | MariaDB 자동 백업 |
| mongodb-backup | - | MongoDB 자동 백업 |
| redis-backup | - | Redis 자동 백업 |

### 백업 설정

기본적으로 1시간 간격으로 자동 백업됩니다. 백업 간격을 변경하려면:

```bash
BACKUP_INTERVAL_SECONDS=1800 docker compose up -d
```

백업 파일 위치:
- MariaDB: `docker/local/mariadb/backups/`
- MongoDB: `docker/local/mongodb/backups/`
- Redis: `docker/local/redis/backups/`

## 테스트

```bash
# 전체 테스트 실행
./gradlew test

# 테스트 리포트 확인
open build/reports/tests/test/index.html
```

## 라이선스

이 프로젝트는 LG CNS 내부용입니다.
