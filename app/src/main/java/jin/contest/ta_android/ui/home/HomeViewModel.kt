package jin.contest.ta_android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jin.contest.ta_android.data.model.WeeklyReportResponse
import jin.contest.ta_android.data.repository.ReportRepository
import jin.contest.ta_android.data.repository.DiaryRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val reportRepository: ReportRepository,
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    private val _weeklyReports = MutableLiveData<List<WeeklyReportResponse>>()
    val weeklyReports: LiveData<List<WeeklyReportResponse>> = _weeklyReports

    private val _weeklyDo = MutableLiveData<List<Boolean>>()
    val weeklyDo: LiveData<List<Boolean>> = _weeklyDo

    fun fetchWeeklyReports(page: Int = 0, size: Int = 10) {
        viewModelScope.launch {
            val result = reportRepository.getWeeklyReports(page, size)
            _weeklyReports.value = result?.content ?: emptyList()
        }
    }

    fun fetchWeeklyDo() {
        viewModelScope.launch {
            val result = diaryRepository.getWeeklyDo()
            _weeklyDo.value = result?.weeklyDo ?: List(7) { false }
        }
    }

    class Factory(
        private val reportRepository: ReportRepository,
        private val diaryRepository: DiaryRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(reportRepository, diaryRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}