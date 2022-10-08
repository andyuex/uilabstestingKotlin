package es.unex.giiis.asee.uilabs_m_kotlin

import android.content.Intent
import java.text.SimpleDateFormat
import java.util.*

// Do not modify

class ToDoItem {
    enum class Priority {
        LOW, MED, HIGH
    }

    enum class Status {
        NOT_DONE, DONE
    }

    var title: String? = String()
    var priority = Priority.LOW
    var status = Status.NOT_DONE
    var date = Date()

    internal constructor(title: String?, priority: Priority, status: Status, date: Date) {
        this.title = title
        this.priority = priority
        this.status = status
        this.date = date
    }

    // Create a new ToDoItem from data packaged in an Intent
    internal constructor(intent: Intent) {
        title = intent.getStringExtra(TITLE)
        priority = Priority.valueOf(intent.getStringExtra(PRIORITY) ?: Priority.MED.toString())
        status = Status.valueOf(intent.getStringExtra(STATUS) ?: Status.NOT_DONE.toString())
        date = FORMAT.parse(intent.getStringExtra(DATE) ?: Date().toString()) as Date
    }

    override fun toString(): String {
        return (title + ITEM_SEP + priority + ITEM_SEP + status + ITEM_SEP
                + FORMAT.format(date))
    }

    fun toLog(): String {
        return ("Title:" + title + ITEM_SEP + "Priority:" + priority
                + ITEM_SEP + "Status:" + status + ITEM_SEP + "Date:"
                + FORMAT.format(date))
    }

    companion object {
        val ITEM_SEP = System.getProperty("line.separator") as String
        const val TITLE = "title"
        const val PRIORITY = "priority"
        const val STATUS = "status"
        const val DATE = "date"
        const val FILENAME = "filename"
        val FORMAT = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US
        )

        // Take a set of String data values and
        // package them for transport in an Intent
        fun packageIntent(
            intent: Intent, title: String?,
            priority: Priority, status: Status, date: String?
        ) {
            intent.putExtra(TITLE, title)
            intent.putExtra(PRIORITY, priority.toString())
            intent.putExtra(STATUS, status.toString())
            intent.putExtra(DATE, date)
        }
    }
}
