# 설문조사 서비스

---

## 프로젝트 구조

- 멀티모듈 구조 사용

```
hjpark-onboarding/
├── support/
│   ├── shared-common/ (공통 모듈)
│   └── logging/ (로깅 모듈) //사용 미정
└── survey/
    ├── domain/ (도메인 모듈)
    ├── data/ (데이터 접근 모듈)
    ├── application/ (애플리케이션 모듈)
    └── api/ (API 모듈)
```

---

### 1. support 모듈

#### **shared-common**
- **역할과 책임**
  - 공통으로 사용되는 예외 처리와 응답 형식을 정의
  - 외부 의존성 최소화

#### **logging**
- **역할과 책임**
  - 로깅 관련 설정과 유틸리티 제공

---

### 2. survey 모듈

#### **domain**
- **역할과 책임**
  - 비즈니스 로직 포함
  - 외부 의존성이 없음 (Spring, JPA 등에 의존하지 않음)

#### **data**
- **역할과 책임**
  - 데이터베이스와의 상호작용을 담당
  - JPA 엔티티와 리포지토리 정의
  - 도메인 모듈에만 의존하여 데이터 접근 로직 캡슐화

#### **application**
- **역할과 책임**
  - 유스케이스 구현을 담당
  - 도메인 로직과 데이터 접근 계층을 조합하여 비즈니스 요구사항 구현
  - 트랜잭션 처리

#### **api**
- **역할과 책임**
  - HTTP 엔드포인트 정의
  - 클라이언트 요청 처리 및 응답 생성
  - 애플리케이션의 진입점 역할
  - API 문서화 (Swagger 등)
  - shared-common, application 모듈 의존

---

## 의존성 구조

- 모듈 간 의존성

```
shared-common -> api -> application -> domain <- data
```

각 모듈의 의존성 규칙:
- shared-common: 의존성 없음
- api: shared-common, application
- application: domain
- domain: 의존성 없음
- data: domain

---

## 기술 스택

| 기술       | 버전     | 사용 목적                     |
|------------|--------|-------------------------------|
| Kotlin     | 1.9.25 | 언어                         |
| Spring Boot| 3.4.4  | 애플리케이션 프레임워크       |
| Spring Data JPA | -      | ORM 및 데이터베이스 액세스 |
| H2 Database | -      | 인메모리 RDBMS               |
| Gradle     | 8.13   | 빌드 도구                    |

---

## 빌드 및 실행 방법

### 빌드
```bash
./gradlew clean build
```

### 실행
```bash
./gradlew :survey:api:bootRun
```

### 테스트
```bash
./gradlew test
```

---