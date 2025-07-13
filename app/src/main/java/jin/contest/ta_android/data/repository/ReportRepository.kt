package jin.contest.ta_android.data.repository

import jin.contest.ta_android.data.model.PageResponse
import jin.contest.ta_android.data.model.WeeklyReportResponse
import jin.contest.ta_android.data.remote.ApiService
import jin.contest.ta_android.data.model.MonthlyReportResponse

class ReportRepository(private val apiService: ApiService) {
    suspend fun getWeeklyReports(page: Int, size: Int): PageResponse<WeeklyReportResponse>? {
        val response = apiService.getWeeklyReports(page, size)
        return if (response.isSuccessful) response.body() else null
    }
}
class MonthReportRepository(private val apiService: ApiService) {
    suspend fun getMonthReports(page: Int, size: Int): PageResponse<MonthlyReportResponse>? {
        val response = apiService.getMonthlyReports(page, size)
        return if (response.isSuccessful) response.body() else null
    }
}