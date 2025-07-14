package jin.contest.ta_android.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jin.contest.ta_android.data.model.UserInfoResponse
import jin.contest.ta_android.data.remote.RetrofitClient
import jin.contest.ta_android.data.repository.AuthRepository
import kotlinx.coroutines.launch

class MyPageViewModel : ViewModel() {
    private val repository = AuthRepository(RetrofitClient.apiService)
    private val _userInfo = MutableLiveData<UserInfoResponse?>()
    val userInfo: LiveData<UserInfoResponse?> get() = _userInfo

    fun fetchUserInfo(sessionId: String) {
        viewModelScope.launch {
            _userInfo.value = repository.getUserInfo(sessionId)
        }
    }
}