package jin.contest.ta_android.data.model

data class MonthlyReportResponse(
    val title: String,
    val content: String,
    val type: String,
    val startDate: String,
    val endDate: String,
    val depression: Int,
    val anger: Int,
    val happy: Int,
    val riskScore: Int
)