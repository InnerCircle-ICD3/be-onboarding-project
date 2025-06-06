package onboarding.survey.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["onboarding.survey"])
class OnboardingApplication

fun main(args: Array<String>) {
    runApplication<OnboardingApplication>(*args)
}
