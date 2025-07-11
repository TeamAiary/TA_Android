package jin.contest.ta_android.data.model

// 주간 리포트 응답 데이터 모델
data class WeeklyReportResponse(
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

// 페이지네이션 응답 데이터 모델(제네릭)
data class PageResponse<T>(
    val content: List<T>,
    val totalPages: Int,
    val totalElements: Int,
    val number: Int
) 