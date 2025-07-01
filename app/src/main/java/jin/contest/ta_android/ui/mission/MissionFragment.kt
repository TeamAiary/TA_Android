package jin.contest.ta_android.ui.mission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import jin.contest.ta_android.databinding.FragmentMissionBinding

class MissionFragment : Fragment() {

    private var _binding: FragmentMissionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val missionViewModel =
            ViewModelProvider(this).get(MissionViewModel::class.java)

        _binding = FragmentMissionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textMission
        missionViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}