package jin.contest.ta_android.data.remote


import jin.contest.ta_android.data.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import android.util.Log

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val sessionId = SessionManager.jsessionId

        if (!sessionId.isNullOrEmpty()) {
            Log.d("AuthInterceptor", "Sending JSESSIONID: $sessionId")

            val requestWithCookie = originalRequest.newBuilder()
                .addHeader("Cookie", sessionId)
                .build()

            return chain.proceed(requestWithCookie)
        }

        Log.d("AuthInterceptor", "No JSESSIONID found.")
        return chain.proceed(originalRequest)
    }
}
