package es.unex.giiis.asee.uilabs_m_kotlin

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
        // TODO - Inflate the View for every element
        val v: View? = null
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

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {
        private val title: TextView? = null
        private val statusView: CheckBox? = null
        private val priorityView: TextView? = null
        private val dateView: TextView? = null
        fun bind(toDoItem: ToDoItem?, listener: OnItemClickListener) {

            // TODO - Display Title in TextView


            // TODO - Display Priority in a TextView


            // TODO - Display Time and Date.
            // Hint - use ToDoItem.FORMAT.format(toDoItem.getDate()) to get date and time String


            // TODO - Set up Status CheckBox
            statusView!!.setOnCheckedChangeListener { buttonView, isChecked ->
                // TODO - Set up and implement an OnCheckedChangeListener
                // is called when the user toggles the status checkbox
            }
            itemView.setOnClickListener { listener.onItemClick(toDoItem) }
        }
    }
}
