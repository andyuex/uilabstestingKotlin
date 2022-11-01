package es.unex.giiis.asee.uilabs_m_testing_kotlin

open class ToDoItemList {
    private var elements: MutableList<ToDoItem> = ArrayList()

    fun addItem(item: ToDoItem) {
        elements.add(item)
    }

    fun deleteAllItems() {
        elements.clear()
    }

    fun updateItem(position: Int, item: ToDoItem?) {
        val itemAtPosition = elements[position] as ToDoItem
        itemAtPosition.status = ToDoItem.Status.NOT_DONE
    }

    operator fun get(position: Int): ToDoItem {
        return elements[position]
    }

    fun size(): Int {
        return elements.size
    }
}
