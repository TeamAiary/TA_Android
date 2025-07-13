package jin.contest.ta_android.ui.mission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import jin.contest.ta_android.data.remote.RetrofitClient
import jin.contest.ta_android.data.repository.MissionRepository
import jin.contest.ta_android.databinding.FragmentMissionBinding

class MissionFragment : Fragment() {

    private var _binding: FragmentMissionBinding? = null
    private val binding get() = _binding!!
    private lateinit var missionViewModel: MissionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMissionBinding.inflate(inflater, container, false)
        val root = binding.root

        // ViewModel에 MissionRepository 주입
        val apiService = RetrofitClient.apiService
        val missionRepository = MissionRepository(apiService)
        missionViewModel = ViewModelProvider(this, MissionViewModel.Factory(missionRepository))[MissionViewModel::class.java]

        missionViewModel.missions.observe(viewLifecycleOwner, Observer { missions ->
            // TODO: 미션 리스트를 UI에 반영 (예: 각 미션 TextView/CheckBox에 내용 할당)
            // 예시: binding.tvMissionTitle.text = missions.getOrNull(0)?.content ?: ""
        })

        missionViewModel.fetchMissions()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}