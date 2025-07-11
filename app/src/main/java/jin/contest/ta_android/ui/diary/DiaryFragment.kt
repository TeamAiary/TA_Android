package jin.contest.ta_android.ui.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jin.contest.ta_android.data.remote.RetrofitClient
import jin.contest.ta_android.data.repository.DiaryRepository
import jin.contest.ta_android.databinding.FragmentDiaryBinding

class DiaryFragment : Fragment() {
    private var _binding: FragmentDiaryBinding? = null
    private val binding get() = _binding!!
    private lateinit var diaryViewModel: DiaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiaryBinding.inflate(inflater, container, false)
        val root = binding.root

        // ViewModel에 DiaryRepository 주입
        val apiService = RetrofitClient.apiService
        val diaryRepository = DiaryRepository(apiService)
        diaryViewModel = ViewModelProvider(this, DiaryViewModel.Factory(diaryRepository))[DiaryViewModel::class.java]

        diaryViewModel.diaries.observe(viewLifecycleOwner, Observer { diaries ->
            // TODO: 일기 리스트를 UI에 반영 (예: RecyclerView adapter에 submitList)
            // 예시: binding.recyclerView.adapter.submitList(diaries)
        })

        // 예시: 2025년 7월, page=0, size=12로 호출
        diaryViewModel.fetchAllDiaries(2025, 7, 0, 12)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
