package jin.contest.ta_android.data.model

data class WritingRequest(
    val title: String,
    val content: String,
    val weather: String,
    val happy : Int,
    val depression : Int,
    val anger : Int
)