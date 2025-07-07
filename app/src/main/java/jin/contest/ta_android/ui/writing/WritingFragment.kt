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
import jin.contest.ta_android.EmotionActivity


class WritingFragment : Fragment() {

    companion object {
        fun newInstance() = WritingFragment()
    }
    private var iconsVisible = false

    private val viewModel: WritingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_writing, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewDate = view.findViewById<TextView>(R.id.textViewDate)
        val currentDate = java.text.SimpleDateFormat("yyyy년 MM월 dd일", java.util.Locale.getDefault()).format(java.util.Date())
        textViewDate.text = currentDate

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
            hideIcons(iconSun, iconCloud, iconRain, iconSnow)
        }

        iconCloud.setOnClickListener {
            btnWeather.setImageResource(R.drawable.icon_cloudy)
            hideIcons(iconSun, iconCloud, iconRain, iconSnow)
        }

        iconRain.setOnClickListener {
            btnWeather.setImageResource(R.drawable.icon_rain)
            hideIcons(iconSun, iconCloud, iconRain, iconSnow)
        }
        iconSnow.setOnClickListener {
            btnWeather.setImageResource(R.drawable.icon_snow)
            hideIcons(iconSun, iconCloud, iconRain, iconSnow)
        }

        val buttonSave = view.findViewById<Button>(R.id.buttonSave)

        buttonSave.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("저장 확인")
                .setMessage("이대로 저장하시겠습니까?")
                .setNegativeButton("아니오") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("예") { dialog, _ ->
                    val intent = Intent(requireContext(), EmotionActivity::class.java)
                    startActivity(intent)
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
}