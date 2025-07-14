package jin.contest.ta_android.data.repository

import jin.contest.ta_android.data.model.SignUpRequest
import jin.contest.ta_android.data.model.SignUpResponse
import jin.contest.ta_android.data.model.LogInRequest
import jin.contest.ta_android.data.model.LogInResponse
import jin.contest.ta_android.data.remote.ApiService
import retrofit2.Response
import jin.contest.ta_android.data.model.UserInfoResponse
import jin.contest.ta_android.data.remote.RetrofitClient

class AuthRepository(private val apiService: ApiService) {
    suspend fun registerUser(request: SignUpRequest): Response<SignUpResponse> {
        return apiService.registerUser(request)
    }
    suspend fun loginUser(email: String, password: String): Response<LogInResponse> {
        return apiService.loginUser(LogInRequest(email, password))
    }

    suspend fun getUserInfo(sessionId: String): UserInfoResponse? {
        val response = RetrofitClient.apiService.getUserInfo(sessionId)
        return if (response.isSuccessful) response.body() else null
    }
} 