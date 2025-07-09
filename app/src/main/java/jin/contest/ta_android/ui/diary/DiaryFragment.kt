package jin.contest.ta_android.ui.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import jin.contest.ta_android.databinding.FragmentDiaryBinding
import androidx.recyclerview.widget.RecyclerView
import jin.contest.ta_android.R
import androidx.recyclerview.widget.LinearLayoutManager

class DiaryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DiaryAdapter
    private lateinit var diaryList: List<DiaryItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_diary, container, false)
        recyclerView = view.findViewById(R.id.diaryRecyclerView)

        // 예시 데이터
        diaryList = listOf(
            DiaryItem("12", "Mon", "대학교",R.drawable.icon_sun, R.drawable.icon_angry,"100"),
            DiaryItem("11", "Sun", "알바",R.drawable.icon_snow,R.drawable.icon_depress,"90"),
            DiaryItem("9", "Fri", "공부", R.drawable.icon_cloudy,R.drawable.icon_happy,"100"),
            DiaryItem("9", "Fri", "공부", R.drawable.icon_cloudy,R.drawable.icon_happy,"100"),
            DiaryItem("9", "Fri", "공부", R.drawable.icon_cloudy,R.drawable.icon_happy,"100"),
            DiaryItem("9", "Fri", "공부", R.drawable.icon_cloudy,R.drawable.icon_happy,"100"),
            DiaryItem("9", "Fri", "공부", R.drawable.icon_cloudy,R.drawable.icon_happy,"100"),
            DiaryItem("9", "Fri", "공부", R.drawable.icon_cloudy,R.drawable.icon_happy,"100"),
            DiaryItem("9", "Fri", "공부", R.drawable.icon_cloudy,R.drawable.icon_happy,"100"),
            DiaryItem("9", "Fri", "공부", R.drawable.icon_cloudy,R.drawable.icon_happy,"100"),
            DiaryItem("9", "Fri", "공부", R.drawable.icon_cloudy,R.drawable.icon_happy,"100"),
            DiaryItem("9", "Fri", "공부", R.drawable.icon_cloudy,R.drawable.icon_happy,"100")
        )

        adapter = DiaryAdapter(diaryList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return view
    }
}
