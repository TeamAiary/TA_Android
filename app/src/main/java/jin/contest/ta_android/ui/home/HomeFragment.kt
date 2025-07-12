package jin.contest.ta_android.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jin.contest.ta_android.WritingActivity
import jin.contest.ta_android.data.remote.RetrofitClient
import jin.contest.ta_android.data.repository.DiaryRepository
import jin.contest.ta_android.data.repository.ReportRepository
import jin.contest.ta_android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var diaryViewModel: DiaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root

        // ViewModel에 ReportRepository 주입
        val apiService = RetrofitClient.apiService
        val reportRepository = ReportRepository(apiService)
        homeViewModel = ViewModelProvider(this, HomeViewModel.Factory(reportRepository))[HomeViewModel::class.java]

        homeViewModel.weeklyReports.observe(viewLifecycleOwner, Observer { reports ->
            if (reports.isNotEmpty()) {
                val firstReport = reports[0]
                binding.tvWeeklyReportTitle.text = firstReport.title
                // content용 TextView가 레이아웃에 추가되어 있다면 아래 코드 사용
                binding.tvWeeklyReportContent.text = firstReport.content
            } else {
                binding.tvWeeklyReportTitle.text = "리포트가 없습니다."
                binding.tvWeeklyReportContent.text = ""
            }
        })

        homeViewModel.fetchWeeklyReports()

        // 일기 ViewModel에 DiaryRepository 주입
        val diaryRepository = DiaryRepository(apiService)
        diaryViewModel = ViewModelProvider(this, DiaryViewModel.Factory(diaryRepository))[DiaryViewModel::class.java]

        diaryViewModel.diaries.observe(viewLifecycleOwner, Observer { diaries ->
            if (diaries.isNotEmpty()) {
                val firstDiary = diaries[0]
                binding.tvDiaryContent.text = firstDiary.title
            } else {
                binding.tvDiaryContent.text = "일기가 없습니다."
            }
        })
        // 예시: 2025년 7월, page=0, size=12로 호출
        diaryViewModel.fetchAllDiaries(2025, 7, 0, 12)
        binding.floatingToday.btnWriteDiary.setOnClickListener {
            val intent = Intent(requireActivity(), WritingActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}