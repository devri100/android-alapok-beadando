package hu.korsosrichard.demoapp.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import hu.korsosrichard.demoapp.viewmodels.CoreViewModel

open class RemovingItemTouchHelperCallback<T>(
    val fragment: Fragment,
    val viewModel: CoreViewModel<T>
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return true;
    }

    open fun getData(position: Int): T = viewModel.list.value!!.data!![position]

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val data = getData(viewHolder.adapterPosition)
        viewModel.delete(data).observe(fragment.viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                Snackbar.make(fragment.requireView(), "Book removed", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        viewModel.insert(data).observe(fragment.viewLifecycleOwner, Observer {

                        })
                    }.show()
            }
        })
    }
}