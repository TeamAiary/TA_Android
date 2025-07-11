package jin.contest.ta_android.ui.emotion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jin.contest.ta_android.data.model.WritingRequest
import jin.contest.ta_android.data.model.WritingResponse
import jin.contest.ta_android.data.remote.RetrofitClient
import jin.contest.ta_android.data.repository.WriteRepository
import kotlinx.coroutines.launch

class EmotionViewModel : ViewModel() {
    private val repository = WriteRepository(RetrofitClient.apiService)
    private val _postResult = MutableLiveData<Result<WritingResponse>>()
    val writingResult: LiveData<Result<WritingResponse>> = _postResult

    fun submitPost(request: WritingRequest) {
        viewModelScope.launch {
            try {
                val response = repository.createPost(request)
                if (response.isSuccessful && response.body() != null) {
                    _postResult.value = Result.success(response.body()!!)
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "서버 오류"
                    _postResult.value = Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                _postResult.value = Result.failure(e)
            }
        }
    }
}