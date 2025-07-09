package jin.contest.ta_android.ui.writing

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import jin.contest.ta_android.R
import android.widget.TextView
import android.widget.Button
import android.app.AlertDialog
import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import jin.contest.ta_android.EmotionActivity
import jin.contest.ta_android.MainActivity
import jin.contest.ta_android.databinding.FragmentWritingBinding
import jin.contest.ta_android.data.model.WritingRequest
import jin.contest.ta_android.ui.login.LogInViewModel

class WritingFragment : Fragment() {

    companion object {
        fun newInstance() = WritingFragment()
    }
    private var iconsVisible = false

    private var _binding: FragmentWritingBinding? = null
    private val binding get() = _binding!!


    private val viewModel: WritingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWritingBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewDate = view.findViewById<TextView>(R.id.textViewDate)
        val currentDate = java.text.SimpleDateFormat("yyyy년 MM월 dd일", java.util.Locale.getDefault()).format(java.util.Date())
        textViewDate.text = currentDate
        var resultWeather = String()
        val btnWeather = view.findViewById<ImageButton>(R.id.btnWeather)
        val iconSun = view.findViewById<ImageButton>(R.id.iconSun)
        val iconCloud = view.findViewById<ImageButton>(R.id.iconCloud)
        val iconRain = view.findViewById<ImageButton>(R.id.iconRain)
        val iconSnow = view.findViewById<ImageButton>(R.id.iconSnow)



        btnWeather.setOnClickListener {
            if (iconsVisible) {
                hideIcons(iconSun, iconCloud, iconRain, iconSnow)
            } else {
                showIcons(iconSun, iconCloud, iconRain, iconSnow)
            }
        }

        iconSun.setOnClickListener {
            btnWeather.setImageResource(R.drawable.icon_sun)
            resultWeather="맑음"
            hideIcons(iconSun, iconCloud, iconRain, iconSnow)
        }

        iconCloud.setOnClickListener {
            btnWeather.setImageResource(R.drawable.icon_cloudy)
            resultWeather="흐림"
            hideIcons(iconSun, iconCloud, iconRain, iconSnow)
        }

        iconRain.setOnClickListener {
            btnWeather.setImageResource(R.drawable.icon_rain)
            resultWeather="비"
            hideIcons(iconSun, iconCloud, iconRain, iconSnow)
        }
        iconSnow.setOnClickListener {
            btnWeather.setImageResource(R.drawable.icon_snow)
            resultWeather="눈"
            hideIcons(iconSun, iconCloud, iconRain, iconSnow)
        }

        val buttonSave = view.findViewById<Button>(R.id.buttonSave)

        buttonSave.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("저장 확인")
                .setMessage("이대로 작성을 완료하시겠습니까?")
                .setPositiveButton("예") { dialog, _ ->
                    val title = binding.editTextTitle.text.toString()
                    val content = binding.editTextDiary.text.toString()

                    if (title.isEmpty() || content.isEmpty()){
                        Toast.makeText(activity, "일기를 작성해주세요", Toast.LENGTH_SHORT).show()
                    }
                    val request = WritingRequest(
                        title = title,
                        content = content,
                        weather = resultWeather
                    )
                    viewModel.submitPost(request)
                    observeViewModel()
                    dialog.dismiss()
                }
                .setNegativeButton("아니오") { dialog, _ ->
                    dialog.dismiss()
                }

                .show()
        }
    }

    private fun showIcons(vararg icons: ImageButton) {
        for (icon in icons) {
            icon.visibility = View.VISIBLE
            icon.alpha = 0f
            icon.animate().alpha(1f).setDuration(200).start()
        }
        iconsVisible = true
    }

    private fun hideIcons(vararg icons: ImageButton) {
        for (icon in icons) {
            icon.animate().alpha(0f).setDuration(200).withEndAction {
                icon.visibility = View.GONE
            }.start()
        }
        iconsVisible = false
    }

    private fun observeViewModel() {
        viewModel.writingResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                Toast.makeText(requireContext(), "보내기 성공!", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), EmotionActivity::class.java)
                startActivity(intent)
            }
            result.onFailure { e ->
                Toast.makeText(requireContext(), "보내기에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}