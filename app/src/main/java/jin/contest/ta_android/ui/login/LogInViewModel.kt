package jin.contest.ta_android.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jin.contest.ta_android.data.model.LogInResponse
import kotlinx.coroutines.launch
import jin.contest.ta_android.data.repository.AuthRepository
import jin.contest.ta_android.data.remote.RetrofitClient
import jin.contest.ta_android.data.SessionManager

class LogInViewModel : ViewModel() {
    private val repository = AuthRepository(RetrofitClient.apiService)
    private val _logInResult = MutableLiveData<Result<LogInResponse>>()
    val logInResult: LiveData<Result<LogInResponse>> = _logInResult
    fun logIn(email : String , password : String) {
        viewModelScope.launch {
            try {
                val response = repository.loginUser(email, password)
                if (response.isSuccessful && response.body() != null) {
                    _logInResult.value = Result.success(response.body()!!)
                    val setCookie = response.headers()["Set-Cookie"]
                    val sessionId = setCookie?.split(";")?.find { it.trim().startsWith("JSESSIONID=") }
                    SessionManager.jsessionId = sessionId

                } else {
                    val errorMsg = response.errorBody()?.string() ?: "알 수 없는 오류"
                    _logInResult.value = Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                _logInResult.value = Result.failure(e)
            }
        }
    }
} 