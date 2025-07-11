package jin.contest.ta_android.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import jin.contest.ta_android.databinding.FragmentHomeBinding
import jin.contest.ta_android.WritingActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        // 주간 리포트 LiveData 관찰
        homeViewModel.weeklyReports.observe(viewLifecycleOwner, Observer { reports ->
            // TODO: 리포트 리스트를 UI에 반영
            // 예: recyclerView.adapter.submitList(reports)
        })

        // 리포트 불러오기 호출
        homeViewModel.fetchWeeklyReports()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}