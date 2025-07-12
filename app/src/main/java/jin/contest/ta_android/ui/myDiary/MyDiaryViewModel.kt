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
import kotlinx.coroutines.launch

class MyDiaryViewModel : ViewModel() {
    private val repository = DiaryRepository(RetrofitClient.apiService)

    private val _diaryList = MutableLiveData<List<DiaryResponse>>()
    val diaryList: LiveData<List<DiaryResponse>> get() = _diaryList

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

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
