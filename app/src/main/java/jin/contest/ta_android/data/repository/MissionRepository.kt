package jin.contest.ta_android.data.repository

import jin.contest.ta_android.data.model.MissionResponse
import jin.contest.ta_android.data.model.MissionClearRequest
import jin.contest.ta_android.data.model.MissionClearResponse
import jin.contest.ta_android.data.model.MissionProgressResponse
import jin.contest.ta_android.data.remote.ApiService

class MissionRepository(private val apiService: ApiService) {
    suspend fun getMissions(): List<MissionResponse>? {
        val response = apiService.getMissions()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun clearMission(missionNumber: Int): MissionClearResponse? {
        val request = MissionClearRequest(missionNumber)
        val response = apiService.clearMission(request)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun getMissionProgress(): MissionProgressResponse? {
        val response = apiService.getMissionProgress()
        return if (response.isSuccessful) response.body() else null
    }
} 