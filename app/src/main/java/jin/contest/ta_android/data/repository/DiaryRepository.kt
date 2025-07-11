package jin.contest.ta_android.data.repository

import jin.contest.ta_android.data.model.DiaryResponse
import jin.contest.ta_android.data.model.PageResponse
import jin.contest.ta_android.data.remote.ApiService

class DiaryRepository(private val apiService: ApiService) {
    suspend fun getAllDiaries(year: Int, month: Int, page: Int, size: Int): PageResponse<DiaryResponse>? {
        val response = apiService.getAllDiaries(year, month, page, size)
        return if (response.isSuccessful) response.body() else null
    }
} 