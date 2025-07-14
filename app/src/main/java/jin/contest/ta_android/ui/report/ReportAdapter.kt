package jin.contest.ta_android.ui.report
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import jin.contest.ta_android.R
import android.view.LayoutInflater


data class ReportItem(
    val title: String,
    val content: String,
    val type: String,
    val startDate: String,
    val endDate: String,
    val depression: Int,
    val anger: Int,
    val happy: Int,
    val riskScore: Int
)

class ReportAdapter(
    private var items: List<ReportItem>,
    private val onItemClick: ((ReportItem) -> Unit)? = null
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvDepression: TextView = itemView.findViewById(R.id.tvDepression)
        private val tvAnger: TextView = itemView.findViewById(R.id.tvAnger)
        private val tvHappy: TextView = itemView.findViewById(R.id.tvHappy)
        private val tvRiskScore: TextView = itemView.findViewById(R.id.tvRiskScore)
        private val tvContent: TextView = itemView.findViewById(R.id.tvContent)

        fun bind(item: ReportItem) {
            tvTitle.text = item.title
            tvDepression.text = "우울: ${item.depression}"
            tvAnger.text = "분노: ${item.anger}"
            tvHappy.text = "행복: ${item.happy}"
            tvRiskScore.text = "위험 점수: ${item.riskScore}"
            tvContent.text = item.content

            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<ReportItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
