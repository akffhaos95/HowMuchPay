import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.howmuch.Member
import com.example.howmuch.R

class SubAdapter(private val context: Context) :
    RecyclerView.Adapter<SubAdapter.Holder>() {
    private var member: List<Member> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.result_member_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return member.size
    }

    override fun onBindViewHolder(viewHolder: Holder, position: Int) {
        viewHolder.bind(member[position])
    }

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        private val groupName = itemView?.findViewById<CheckBox>(R.id.member_name)

        fun bind(member:Member) {
            groupName?.text = member.name
        }
    }
}