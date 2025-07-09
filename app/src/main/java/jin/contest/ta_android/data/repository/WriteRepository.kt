package jin.contest.ta_android.data.repository

import jin.contest.ta_android.data.model.WritingRequest
import jin.contest.ta_android.data.model.WritingResponse
import jin.contest.ta_android.data.remote.ApiService
import retrofit2.Response

class WriteRepository(private val apiService: ApiService) {
    suspend fun createPost(postRequest: WritingRequest): Response<WritingResponse> {
        return apiService.createPost(postRequest)
    }
}