package jin.contest.ta_android.data.model

data class SignUpRequest(
    val email: String,
    val password: String,
    val userName: String,
    val age: Int,
    val gender: String,      // "MALE" or "FEMALE"
    val role: String,        // "ADMIN", "PATIENT", "EXPERT"
    val phoneNumber: String
) 