package es.unex.giiis.asee.uilabs_m_testing_kotlin

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import es.unex.giiis.asee.uilabs_m_testing_kotlin.ToDoItem.Priority
import es.unex.giiis.asee.uilabs_m_testing_kotlin.ToDoItem.Status
import java.util.*

class AddToDoActivity : AppCompatActivity() {
    private var mDate: Date? = null
    private var mPriorityRadioGroup: RadioGroup? = null
    private var mStatusRadioGroup: RadioGroup? = null
    private var mTitleText: EditText? = null
    private var mDefaultStatusButton: RadioButton? = null
    private var mDefaultPriorityButton: RadioButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)
        mTitleText = findViewById<View>(R.id.title) as EditText
        mDefaultStatusButton = findViewById<View>(R.id.statusNotDone) as RadioButton
        mDefaultPriorityButton = findViewById<View>(R.id.medPriority) as RadioButton
        mPriorityRadioGroup = findViewById<View>(R.id.priorityGroup) as RadioGroup
        mStatusRadioGroup = findViewById<View>(R.id.statusGroup) as RadioGroup
        dateView = findViewById<View>(R.id.date) as TextView
        timeView = findViewById<View>(R.id.time) as TextView

        // Set the default date and time
        setDefaultDateTime()

        // OnClickListener for the Date button, calls showDatePickerDialog() to show
        // the Date dialog
        val datePickerButton = findViewById<View>(R.id.date_picker_button) as Button
        datePickerButton.setOnClickListener { showDatePickerDialog() }

        // OnClickListener for the Time button, calls showTimePickerDialog() to show
        // the Time Dialog
        val timePickerButton = findViewById<View>(R.id.time_picker_button) as Button
        timePickerButton.setOnClickListener { showTimePickerDialog() }

        // OnClickListener for the Cancel Button,
        val cancelButton = findViewById<View>(R.id.cancelButton) as Button
        cancelButton.setOnClickListener {
            log("Entered cancelButton.OnClickListener.onClick()")
            setResult(RESULT_CANCELED)
            finish()
        }

        //OnClickListener for the Reset Button
        val resetButton = findViewById<View>(R.id.resetButton) as Button
        resetButton.setOnClickListener {
            log("Entered resetButton.OnClickListener.onClick()")

            mTitleText?.setText("")
            mStatusRadioGroup?.check(mDefaultStatusButton?.id!!)
            mPriorityRadioGroup?.check(mDefaultPriorityButton?.id!!)
            setDefaultDateTime()
        }

        // OnClickListener for the Submit Button
        // Implement onClick().
        val submitButton = findViewById<View>(R.id.submitButton) as Button
        submitButton.setOnClickListener {
            log("Entered submitButton.OnClickListener.onClick()")

            // Gather ToDoItem data
            val priority = priority
            val status = status
            val title = mTitleText?.text.toString()
            val date = "$dateString $timeString"

            val intent = Intent()
            ToDoItem.packageIntent(intent, title, priority, status, date)

            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun showDatePickerDialog() {
        val datePickerFragment = DatePickerFragment()
        datePickerFragment.show(supportFragmentManager, "datePickerFragment")
    }

    private fun showTimePickerDialog() {
        val timePickerFragment = TimePickerFragment()
        timePickerFragment.show(supportFragmentManager, "timePickerFragment")
    }

    // Do not modify below here
    // Use this method to set the default date and time
    private fun setDefaultDateTime() {

        // Default is current time + 7 days
        mDate = Date()
        mDate = Date(mDate!!.time + SEVEN_DAYS)
        val c = Calendar.getInstance()
        c.time = mDate
        setDateString(
            c[Calendar.YEAR], c[Calendar.MONTH],
            c[Calendar.DAY_OF_MONTH]
        )
        dateView!!.text = dateString
        setTimeString(
            c[Calendar.HOUR_OF_DAY], c[Calendar.MINUTE],
            c[Calendar.MILLISECOND]
        )
        timeView!!.text = timeString
    }

    private val priority: Priority
        get() = when (mPriorityRadioGroup!!.checkedRadioButtonId) {
            R.id.lowPriority -> {
                Priority.LOW
            }
            R.id.highPriority -> {
                Priority.HIGH
            }
            else -> {
                Priority.MED
            }
        }
    private val status: Status
        get() {
            return when (mStatusRadioGroup!!.checkedRadioButtonId) {
                R.id.statusDone -> {
                    Status.DONE
                }
                else -> {
                    Status.NOT_DONE
                }
            }
        }

    // DialogFragment used to pick a ToDoItem deadline date
    class DatePickerFragment : DialogFragment(), OnDateSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            // Use the current date as the default date in the picker
            val c = Calendar.getInstance()
            val year = c[Calendar.YEAR]
            val month = c[Calendar.MONTH]
            val day = c[Calendar.DAY_OF_MONTH]

            // Create a new instance of DatePickerDialog and return it
            return DatePickerDialog(requireContext(), this, year, month, day)
        }

        override fun onDateSet(
            view: DatePicker, year: Int, monthOfYear: Int,
            dayOfMonth: Int
        ) {
            setDateString(year, monthOfYear, dayOfMonth)
            dateView!!.text = dateString
        }
    }

    // DialogFragment used to pick a ToDoItem deadline time
    class TimePickerFragment : DialogFragment(), OnTimeSetListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            // Use the current time as the default values for the picker
            val c = Calendar.getInstance()
            val hour = c[Calendar.HOUR_OF_DAY]
            val minute = c[Calendar.MINUTE]

            // Create a new instance of TimePickerDialog and return
            return TimePickerDialog(
                activity, this, hour, minute,
                true
            )
        }

        override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
            setTimeString(hourOfDay, minute, 0)
            timeView!!.text = timeString
        }
    }

    private fun log(msg: String) {
        try {
            Thread.sleep(500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.i(TAG, msg)
    }

    companion object {
        // 7 days in milliseconds - 7 * 24 * 60 * 60 * 1000
        private const val SEVEN_DAYS = 604800000
        private const val TAG = "Lab-UserInterface"
        private var timeString: String? = null
        private var dateString: String? = null
        private var dateView: TextView? = null
        private var timeView: TextView? = null
        private fun setDateString(year: Int, monthOfYear: Int, dayOfMonth: Int) {

            // Increment monthOfYear for Calendar/Date -> Time Format setting
            var monthOfYear = monthOfYear
            monthOfYear++
            var mon = "" + monthOfYear
            var day = "" + dayOfMonth
            if (monthOfYear < 10) mon = "0$monthOfYear"
            if (dayOfMonth < 10) day = "0$dayOfMonth"
            dateString = "$year-$mon-$day"
        }

        private fun setTimeString(hourOfDay: Int, minute: Int, mili: Int) {
            var hour = "" + hourOfDay
            var min = "" + minute
            if (hourOfDay < 10) hour = "0$hourOfDay"
            if (minute < 10) min = "0$minute"
            timeString = "$hour:$min:00"
        }
    }
}
