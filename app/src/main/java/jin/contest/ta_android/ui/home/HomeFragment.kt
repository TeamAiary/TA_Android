package jin.contest.ta_android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import jin.contest.ta_android.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root

        homeViewModel.weeklyReports.observe(viewLifecycleOwner, Observer { reports ->
            if (reports.isNotEmpty()) {
                val firstReport = reports[0]
                binding.tvWeeklyReportTitle.text = firstReport.title
                // content용 TextView가 없으므로, 아래에 추가 필요
                // binding.tvWeeklyReportContent.text = firstReport.content
            } else {
                binding.tvWeeklyReportTitle.text = "리포트가 없습니다."
                // binding.tvWeeklyReportContent.text = ""
            }
        })

        homeViewModel.fetchWeeklyReports()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}