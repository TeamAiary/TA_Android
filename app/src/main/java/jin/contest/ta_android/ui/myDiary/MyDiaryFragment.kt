package jin.contest.ta_android.ui.myDiary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import jin.contest.ta_android.databinding.FragmentMyDiaryBinding
import jin.contest.ta_android.R
import androidx.appcompat.app.AlertDialog
import java.util.Calendar
import androidx.recyclerview.widget.RecyclerView
import jin.contest.ta_android.data.remote.RetrofitClient
import jin.contest.ta_android.data.repository.DiaryDetailRepository
import jin.contest.ta_android.ui.home.DiaryViewModel

class MyDiaryFragment : Fragment() {

    private var _binding: FragmentMyDiaryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyDiaryViewModel by viewModels()
    private lateinit var adapter: MyDiaryAdapter

    private var isPopupVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyDiaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        selectYear()
        viewModel.diaryDetail.observe(viewLifecycleOwner) { event ->
            val diary = event.getContentIfNotHandled() ?: return@observe
            if (!isPopupVisible) {
                isPopupVisible = true
                val dialog = DiaryDetailBottomSheet(diary)
                dialog.onDismissListener = { isPopupVisible = false }
                dialog.show(parentFragmentManager, "DiaryPopup")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getWeekdayFromDate(dateString: String): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val date = sdf.parse(dateString)
        val cal = java.util.Calendar.getInstance()
        cal.time = date!!
        return when (cal.get(java.util.Calendar.DAY_OF_WEEK)) {
            java.util.Calendar.SUNDAY -> "일"
            java.util.Calendar.MONDAY -> "월"
            java.util.Calendar.TUESDAY -> "화"
            java.util.Calendar.WEDNESDAY -> "수"
            java.util.Calendar.THURSDAY -> "목"
            java.util.Calendar.FRIDAY -> "금"
            java.util.Calendar.SATURDAY -> "토"
            else -> ""
        }
    }

    private fun getEmotionIcon(emotion: String): Int {
        return when (emotion) {
            "happy" -> R.drawable.icon_happy
            "depress" -> R.drawable.icon_depress
            else -> R.drawable.icon_angry
        }
    }

    private fun getWeatherIcon(weather: String): Int {
        return when (weather) {
            "SUNNY" -> R.drawable.icon_sun
            "CLOUDY" -> R.drawable.icon_cloudy
            "RAINY" -> R.drawable.icon_rain
            else -> R.drawable.icon_snow
        }
    }
    private fun selectYear(){
        binding.buttonYear.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.year_month_picker, null)

            val yearPicker = dialogView.findViewById<NumberPicker>(R.id.yearPicker)
            val monthPicker = dialogView.findViewById<NumberPicker>(R.id.monthPicker)

            val now = Calendar.getInstance()
            val currentYear = now.get(Calendar.YEAR)
            val currentMonth = now.get(Calendar.MONTH) + 1

            yearPicker.minValue = 2000
            yearPicker.maxValue = currentYear + 5
            yearPicker.value = currentYear

            monthPicker.minValue = 1
            monthPicker.maxValue = 12
            monthPicker.value = currentMonth

            AlertDialog.Builder(requireContext())
                .setTitle("연도/월 선택")
                .setView(dialogView)
                .setNegativeButton("취소", null)
                .setPositiveButton("확인") { _, _ ->
                    var selectedYear = yearPicker.value
                    var selectedMonth = monthPicker.value
                    binding.buttonYear.text = "${selectedYear}년 ${selectedMonth}월"
                    floatNode(selectedYear, selectedMonth)
                }
                .show()
        }

    }
    private fun floatNode(year : Int , month : Int){
        binding.diaryRecyclerView.adapter = null
        binding.diaryRecyclerView.layoutManager = null

        viewModel.diaryList.observe(viewLifecycleOwner) { diaries ->
            Log.d("MyDiaryFragment", "diaryList observe: ${diaries.size}")
            val items = diaries.map { diary ->
                DiaryItem(
                    day = diary.createdAt.substring(8, 10),
                    weekday = getWeekdayFromDate(diary.createdAt),
                    title = diary.title,
                    preview = diary.preview,
                    weather = getWeatherIcon(diary.weather.uppercase()),
                    emotion = getEmotionIcon(diary.emotion.lowercase()),
                    score = "${diary.emotionPoint}점",
                    id = diary.diaryId
                )
            }
            adapter = MyDiaryAdapter(items, object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val clickedItem = items[position]
                    viewModel.loadDiary(clickedItem.id)
                }

            })


            binding.diaryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.diaryRecyclerView.adapter = adapter
            adapter.updateItems(items)
        }

        viewModel.loadDiaries(year, month, 0, 12)
    }
}
