package hu.korsosrichard.demoapp.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

interface AdapterModel {
    fun getAdapterId(): Long
    fun getAdapterText(): String
}

class CoreAdapter<T : AdapterModel>(val list: List<T>, val first: String? = null) : BaseAdapter() {
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val newView: View
        newView = if (view == null) {
            val inflater = LayoutInflater.from(parent!!.context)
            inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        } else {
            view
        }

        newView.findViewById<TextView>(android.R.id.text1).text =
            if (position == 0 && first != null) first else list[position - getDelta()].getAdapterText()
        return newView
    }

    override fun getItem(position: Int): Any? {
        return if (position == 0 && first != null) null else list[position - getDelta()]
    }

    override fun getItemId(position: Int): Long {
        return if (position == 0 && first != null) -1L else list[position - getDelta()].getAdapterId()
    }

    override fun getCount(): Int {
        return list.size + getDelta()
    }

    private fun getDelta() = if (first == null) 0 else 1


}