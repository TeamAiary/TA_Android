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
            val missionTextViews = arrayOf(
                binding.tvMission1,
                binding.tvMission2,
                binding.tvMission3,
                binding.tvMission4,
                binding.tvMission5,
                binding.tvMission6
            )

            missions.forEachIndexed { index, mission ->
                if (index < missionTextViews.size) {
                    missionTextViews[index].text = mission.content
                }
            }
        })

        missionViewModel.fetchMissions()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}