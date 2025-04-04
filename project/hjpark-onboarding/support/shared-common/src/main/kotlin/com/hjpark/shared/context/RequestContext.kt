package com.hjpark.shared.context

/** 요청 컨텍스트 관리를 위한 단순 유틸리티 */
object RequestContext {
    private val requestIdThreadLocal = ThreadLocal<String>()

    fun setRequestId(requestId: String) {
        requestIdThreadLocal.set(requestId)
    }

    fun getRequestId(): String? {
        return requestIdThreadLocal.get()
    }

    fun clear() {
        requestIdThreadLocal.remove()
    }
}
