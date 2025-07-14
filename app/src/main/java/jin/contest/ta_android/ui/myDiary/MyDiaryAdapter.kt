package jin.contest.ta_android.ui.myDiary

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import jin.contest.ta_android.R
import android.util.Log
import android.view.View.OnClickListener
import android.widget.AdapterView
import androidx.compose.ui.tooling.preview.Preview
import jin.contest.ta_android.databinding.ItemDiaryListBinding


data class DiaryItem(
    val day: String,
    val weekday: String,
    val title: String,
    val preview: String,
    val weather: Int,
    val emotion: Int,
    val score: String,
    val id: Long
)

data class DiaryInfo(
    val title: String,
    val createdAt: String,
    val weather: Int,
    val content: String,
    val emotion: Int,
    val emotionPoint: String,
)

interface OnItemClickListener {
    fun onItemClick(position: Int)
}

class MyDiaryAdapter(private var items: List<DiaryItem>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<MyDiaryAdapter.DiaryViewHolder>() {
    fun updateItems(newItems: List<DiaryItem>) {
        items = newItems
        notifyDataSetChanged()
    }
    inner class DiaryViewHolder(private val binding: ItemDiaryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var id :Long = 0
        fun bind(item: DiaryItem) {
            binding.tvDay.text = item.day
            binding.tvWeekday.text = item.weekday
            binding.tvTitle.text = item.title
            binding.tvPreview.text = item.preview.plus("..")
            binding.ivWeather.setImageResource(item.weather)
            binding.ivEmotion.setImageResource(item.emotion)
            binding.tvScore.text = item.score
            }
            init {
            binding.root.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val binding = ItemDiaryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

