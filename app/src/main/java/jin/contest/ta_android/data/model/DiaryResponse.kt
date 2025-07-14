package jin.contest.ta_android.data.model

data class DiaryResponse(
    val title: String,
    val createdAt: String,
    val weather: String,
    val preview: String,
    val emotion: String,
    val emotionPoint: Int,
    val diaryId: Long
) 