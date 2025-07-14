package jin.contest.ta_android.data.model

data class UserInfoResponse(
    val userId: Long,
    val email: String,
    val userName: String,
    val age: Int?,
    val gender: String?,
    val role: String?,
    val phoneNumber: String?
) 