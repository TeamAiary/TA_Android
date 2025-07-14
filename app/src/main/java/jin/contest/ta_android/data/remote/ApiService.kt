package jin.contest.ta_android.data.remote

import jin.contest.ta_android.data.model.SignUpRequest
import jin.contest.ta_android.data.model.SignUpResponse
import jin.contest.ta_android.data.model.LogInResponse
import jin.contest.ta_android.data.model.LogInRequest
import jin.contest.ta_android.data.model.WritingRequest
import jin.contest.ta_android.data.model.WritingResponse
import jin.contest.ta_android.data.model.PageResponse
import jin.contest.ta_android.data.model.WeeklyReportResponse
import jin.contest.ta_android.data.model.DiaryResponse
import jin.contest.ta_android.data.model.WeeklyDoResponse
import jin.contest.ta_android.data.model.MonthlyReportResponse
import jin.contest.ta_android.data.model.MissionResponse
import jin.contest.ta_android.data.model.MissionClearRequest
import jin.contest.ta_android.data.model.MissionClearResponse
import jin.contest.ta_android.data.model.MissionProgressResponse
import jin.contest.ta_android.data.model.DiaryDetailResponse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path


interface ApiService {
    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: SignUpRequest): Response<SignUpResponse>

    @POST("/api/auth/login")
    suspend fun loginUser(@Body request: LogInRequest): Response<LogInResponse>

    @POST("/api/diary")
    suspend fun createPost(@Body postRequest: WritingRequest): Response<WritingResponse>

    @GET("/api/report/weekly")
    suspend fun getWeeklyReports(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<PageResponse<WeeklyReportResponse>>

    @GET("/api/report/monthly")
    suspend fun getMonthlyReports(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<PageResponse<MonthlyReportResponse>>

    @GET("/api/diary")
    suspend fun getAllDiaries(
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<PageResponse<DiaryResponse>>

    @GET("/api/diary/weekly")
    suspend fun getWeeklyDo(): Response<WeeklyDoResponse>

    @GET("/api/mission")
    suspend fun getMissions(): Response<List<MissionResponse>>

    @POST("/api/mission/clear")
    suspend fun clearMission(
        @Body request: MissionClearRequest
    ): Response<MissionClearResponse>

    @GET("/api/user/mission")
    suspend fun getMissionProgress(): Response<MissionProgressResponse>

    @GET("/api/diary/{id}")
    suspend fun getDiaryById(@Path("id") id: Long): Response<DiaryDetailResponse>

    @DELETE("/api/diary/{id}")
    suspend fun deleteDiary(@Path("id") id: Long): Response<Void>
}
