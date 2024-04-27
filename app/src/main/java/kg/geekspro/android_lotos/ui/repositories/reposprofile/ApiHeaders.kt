package kg.geekspro.android_lotos.ui.repositories.reposprofile

import okhttp3.Interceptor
import okhttp3.Response

class ApiHeaders : Interceptor {
    private var sessionId: String? = null

    fun setSessionId(sessionId: String) {
        this.sessionId = sessionId
    }

    fun clearSessionId() {
        sessionId = null
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (sessionId != null) {
            // Замените "Your-Header-Name" на имя заголовка, который вы хотите установить
            request = request.newBuilder().header("Set-Cookie", sessionId!!).build()
        }
        return chain.proceed(request)
    }
}