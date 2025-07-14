package jin.contest.ta_android.ui.myDiary

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jin.contest.ta_android.R
import jin.contest.ta_android.data.model.DiaryDetailResponse


class DiaryDetailBottomSheet(private val diary: DiaryDetailResponse) : BottomSheetDialogFragment() {
    var onDismissListener: (() -> Unit)? = null

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_diary_detail, container, false)
        view.findViewById<TextView>(R.id.detailTitle).text = diary.title
        view.findViewById<TextView>(R.id.detailDate).text = diary.createdAt.substring(0,10)
        view.findViewById<TextView>(R.id.detailContent).text = diary.content
        view.findViewById<ImageView>(R.id.detailWeather).imageAlpha = getWeather(diary.weather)
        view.findViewById<ImageView>(R.id.detailEmotion).imageAlpha = getEmotion(diary.emotion)
        view.findViewById<TextView>(R.id.detailContent).text = diary.content

        return view
    }
    private fun getEmotion(emotion: String): Int {
        return when (emotion) {
            "happy" -> R.drawable.icon_happy
            "depress" -> R.drawable.icon_depress
            else -> R.drawable.icon_angry
        }
    }

    private fun getWeather(weather: String): Int {
        return when (weather) {
            "SUNNY" -> R.drawable.icon_sun
            "CLOUDY" -> R.drawable.icon_cloudy
            "RAINY" -> R.drawable.icon_rain
            else -> R.drawable.icon_snow
        }
    }
}
