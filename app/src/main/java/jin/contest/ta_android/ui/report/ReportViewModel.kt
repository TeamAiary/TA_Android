package jin.contest.ta_android.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jin.contest.ta_android.data.model.WeeklyReportResponse
import jin.contest.ta_android.data.remote.RetrofitClient
import jin.contest.ta_android.data.repository.ReportRepository
import jin.contest.ta_android.data.model.MonthlyReportResponse
import jin.contest.ta_android.data.repository.MonthReportRepository
import kotlinx.coroutines.launch

class ReportViewModel : ViewModel() {

    private val repository = ReportRepository(RetrofitClient.apiService)
    private val month_repository = MonthReportRepository(RetrofitClient.apiService)

    private val _weeklyList = MutableLiveData<List<WeeklyReportResponse>>()
    val weeklyList: LiveData<List<WeeklyReportResponse>> get() = _weeklyList

    private val _monthlyList = MutableLiveData<List<MonthlyReportResponse>>()
    val monthlyList: LiveData<List<MonthlyReportResponse>> get() = _monthlyList

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun loadWeeklyReport(page: Int = 0, size: Int = 12) {
        viewModelScope.launch {
            try {
                val response = repository.getWeeklyReports(page, size)
                if (response!=null) {
                    val pageData = response
                    _weeklyList.value = pageData?.content ?: emptyList()
                } else {
                    _errorMessage.value = "서버 오류"
                }
            } catch (e: Exception) {
                _errorMessage.value = "네트워크 오류"
            }
        }
    }

    fun loadMonthlyReport(page: Int = 0, size: Int = 12) {
        viewModelScope.launch {
            try {
                val response = month_repository.getMonthReports(page, size)
                if (response!=null) {
                    val pageData = response
                    _monthlyList.value = pageData?.content ?: emptyList()
                } else {
                    _errorMessage.value = "서버 오류"
                }
            } catch (e: Exception) {
                _errorMessage.value = "네트워크 오류"
            }
        }
    }
}