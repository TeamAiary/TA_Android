package jin.contest.ta_android.data.repository

import jin.contest.ta_android.data.model.DiaryDetailResponse
import jin.contest.ta_android.data.remote.ApiService

class DiaryDetailRepository(private val api: ApiService) {
    suspend fun getDiary(id: Long): DiaryDetailResponse? {
        val response = api.getDiaryById(id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
    suspend fun deleteDiary(id: Long): Boolean {
        val response = api.deleteDiary(id)
        return response.isSuccessful
    }
}
