package jin.contest.ta_android.ui.myDiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jin.contest.ta_android.data.SessionManager
import jin.contest.ta_android.data.model.DiaryResponse
import jin.contest.ta_android.data.remote.RetrofitClient
import jin.contest.ta_android.data.repository.MyDiaryRepository
import kotlinx.coroutines.launch

class MyDiaryViewModel : ViewModel() {
    private val repository = MyDiaryRepository(RetrofitClient.apiService)
    private val _MyDiaryList = MutableLiveData<List<DiaryResponse>>()
    val MyDiaryList: LiveData<List<DiaryResponse>> get() = _MyDiaryList

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun myDiary() {
        viewModelScope.launch {
            try {
                val response = repository.getMyDiaryList()
                if (response.isSuccessful && response.body() != null) {
                    _MyDiaryList.value = response.body() ?: emptyList()

                } else {
                    _errorMessage.value = "서버 응답 오류: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "네트워크 오류: ${e.localizedMessage}"
            }
        }
    }
}