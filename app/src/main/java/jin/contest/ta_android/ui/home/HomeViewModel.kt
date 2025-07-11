package jin.contest.ta_android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jin.contest.ta_android.data.model.WeeklyReportResponse
import jin.contest.ta_android.data.repository.ReportRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    private val _weeklyReports = MutableLiveData<List<WeeklyReportResponse>>()
    val weeklyReports: LiveData<List<WeeklyReportResponse>> = _weeklyReports

    private val reportRepository = ReportRepository(/* ApiService 인스턴스 주입 필요 */)

    fun fetchWeeklyReports(page: Int = 0, size: Int = 10) {
        viewModelScope.launch {
            val result = reportRepository.getWeeklyReports(page, size)
            _weeklyReports.value = result?.content ?: emptyList()
        }
    }
}