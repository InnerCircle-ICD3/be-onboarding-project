## Command

Run :
./gradlew clean build --refresh-dependencies --no-daemon
./gradlew :app:bootRun

Test :
./gradlew clean test

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
