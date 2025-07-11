package jin.contest.ta_android.data.model

data class DiaryResponse(
    val title: String,
    val date: String,
    val weather: String,
    val preview: String,
    val emotion: String,
    val emotionPoint: Int
) 