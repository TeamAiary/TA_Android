package jin.contest.ta_android.data.remote

import jin.contest.ta_android.data.model.SignUpRequest
import jin.contest.ta_android.data.model.SignUpResponse
import jin.contest.ta_android.data.model.LogInResponse
import jin.contest.ta_android.data.model.LogInRequest
import jin.contest.ta_android.data.model.WritingRequest
import jin.contest.ta_android.data.model.WritingResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: SignUpRequest): Response<SignUpResponse>

    @POST("/api/auth/login")
    suspend fun loginUser(@Body request: LogInRequest): Response<LogInResponse>

    @POST("/api/diary")
    suspend fun createPost(@Body postRequest: WritingRequest): Response<WritingResponse>

}

