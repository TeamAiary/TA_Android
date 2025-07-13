package jin.contest.ta_android.ui.mission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jin.contest.ta_android.data.model.MissionResponse
import jin.contest.ta_android.data.repository.MissionRepository
import kotlinx.coroutines.launch

class MissionViewModel(private val missionRepository: MissionRepository) : ViewModel() {
    private val _missions = MutableLiveData<List<MissionResponse>>()
    val missions: LiveData<List<MissionResponse>> = _missions

    fun fetchMissions() {
        viewModelScope.launch {
            val result = missionRepository.getMissions()
            _missions.value = result ?: emptyList()
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