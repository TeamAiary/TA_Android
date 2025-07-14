package jin.contest.ta_android.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jin.contest.ta_android.WritingActivity
import jin.contest.ta_android.data.remote.RetrofitClient
import jin.contest.ta_android.data.repository.ReportRepository
import jin.contest.ta_android.data.repository.DiaryRepository
import jin.contest.ta_android.data.repository.TodayRepository
import jin.contest.ta_android.databinding.FragmentHomeBinding
import androidx.navigation.fragment.findNavController
import jin.contest.ta_android.R

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root

        // ViewModel에 Repository들 주입
        val apiService = RetrofitClient.apiService
        val reportRepository = ReportRepository(apiService)
        val diaryRepository = DiaryRepository(apiService)
        val todayRepository = TodayRepository()
        homeViewModel = ViewModelProvider(this, HomeViewModel.Factory(reportRepository, diaryRepository, todayRepository))[HomeViewModel::class.java]

        homeViewModel.weeklyReports.observe(viewLifecycleOwner, Observer { reports ->
            if (reports?.content?.isNotEmpty() == true) {
                val firstReport = reports.content[0]  // 첫 번째 리포트 가져오기
                Log.d("HomeFragment", "첫 번째 리포트: ${firstReport.title}, ${firstReport.content}")
                binding.tvWeeklyReportContent.text = firstReport.content
            } else {
                binding.tvWeeklyReportContent.text = "리포트가 없습니다."
            }
        })

        homeViewModel.weeklyDo.observe(viewLifecycleOwner, Observer { weeklyDo ->
            // 체크박스들에 일기 작성 여부 표시 (월~일 순서)
            if (weeklyDo.size >= 7) {
                binding.cbMonday.isChecked = weeklyDo[0]
                binding.cbTuesday.isChecked = weeklyDo[1]
                binding.cbWednesday.isChecked = weeklyDo[2]
                binding.cbThursday.isChecked = weeklyDo[3]
                binding.cbFriday.isChecked = weeklyDo[4]
                binding.cbSaturday.isChecked = weeklyDo[5]
                binding.cbSunday.isChecked = weeklyDo[6]
            }
        })

        homeViewModel.fetchWeeklyReports()
        homeViewModel.fetchWeeklyDo()

        // 일기 작성하기 버튼 클릭 시 WritingActivity로 이동
        binding.floatingToday.btnWriteDiary.setOnClickListener {
            val intent = Intent(requireActivity(), WritingActivity::class.java)
            startActivity(intent)
        }

        homeViewModel.todayDiary.observe(viewLifecycleOwner, Observer { diary ->
            if (diary?.content?.isNotEmpty() == true) {
                binding.tvDiaryTitle.text = diary.title
                binding.tvDiaryDate.text = diary.createdAt.substring(0,10)
                binding.tvDiaryWeather.text = "(날씨 : ${diary.weather})"
                binding.tvDiaryEmotion.text = "[${getEmotion(diary.emotion)}점수 : ${diary.emotionPoint}]"
                binding.tvDiaryContent.text = diary.content
                Log.d("test", "${diary.content}")
            }
            else{
                binding.tvDiaryTitle.text = "오늘의 일기가 없습니다"
            }
        })
        homeViewModel.fetchTodayDiary()

        // 주변 상담소 카드 클릭 시 CounselFragment로 이동
        binding.cardCounselLink.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_counsel)
        }

        return root
    }
    private fun getEmotion(emotion: String): String {
        return when (emotion) {
            "happy" -> "행복"
            "depress" -> "우울"
            else -> "분노"
        }
    }

    private fun getWeather(weather: String): String {
        return when (weather) {
            "SUNNY" -> "맑음"
            "CLOUDY" -> "흐림"
            "RAINY" -> "비"
            else -> "눈"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}