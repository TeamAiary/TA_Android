package jin.contest.ta_android.ui.diary

import android.media.Image
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import jin.contest.ta_android.R


data class DiaryItem(
    val day: String,
    val weekday: String,
    val title: String,
    val weather: Int,
    val emotion: Int,
    val score: String
)

class DiaryAdapter(private val items: List<DiaryItem>) :
    RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    inner class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDay: TextView = itemView.findViewById(R.id.tvDay)
        val tvWeekday: TextView = itemView.findViewById(R.id.tvWeekday)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val ivWeather: ImageView = itemView.findViewById(R.id.ivWeather)
        val ivEmoition: ImageView = itemView.findViewById(R.id.ivEmotion)
        val tvScore: TextView = itemView.findViewById(R.id.tvScore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_diary_list, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val item = items[position]
        holder.tvDay.text = item.day
        holder.tvWeekday.text = item.weekday
        holder.tvTitle.text = item.title
        holder.ivWeather.setImageResource(item.weather)
        holder.ivEmoition.setImageResource(item.emotion)
        holder.tvScore.text = item.score

    }

    override fun getItemCount(): Int = items.size
}
