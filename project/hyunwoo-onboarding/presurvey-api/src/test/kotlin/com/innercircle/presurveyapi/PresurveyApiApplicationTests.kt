package com.innercircle.presurveyapi

import com.innercircle.presurveyapi.common.logger
import org.junit.jupiter.api.Test
import org.slf4j.MDC
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PresurveyApiApplicationTests {

	private val log = logger()

	/*
	MdcFilter는 Servlet Filter, HTTP 요청이 들어올 때만 작동하므로,
	test 확인을 위한 mdc 수동 테스트
	 */
	@Test
	fun `로깅 수동 테스트`() {
		MDC.put("traceId", "test-trace-id")
		MDC.put("method", "TEST")
		MDC.put("uri", "/test-endpoint")

		log.info("🧪 테스트에서 찍는 로그입니다.")

		MDC.clear()
	}

}
