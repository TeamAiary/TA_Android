package jin.contest.ta_android.ui.mission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jin.contest.ta_android.data.model.MissionResponse
import jin.contest.ta_android.data.repository.MissionRepository
import kotlinx.coroutines.launch
import jin.contest.ta_android.data.model.MissionClearResponse

class MissionViewModel(private val missionRepository: MissionRepository) : ViewModel() {
    private val _missions = MutableLiveData<List<MissionResponse>>()
    val missions: LiveData<List<MissionResponse>> = _missions

    private val _clearResult = MutableLiveData<Result<MissionClearResponse>>()
    val clearResult: LiveData<Result<MissionClearResponse>> = _clearResult

    fun fetchMissions() {
        viewModelScope.launch {
            val result = missionRepository.getMissions()
            _missions.value = result ?: emptyList()
        }
    }

    fun clearMission(missionNumber: Int) {
        viewModelScope.launch {
            try {
                val result = missionRepository.clearMission(missionNumber)
                if (result != null) {
                    _clearResult.value = Result.success(result)
                } else {
                    _clearResult.value = Result.failure(Exception("미션 완료에 실패했습니다."))
                }
            } catch (e: Exception) {
                _clearResult.value = Result.failure(e)
            }
        }
    }

    class Factory(private val missionRepository: MissionRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MissionViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MissionViewModel(missionRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}