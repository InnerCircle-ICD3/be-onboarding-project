# 설문조사 서비스

---

## 프로젝트 구조

- 멀티모듈 구조 사용

```
hjpark-onboarding/
├── support/
│   ├── shared-common/ (공통 모듈)
│   └── logging/ (로깅 모듈)
└── survey/
    ├── data/ (데이터 접근 모듈)
    ├── application/ (애플리케이션 모듈)
    └── api/ (API 모듈)
```

---

### 1. support 모듈

#### **shared-common**
- **역할과 책임**
  - 공통으로 사용되는 DTO 정의
  - 예외 처리와 응답 형식 정의
  - API 계층과 Application 계층 간의 데이터 전달 객체 정의

#### **logging**
- **역할과 책임**
  - 로깅 관련 설정과 유틸리티 제공
  - 로그 포맷팅 및 필터링
  - 로그 레벨 관리
  - 로그 파일 관리

---

### 2. survey 모듈

#### **data**
- **역할과 책임**
  - 데이터베이스와의 상호작용을 담당
  - JPA 엔티티와 리포지토리 정의
  - 데이터 접근 로직 캡슐화
  - 트랜잭션 관리

#### **application**
- **역할과 책임**
  - 비즈니스 로직 구현
  - 데이터 접근 계층을 활용한 비즈니스 요구사항 구현
  - 트랜잭션 처리
  - 도메인 로직 실행

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
shared-common -> api -> application -> data
```

각 모듈 간의 의존성 규칙:
- api: shared-common, logging, application
- application: shared-common, data
- data: 의존성 없음

---
## ERD
<img src="./erd/설문조사 시스템 ERD.png" width="600"> <img src="./erd/설문조사 시스템 ERD 상세.png" width="800">

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

## 추가 구현 사항

### 설문 목록 조회 기능
- 설문 제목을 키워드로 검색하여 설문 목록을 조회하는 기능 구현
- 키워드가 없는 경우 전체 설문 목록 반환
- 검색 결과는 설문 생성일 기준 내림차순으로 정렬
- 각 설문의 간략한 정보(제목, 설명, 생성일 등)만 포함하여 반환
- ```kotlin
  data class SurveyDto(
    val id: Long,
    val name: String,
    val description: String?,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
    val questionCount: Int
  )
  ```

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