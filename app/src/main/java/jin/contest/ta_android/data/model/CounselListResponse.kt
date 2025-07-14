package jin.contest.ta_android.data.model

data class CounselListResponse(
    val totalCount: Int,
    val data: List<CounselItem>
)

data class CounselItem(
    val 기관명: String,
    val 주소: String
) 