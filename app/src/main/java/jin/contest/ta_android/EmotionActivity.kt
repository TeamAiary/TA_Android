package jin.contest.ta_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jin.contest.ta_android.ui.emotion.EmotionFragment

class EmotionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_emotion)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.emotion_fragment_container, EmotionFragment.newInstance())
                .commitNow()
        }
        val title = intent.getStringExtra("title") ?: ""
        val content = intent.getStringExtra("content") ?: ""
        val resultWeather = intent.getStringExtra("resultWeather") ?: ""

        val bundle = Bundle().apply {
            putString("title", title)
            putString("content", content)
            putString("resultWeather", resultWeather)
        }

        val emotionFragment = EmotionFragment()
        emotionFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.emotion_fragment_container, emotionFragment)
            .commit()
    }
}