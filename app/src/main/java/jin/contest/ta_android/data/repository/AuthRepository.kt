package jin.contest.ta_android.data.repository

import jin.contest.ta_android.data.model.SignUpRequest
import jin.contest.ta_android.data.model.SignUpResponse
import jin.contest.ta_android.data.model.LogInRequest
import jin.contest.ta_android.data.model.LogInResponse
import jin.contest.ta_android.data.remote.ApiService
import retrofit2.Response

class AuthRepository(private val apiService: ApiService) {
    suspend fun registerUser(request: SignUpRequest): Response<SignUpResponse> {
        return apiService.registerUser(request)
    }
    suspend fun loginUser(email: String, password: String): Response<LogInResponse> {
        return apiService.loginUser(LogInRequest(email, password))
    }
} 