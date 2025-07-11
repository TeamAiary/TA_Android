package jin.contest.ta_android.ui.emotion

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import jin.contest.ta_android.EmotionActivity
import jin.contest.ta_android.data.model.WritingRequest
import jin.contest.ta_android.MainActivity
import jin.contest.ta_android.databinding.FragmentEmotionBinding
import jin.contest.ta_android.ui.writing.WritingFragment

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmotionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seekBarHappy = view.findViewById<SeekBar>(R.id.seekBarHappy)
        val seekBarDepress = view.findViewById<SeekBar>(R.id.seekBarDepress)
        val seekBarAngry = view.findViewById<SeekBar>(R.id.seekBarAngry)
        val textValueHappy = view.findViewById<TextView>(R.id.textValueHappy)
        val textValueDepress = view.findViewById<TextView>(R.id.textValueDepress)
        val textValueAngry = view.findViewById<TextView>(R.id.textValueAngry)

        seekBarHappy.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val displayValueHappy = progress + 1  // 1부터 시작하도록 보정
                textValueHappy.text = "$displayValueHappy"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBarDepress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val displayValueDepress = progress + 1  // 1부터 시작하도록 보정
                textValueDepress.text = "$displayValueDepress"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        seekBarAngry.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val displayValueAngry = progress + 1  // 1부터 시작하도록 보정
                textValueAngry.text = "$displayValueAngry"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.buttonToHome.setOnClickListener {
            val title = arguments?.getString("title")?:""
            val content = arguments?.getString("content")?:""
            val resultWeather = arguments?.getString("resultWeather")?:""
            Log.d("text",title)
            Log.d("content", content)
            Log.d("weather", resultWeather)
            val angryDegree = seekBarAngry.progress
            val happyDegree = seekBarHappy.progress
            val depressDegree = seekBarDepress.progress

            val request = WritingRequest(
                title = title,
                content = content,
                weather = resultWeather,
                happy = happyDegree,
                depression = depressDegree,
                anger = angryDegree

            )
            viewModel.submitPost(request)
            observeViewModel()
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)

        }
    }

    private fun observeViewModel() {
        viewModel.writingResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                Toast.makeText(requireContext(), "보내기 성공!", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
            result.onFailure { e ->
                Toast.makeText(requireContext(), "보내기에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}