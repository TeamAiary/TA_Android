package jin.contest.ta_android.ui.report

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import jin.contest.ta_android.databinding.FragmentReportBinding
import jin.contest.ta_android.data.model.MonthlyReportResponse
import jin.contest.ta_android.data.model.WeeklyReportResponse
import jin.contest.ta_android.databinding.FragmentMyDiaryBinding
import androidx.core.content.ContextCompat
import android.graphics.Color
import jin.contest.ta_android.R


class ReportFragment : Fragment() {
    private var _binding: FragmentReportBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportViewModel by viewModels()
    private lateinit var adapter: ReportAdapter

    private var weeklyData: List<WeeklyReportResponse> = emptyList()
    private var monthlyData: List<MonthlyReportResponse> = emptyList()
    private var isWeeklyMode: Boolean? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ReportAdapter(emptyList())
        binding.reportRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.reportRecyclerView.adapter = adapter

        viewModel.weeklyList.observe(viewLifecycleOwner) {
            weeklyData = it
            if (isWeeklyMode==true) updateList()
        }
        viewModel.monthlyList.observe(viewLifecycleOwner) {
            monthlyData = it
            if (isWeeklyMode==false) updateList()
        }

        binding.buttonWeek.setOnClickListener {
                isWeeklyMode = true
                updateList()
                updateButtonStyles()

        }
        binding.buttonMonth.setOnClickListener {
                isWeeklyMode = false
                updateList()
                updateButtonStyles()
        }

        viewModel.loadWeeklyReport()
        viewModel.loadMonthlyReport()
    }

    private fun updateList() {
        if (isWeeklyMode==true) {
            adapter.submitList(weeklyData.map { it.toReportItem() }) // 변환 함수 필요
        } else {
            adapter.submitList(monthlyData.map { it.toReportItem() })
        }
    }
    fun WeeklyReportResponse.toReportItem() = ReportItem(
        title, content, type, startDate, endDate, depression, anger, happy, riskScore
    )

    fun MonthlyReportResponse.toReportItem() = ReportItem(
        title, content, type, startDate, endDate, depression, anger, happy, riskScore
    )
    private fun updateButtonStyles() {
        val selectedColor = ContextCompat.getColor(requireContext(), R.color.white)
        val unselectedColor = ContextCompat.getColor(requireContext(), R.color.light_green)
        val context = requireContext()

        if (isWeeklyMode==true) {
            binding.buttonWeek.backgroundTintList = ColorStateList.valueOf(selectedColor)
            binding.buttonWeek.setTextColor(Color.parseColor("#B5C18E"))

            binding.buttonMonth.backgroundTintList = ColorStateList.valueOf(unselectedColor)
            binding.buttonMonth.setTextColor(Color.parseColor("#FFFFFF"))
        } else {
            binding.buttonMonth.backgroundTintList = ColorStateList.valueOf(selectedColor)
            binding.buttonMonth.setTextColor(Color.parseColor("#B5C18E"))

            binding.buttonWeek.backgroundTintList = ColorStateList.valueOf(unselectedColor)
            binding.buttonWeek.setTextColor(Color.parseColor("#FFFFFF"))
        }
    }

}