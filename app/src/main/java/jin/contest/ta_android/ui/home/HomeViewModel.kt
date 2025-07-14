package jin.contest.ta_android.ui.home

import android.graphics.pdf.PdfDocument.Page
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jin.contest.ta_android.data.model.DiaryDetailResponse
import jin.contest.ta_android.data.model.WeeklyReportResponse
import jin.contest.ta_android.data.repository.ReportRepository
import jin.contest.ta_android.data.repository.DiaryRepository
import kotlinx.coroutines.launch
import jin.contest.ta_android.data.model.PageResponse
import jin.contest.ta_android.data.model.TodayDiaryResponse
import jin.contest.ta_android.data.repository.TodayRepository

class HomeViewModel(
    private val reportRepository: ReportRepository,
    private val diaryRepository: DiaryRepository,
    private val todayRepository: TodayRepository
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    private val _weeklyReports = MutableLiveData<PageResponse<WeeklyReportResponse>?>()
    val weeklyReports: LiveData<PageResponse<WeeklyReportResponse>?> = _weeklyReports

    private val _weeklyDo = MutableLiveData<List<Boolean>>()
    val weeklyDo: LiveData<List<Boolean>> = _weeklyDo

    private val _todayDiary = MutableLiveData<TodayDiaryResponse?>()
    val todayDiary : LiveData<TodayDiaryResponse?> = _todayDiary

    fun fetchTodayDiary(){
        viewModelScope.launch {
            val result = todayRepository.getTodayDiary()
            _todayDiary.value = result
        }
    }

    fun fetchWeeklyReports(page: Int = 0, size: Int = 10) {
        viewModelScope.launch {
            val result = reportRepository.getWeeklyReports(page, size)
            _weeklyReports.value = result
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
        private val diaryRepository: DiaryRepository,
        private val todayRepository: TodayRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(reportRepository, diaryRepository, todayRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}