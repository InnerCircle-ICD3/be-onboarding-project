# ✏️ 설문조사 서비스 (Survey Service)

온보딩 프로젝트 요구사항 기반으로 설문 양식 생성, 수정, 응답 제출 및 조회 기능을 제공하는 멀티모듈 기반 백엔드 서비스입니다.

## 📦 기능 요약

### 1. 설문조사 생성 API ✅

- 설문 제목, 설명, 질문 리스트를 포함한 설문 생성
- 단/장문형, 단일/다중 선택지 지원
- 질문 수는 1개 이상 10개 이하

### 2. 설문조사 수정 API ✅

- 기존 설문 항목을 수정, 추가, 삭제 가능
- 기존 응답은 보존됨

### 3. 설문 응답 제출 API ✅

- 설문 항목 ID 기반으로 응답 제출
- 응답 형식 및 옵션 유효성 검증 포함

### 4. 설문 응답 조회 API ✅

- 설문 ID 기반 전체 응답 조회
- (Advanced) 특정 질문/응답 기준으로 필터링 가능

## Project Structure

project-root/
├── settings.gradle.kts
├── build.gradle.kts
│
├── api/ # Web Layer(전역예외처리 / Controller)
│ ├── build.gradle.kts
│ └── src/main/kotlin/com/example/
│ ├── ApiApplication.kt
│ ├── controller/
│ │ ├── SurveyController.kt
│ │ └── AnswerController.kt
│ └── exception/
│ ├── GlobalExceptionHandler.kt
│ ├── ErrorResponse.kt
│ └── CustomException.kt
│
├── app/ # Domain Layer(Dto, Entity, Repository, Service, Test)
│ ├── build.gradle.kts
│ └── src/main/kotlin/com/example/
│ ├── dto/
│ │ ├── CreateSurveyRequest.kt
│ │ ├── SurveyDetailResponse.kt
│ │ ├── AnswerRequest.kt
│ │ └── ...
│ │
│ ├── entity/
│ │ ├── Survey.kt
│ │ ├── SurveyItem.kt
│ │ ├── Answer.kt
│ │ └── SelectionOption.kt
│ │
│ ├── repository/
│ │ ├── SurveyRepository.kt
│ │ ├── AnswerRepository.kt
│ │ └── ...
│ │
│ └── service/
│ ├── SurveyService.kt
│ ├── AnswerService.kt
│ └── ...
└──

## 🧪 테스트

- 전체 테스트 수: ✅ **24개**
- 모든 기능 단위 테스트 커버 완료
- TDD 기반으로 설계

## Command

Run :
./gradlew clean build --refresh-dependencies --no-daemon
./gradlew :app:bootRun

Test :
./gradlew clean test
