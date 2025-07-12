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
import jin.contest.ta_android.data.repository.ReportRepository
import jin.contest.ta_android.databinding.FragmentHomeBinding

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

        // ViewModel에 ReportRepository 주입
        val apiService = RetrofitClient.apiService
        val reportRepository = ReportRepository(apiService)
        homeViewModel = ViewModelProvider(this, HomeViewModel.Factory(reportRepository))[HomeViewModel::class.java]

        homeViewModel.weeklyReports.observe(viewLifecycleOwner, Observer { reports ->
            if (reports.isNotEmpty()) {
                val firstReport = reports[0]
                binding.tvWeeklyReportTitle.text = firstReport.title
                binding.tvWeeklyReportContent.text = firstReport.content
            } else {
                binding.tvWeeklyReportTitle.text = "리포트가 없습니다."
                binding.tvWeeklyReportContent.text = ""
            }
        })

        homeViewModel.fetchWeeklyReports()

        // 일기 작성하기 버튼 클릭 시 WritingActivity로 이동
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