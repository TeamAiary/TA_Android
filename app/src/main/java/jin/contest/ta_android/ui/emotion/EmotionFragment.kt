package jin.contest.ta_android.ui.emotion

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import jin.contest.ta_android.MainActivity
import jin.contest.ta_android.databinding.FragmentEmotionBinding

import jin.contest.ta_android.R
import jin.contest.ta_android.WritingActivity
import jin.contest.ta_android.databinding.FragmentHomeBinding

class EmotionFragment : Fragment() {

    private var _binding: FragmentEmotionBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = EmotionFragment()
    }

    private val viewModel: EmotionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmotionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.buttonToHome.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seekBarGauge = view.findViewById<SeekBar>(R.id.seekBarGauge)
        val textViewGaugeValue = view.findViewById<TextView>(R.id.textViewGaugeValue)

        seekBarGauge.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val displayValue = progress + 1  // 1부터 시작하도록 보정
                textViewGaugeValue.text = "$displayValue"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        val iconHappy = view.findViewById<ImageButton>(R.id.iconHappy)
        val iconDepress = view.findViewById<ImageButton>(R.id.iconDepress)
        val iconAngry = view.findViewById<ImageButton>(R.id.iconAngry)

        iconHappy.setOnClickListener {
            iconDepress.background = ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
            iconAngry.background = ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
            iconHappy.background = ContextCompat.getDrawable(requireContext(), R.drawable.circle_border)
        }
        iconDepress.setOnClickListener {
            iconHappy.background = ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
            iconAngry.background = ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
            iconDepress.background = ContextCompat.getDrawable(requireContext(), R.drawable.circle_border)
        }
        iconAngry.setOnClickListener {
            iconDepress.background = ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
            iconHappy.background = ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
            iconAngry.background = ContextCompat.getDrawable(requireContext(), R.drawable.circle_border)
        }


    }


}