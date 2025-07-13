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

        missionViewModel.missionProgress.observe(viewLifecycleOwner, Observer { progress ->
            val missionCheckBoxes = arrayOf(
                binding.cbMission1,
                binding.cbMission2,
                binding.cbMission3,
                binding.cbMission4,
                binding.cbMission5,
                binding.cbMission6
            )
            
            val completedCount = progress.count { it }
            val totalCount = progress.size

            binding.tvMissionProgress.text = "$completedCount/$totalCount"

            val progressPercentage = if (totalCount > 0) {
                (completedCount * 100) / totalCount
            } else {
                0
            }
            binding.progressMission.progress = progressPercentage

            val calendar = java.util.Calendar.getInstance()
            val currentDayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK)
            
            val remainingText = when (currentDayOfWeek) {
                java.util.Calendar.MONDAY -> "D-6"
                java.util.Calendar.TUESDAY -> "D-5"
                java.util.Calendar.WEDNESDAY -> "D-4"
                java.util.Calendar.THURSDAY -> "D-3"
                java.util.Calendar.FRIDAY -> "D-2"
                java.util.Calendar.SATURDAY -> "D-1"
                java.util.Calendar.SUNDAY -> {
                    val now = java.util.Calendar.getInstance()
                    val midnight = java.util.Calendar.getInstance().apply {
                        set(java.util.Calendar.HOUR_OF_DAY, 0)
                        set(java.util.Calendar.MINUTE, 0)
                        set(java.util.Calendar.SECOND, 0)
                        set(java.util.Calendar.MILLISECOND, 0)
                        add(java.util.Calendar.DAY_OF_MONTH, 1)
                    }
                    
                    val diffMillis = midnight.timeInMillis - now.timeInMillis
                    val diffHours = diffMillis / (1000 * 60 * 60)
                    val diffMinutes = (diffMillis % (1000 * 60 * 60)) / (1000 * 60)
                    
                    "${diffHours}H ${diffMinutes}M"
                }
                else -> "D-6"
            }
            
            binding.tvMissionHours.text = remainingText

            progress.forEachIndexed { index, isCompleted ->
                if (index < missionCheckBoxes.size) {
                    missionCheckBoxes[index].isChecked = isCompleted
                }
            }
        })

        missionViewModel.clearResult.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess { response ->
                android.widget.Toast.makeText(requireContext(), response.message, android.widget.Toast.LENGTH_SHORT).show()
            }.onFailure { exception ->
                android.widget.Toast.makeText(requireContext(), "미션 완료에 실패했습니다: ${exception.message}", android.widget.Toast.LENGTH_SHORT).show()
            }
        })

        val missionCheckBoxes = arrayOf(
            binding.cbMission1,
            binding.cbMission2,
            binding.cbMission3,
            binding.cbMission4,
            binding.cbMission5,
            binding.cbMission6
        )

        missionCheckBoxes.forEachIndexed { index, checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    missionViewModel.clearMission(index + 1)
                }
            }
        }

        missionViewModel.fetchMissions()
        missionViewModel.fetchMissionProgress()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}