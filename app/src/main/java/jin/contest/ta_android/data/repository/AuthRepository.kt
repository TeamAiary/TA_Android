package jin.contest.ta_android.data.repository

import jin.contest.ta_android.data.model.SignUpRequest
import jin.contest.ta_android.data.model.SignUpResponse
import jin.contest.ta_android.data.remote.ApiService
import retrofit2.Response

class AuthRepository(private val apiService: ApiService) {
    suspend fun registerUser(request: SignUpRequest): Response<SignUpResponse> {
        return apiService.registerUser(request)
    }
} 