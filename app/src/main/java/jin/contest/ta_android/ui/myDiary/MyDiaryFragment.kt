package jin.contest.ta_android.ui.myDiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import jin.contest.ta_android.databinding.FragmentMyDiaryBinding
import jin.contest.ta_android.R
import androidx.recyclerview.widget.RecyclerView

class MyDiaryFragment : Fragment() {

    private var _binding: FragmentMyDiaryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyDiaryViewModel by viewModels()
    private lateinit var adapter: MyDiaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyDiaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MyDiaryAdapter(emptyList()) // 초기 리스트는 비워둠
        binding.diaryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.diaryRecyclerView.adapter = adapter

        viewModel.MyDiaryList.observe(viewLifecycleOwner) { diaries ->
            // DiaryResponse → DiaryItem 변환
            val items = diaries.map { diary ->
                DiaryItem(
                    day = diary.date.substring(8, 10),
                    weekday = getWeekdayFromDate(diary.date),
                    title = diary.title,
                    weather = getWeatherIcon(diary.weather),
                    emotion = getEmotionIcon(diary.emotion),
                    score = "${diary.emotionPoint}점"
                )
            }
            adapter = MyDiaryAdapter(items) // 새 어댑터로 교체 (아니면 submitList 쓰면 더 좋음)
            binding.diaryRecyclerView.adapter = adapter
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.myDiary()
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
}
