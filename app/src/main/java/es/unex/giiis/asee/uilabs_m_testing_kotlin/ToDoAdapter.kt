package es.unex.giiis.asee.uilabs_m_testing_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToDoAdapter     // Provide a suitable constructor (depends on the kind of dataset)
    (private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {
    private val mItems: MutableList<ToDoItem> = ArrayList<ToDoItem>()

    interface OnItemClickListener {
        fun onItemClick(item: ToDoItem?) //Type of the element to be returned
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.to_do_item, parent, false)
        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems[position], listener)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return mItems.size
    }

    fun add(item: ToDoItem) {
        mItems.add(item)
        notifyDataSetChanged()
    }

    fun clear() {
        mItems.clear()
        notifyDataSetChanged()
    }

    fun getItem(pos: Int): Any {
        return mItems[pos]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title: TextView? = null
        private var statusView: CheckBox? = null
        private var priorityView: TextView? = null
        private var dateView: TextView? = null

        init {
            title = itemView.findViewById(R.id.titleView)
            statusView = itemView.findViewById(R.id.statusCheckBox)
            priorityView = itemView.findViewById(R.id.priorityView)
            dateView = itemView.findViewById(R.id.dateView)
        }

        fun bind(toDoItem: ToDoItem?, listener: OnItemClickListener) {
            title?.text = toDoItem?.title
            priorityView?.text = toDoItem?.priority.toString()
            dateView?.text = ToDoItem.FORMAT.format(toDoItem?.date!!)
            statusView?.isChecked = toDoItem.status == ToDoItem.Status.DONE

            statusView!!.setOnCheckedChangeListener { _, isChecked ->
                // is called when the user toggles the status checkbox
                toDoItem.status = if (isChecked) {
                    ToDoItem.Status.DONE
                } else {
                    ToDoItem.Status.NOT_DONE
                }
            }
            itemView.setOnClickListener { listener.onItemClick(toDoItem) }
        }
    }
}
