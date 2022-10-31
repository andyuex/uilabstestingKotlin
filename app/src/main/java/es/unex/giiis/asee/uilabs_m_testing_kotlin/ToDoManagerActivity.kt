package es.unex.giiis.asee.uilabs_m_testing_kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.io.*
import java.text.ParseException
import java.util.*

class ToDoManagerActivity : AppCompatActivity() {
    private var mRecyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var mAdapter: ToDoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_manager)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            val intent = Intent(this, AddToDoActivity::class.java)
            startActivityForResult(intent, ADD_TODO_ITEM_REQUEST)
        }

        mRecyclerView = findViewById(R.id.my_recycler_view)

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView!!.setHasFixedSize(true)

        // use a linear layout manager
        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView!!.layoutManager = mLayoutManager

        mAdapter = ToDoAdapter(object : ToDoAdapter.OnItemClickListener {
            override fun onItemClick(item: ToDoItem?) {
                Log.i("onItemClick", "Item " + item?.title + " clicked")
                Snackbar.make(mRecyclerView!!, "Item " + item?.title + " clicked", Snackbar.LENGTH_SHORT).show()
            }
        })
        mRecyclerView?.adapter = mAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        log("Entered onActivityResult()")

        if (requestCode == ADD_TODO_ITEM_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                val toDoItem = ToDoItem(data)
                mAdapter?.add(toDoItem)
            }
        }
    }

    public override fun onResume() {
        super.onResume()

        // Load saved ToDoItems, if necessary
        if (mAdapter?.itemCount == 0) {
            loadItems()
        }
    }

    override fun onPause() {
        super.onPause()

        // Save ToDoItems
        saveItems()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all")
        menu.add(Menu.NONE, MENU_DUMP, Menu.NONE, "Dump to log")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            MENU_DELETE -> {
                mAdapter?.clear()
                true
            }
            MENU_DUMP -> {
                dump()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun dump() {
        for (i in 0 until mAdapter?.itemCount!!) {
            val data: String = (mAdapter?.getItem(i) as ToDoItem).toLog()
            log("Item " + i + ": " + data.replace(ToDoItem.ITEM_SEP, ","))
        }
    }

    // Load stored ToDoItems
    private fun loadItems() {
        var reader: BufferedReader? = null
        try {
            val fis = openFileInput(FILE_NAME)
            reader = BufferedReader(InputStreamReader(fis))
            var title: String? = null
            var priority: String? = null
            var status: String? = null
            var date: Date? = null
            while (null != reader.readLine().also { title = it }) {
                priority = reader.readLine()
                status = reader.readLine()
                date = ToDoItem.FORMAT.parse(reader.readLine())
                mAdapter?.add(
                    ToDoItem(
                        title, ToDoItem.Priority.valueOf(priority),
                        ToDoItem.Status.valueOf(status), date
                    )
                )
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ParseException) {
            e.printStackTrace()
        } finally {
            if (null != reader) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    // Save ToDoItems to file
    private fun saveItems() {
        var writer: PrintWriter? = null
        try {
            val fos = openFileOutput(FILE_NAME, MODE_PRIVATE)
            writer = PrintWriter(
                BufferedWriter(
                    OutputStreamWriter(
                        fos
                    )
                )
            )
            for (idx in 0 until mAdapter?.itemCount!!) {
                writer.println(mAdapter?.getItem(idx))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            writer?.close()
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
        // Add a ToDoItem Request Code
        private const val ADD_TODO_ITEM_REQUEST = 0
        private const val FILE_NAME = "TodoManagerActivityData.txt"
        private const val TAG = "Lab-UserInterface"

        // IDs for menu items
        private const val MENU_DELETE = Menu.FIRST
        private const val MENU_DUMP = Menu.FIRST + 1
    }
}
