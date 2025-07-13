package jin.contest.ta_android.data.repository

import jin.contest.ta_android.data.model.MissionResponse
import jin.contest.ta_android.data.remote.ApiService

class MissionRepository(private val apiService: ApiService) {
    suspend fun getMissions(): List<MissionResponse>? {
        val response = apiService.getMissions()
        return if (response.isSuccessful) response.body() else null
    }
} 