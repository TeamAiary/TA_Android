package jin.contest.ta_android.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import jin.contest.ta_android.data.model.SignUpRequest
import jin.contest.ta_android.data.model.SignUpResponse
import jin.contest.ta_android.data.repository.AuthRepository
import jin.contest.ta_android.data.remote.RetrofitClient
import retrofit2.HttpException

class SignUpViewModel : ViewModel() {
    private val repository = AuthRepository(RetrofitClient.apiService)
    private val _signUpResult = MutableLiveData<Result<SignUpResponse>>()
    val signUpResult: LiveData<Result<SignUpResponse>> = _signUpResult

    fun signUp(request: SignUpRequest) {
        viewModelScope.launch {
            try {
                val response = repository.registerUser(request)
                if (response.isSuccessful && response.body() != null) {
                    _signUpResult.value = Result.success(response.body()!!)
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "알 수 없는 오류"
                    _signUpResult.value = Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                _signUpResult.value = Result.failure(e)
            }
        }
    }
} 