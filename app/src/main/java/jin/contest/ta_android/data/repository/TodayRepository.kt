package jin.contest.ta_android.data.repository


import jin.contest.ta_android.data.model.DiaryResponse
import jin.contest.ta_android.data.model.PageResponse
import jin.contest.ta_android.data.model.TodayDiaryResponse
import jin.contest.ta_android.data.remote.ApiService
import jin.contest.ta_android.data.remote.RetrofitClient.apiService
import retrofit2.Response

class TodayRepository() {
        suspend fun getTodayDiary(): TodayDiaryResponse? {
            val response = apiService.getTodayDiary()
            return if (response.isSuccessful) response.body() else null
        }
}