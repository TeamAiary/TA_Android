package jin.contest.ta_android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jin.contest.ta_android.data.model.DiaryResponse
import jin.contest.ta_android.data.repository.DiaryRepository
import kotlinx.coroutines.launch

class DiaryViewModel(private val diaryRepository: DiaryRepository) : ViewModel() {
    private val _diaries = MutableLiveData<List<DiaryResponse>>()
    val diaries: LiveData<List<DiaryResponse>> = _diaries

    fun fetchAllDiaries(year: Int, month: Int, page: Int = 0, size: Int = 12) {
        viewModelScope.launch {
            val result = diaryRepository.getAllDiaries(year, month, page, size)
            _diaries.value = result?.content ?: emptyList()
        }
    }

    class Factory(private val diaryRepository: DiaryRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DiaryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DiaryViewModel(diaryRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}