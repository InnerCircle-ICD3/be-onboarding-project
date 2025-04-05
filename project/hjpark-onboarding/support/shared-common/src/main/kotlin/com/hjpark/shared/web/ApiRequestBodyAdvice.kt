//package com.hjpark.shared.web
//
//import com.hjpark.shared.request.ApiRequest
//import org.springframework.core.annotation.AnnotationUtils
//import org.springframework.http.HttpInputMessage
//import org.springframework.http.converter.HttpMessageConverter
//import org.springframework.stereotype.Component
//import org.springframework.web.bind.annotation.ControllerAdvice
//import org.springframework.web.bind.annotation.RestController
//import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter
//import java.lang.reflect.Method
//import java.lang.reflect.Type
//import kotlin.reflect.KFunction
//import kotlin.reflect.full.declaredFunctions
//import kotlin.reflect.jvm.javaMethod
//
///**
// * API 요청 본문에서 service와 action 값을 자동으로 채워주는 RequestBodyAdvice 구현체
// */
//@ControllerAdvice
//@Component
//class ApiRequestBodyAdvice : RequestBodyAdviceAdapter() {
//
//    override fun supports(
//        methodParameter: org.springframework.core.MethodParameter,
//        targetType: Type,
//        converterType: Class<out HttpMessageConverter<*>>
//    ): Boolean = targetType.typeName.contains("ApiRequest")
//
//    override fun afterBodyRead(
//        body: Any,
//        inputMessage: HttpInputMessage,
//        parameter: org.springframework.core.MethodParameter,
//        targetType: Type,
//        converterType: Class<out HttpMessageConverter<*>>
//    ): Any {
//        if (body is ApiRequest<*>) {
//            try {
//                val method = parameter.method ?: throw IllegalStateException("Method not found")
//
//                // 2. 메서드명 추출 (Kotlin 리플렉션)
//                val kotlinFunction = ReflectJvmMapping.getKotlinFunction(method)
//
//                // 3. 서비스명 생성 (컨트롤러 이름 기반)
//                val serviceName = controllerClass.simpleName
//                    .replace("Controller", "")
//                    .lowercase()
//
//                // 값 설정
//                body.service = "$serviceName-service"
//                body.action = kotlinMethod ?: "unknown"
//
//                println("✅ [Reflection Success] Service: ${body.service}, Action: ${body.action}")
//
//            } catch (e: Exception) {
//                println("❌ [Reflection Error] ${e.message}")
//                body.service = "unknown-service"
//                body.action = "unknown"
//            }
//        }
//        return body
//    }
//
//    private fun getOriginalControllerClass(parameter: org.springframework.core.MethodParameter): Class<*> {
//        // 프록시 클래스인 경우 원본 클래스 추출
//        return parameter.javaClass.javaClass
//    }
//}