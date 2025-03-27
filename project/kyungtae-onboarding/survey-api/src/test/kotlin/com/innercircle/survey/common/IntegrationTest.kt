package com.innercircle.survey.common

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.junit.jupiter.api.extension.ExtendWith

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ExtendWith(SpringExtension::class, RestAssuredInitializer::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
annotation class IntegrationTest(
){

    // RestAssured를 주입받고 랜덤포트를 initPort에 할당
    // RestAssured를 사용하여 테스트를 진행할 때 사용할 수 있도록 설정



}
