package jin.contest.ta_android.data.model

data class DiaryDetailResponse(
    val title: String,
    val createdAt: String,
    val weather: String,
    val content: String,
    val emotion: String,
    val emotionPoint: Int,
) 