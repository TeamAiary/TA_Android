package jin.contest.ta_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jin.contest.ta_android.ui.writing.WritingFragment

class WritingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.writingFragmentContainer, WritingFragment.newInstance())
                .commitNow()
        }
    }
}