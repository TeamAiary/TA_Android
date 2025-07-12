package jin.contest.ta_android.data.repository

import jin.contest.ta_android.data.model.DiaryResponse
import jin.contest.ta_android.data.remote.ApiService
import retrofit2.Response

class MyDiaryRepository (private val apiService: ApiService) {
    suspend fun getMyDiaryList(): Response<List<DiaryResponse>> {
        return apiService.getMyDiaryList()
    }
}