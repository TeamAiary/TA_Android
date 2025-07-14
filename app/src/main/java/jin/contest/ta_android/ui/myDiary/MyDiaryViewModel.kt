package jin.contest.ta_android.ui.myDiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jin.contest.ta_android.data.SessionManager
import jin.contest.ta_android.data.model.DiaryResponse
import jin.contest.ta_android.data.remote.RetrofitClient
import jin.contest.ta_android.data.repository.DiaryRepository
import android.util.Log
import jin.contest.ta_android.data.model.DiaryDetailResponse
import jin.contest.ta_android.data.repository.DiaryDetailRepository
import kotlinx.coroutines.launch

class MyDiaryViewModel : ViewModel() {
    private val repository = DiaryRepository(RetrofitClient.apiService)
    private val detailRepository = DiaryDetailRepository(RetrofitClient.apiService)

    private val _diaryList = MutableLiveData<List<DiaryResponse>>()
    val diaryList: LiveData<List<DiaryResponse>> get() = _diaryList

    private val _diaryDetail = MutableLiveData<Event<DiaryDetailResponse>>()
    val diaryDetail: LiveData<Event<DiaryDetailResponse>> = _diaryDetail

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _deleteSuccess = MutableLiveData<Event<Boolean>>()
    val deleteSuccess: LiveData<Event<Boolean>> = _deleteSuccess

    fun deleteDiary(id: Long) {
        viewModelScope.launch {
            val result = detailRepository.deleteDiary(id)
            _deleteSuccess.value = Event(result)
        }
    }

    fun loadDiary(id: Long) {
        viewModelScope.launch {
            val diary = detailRepository.getDiary(id)
            diary?.let {
                _diaryDetail.value = Event(it)
            }
        }
    }



    fun loadDiaries(year: Int, month: Int, page: Int = 0, size: Int = 12) {
        viewModelScope.launch {
            try {
                val response = repository.getAllDiaries(year, month, page, size)
                if (response!=null) {
                    val pageData = response
                    _diaryList.value = pageData?.content ?: emptyList()
                } else {
                    _errorMessage.value = "서버 오류"
                }
            } catch (e: Exception) {
                _errorMessage.value = "네트워크 오류"
            }
        }
    }
}
