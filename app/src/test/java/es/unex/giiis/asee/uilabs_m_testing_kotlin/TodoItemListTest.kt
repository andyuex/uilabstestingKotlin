package es.unex.giiis.asee.uilabs_m_testing_kotlin

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class TodoItemListTest {
    private var toDoItemList: ToDoItemList? = null
    @Test
    fun shouldAddItemToList() {
        // TODO - Probar método addItem - Insertar elemento
        toDoItemList!!.addItem(
            ToDoItem(
                "TEST",
                ToDoItem.Priority.MED,
                ToDoItem.Status.NOT_DONE,
                Date(2018, 1, 1)
            )
        )

        // TODO - Probar método addItem - Comprobar en la lista que ha sido correctamente insertado
        assertEquals(toDoItemList!!.elements.size, 1)
        assertEquals(toDoItemList!!.elements[0].date, Date(2018, 1, 1))
        assertEquals(toDoItemList!!.elements[0].status, ToDoItem.Status.NOT_DONE)
        assertEquals(toDoItemList!!.elements[0].priority, ToDoItem.Priority.MED)
        assertEquals(toDoItemList!!.elements[0].title, "TEST")
    }

    @Test
    fun shouldUpdateItemOnList() {
        toDoItemList!!.addItem(
            ToDoItem(
                "TEST",
                ToDoItem.Priority.MED,
                ToDoItem.Status.NOT_DONE,
                Date(2018, 1, 1)
            )
        )
        val item = ToDoItem("TEST", ToDoItem.Priority.MED, ToDoItem.Status.DONE, Date(2018, 1, 1))
        toDoItemList!!.updateItem(0, item)
        assertEquals(toDoItemList!!.elements[0].status, ToDoItem.Status.DONE)
    }

    @Test
    fun shouldDeleteAllItemsOnList() {
        toDoItemList!!.addItem(
            ToDoItem(
                "TEST1",
                ToDoItem.Priority.MED,
                ToDoItem.Status.NOT_DONE,
                Date(2018, 1, 1)
            )
        )
        toDoItemList!!.addItem(
            ToDoItem(
                "TEST2",
                ToDoItem.Priority.HIGH,
                ToDoItem.Status.NOT_DONE,
                Date(2018, 1, 1)
            )
        )
        toDoItemList!!.addItem(
            ToDoItem(
                "TEST3",
                ToDoItem.Priority.LOW,
                ToDoItem.Status.NOT_DONE,
                Date(2018, 1, 1)
            )
        )
        toDoItemList!!.addItem(
            ToDoItem(
                "TEST4",
                ToDoItem.Priority.MED,
                ToDoItem.Status.DONE,
                Date(2018, 1, 1)
            )
        )
        toDoItemList!!.deleteAllItems()
        assertEquals(toDoItemList!!.elements.size, 0)
    }

    @Test
    fun shouldGetCorrectItemFromList() {
        val numberOfItems = 5
        for (i in 0 until numberOfItems) {
            toDoItemList!!.addItem(
                ToDoItem(
                    "TEST $i",
                    ToDoItem.Priority.MED,
                    ToDoItem.Status.NOT_DONE,
                    Date(2018, 1, 1)
                )
            )
        }
        for (i in 0 until 5) {
            val item = toDoItemList!![i]
            assertEquals("TEST $i", item.title)
        }
    }

    @Before
    fun initTest() {
        toDoItemList = ToDoItemList()
    }
}
