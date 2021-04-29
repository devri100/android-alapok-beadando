package hu.korsosrichard.demoapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import hu.korsosrichard.demoapp.R
import hu.korsosrichard.demoapp.utils.MyResult
import hu.korsosrichard.demoapp.utils.back
import hu.korsosrichard.demoapp.utils.hideKeyboard

abstract class FormFragment<T> : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    hideKeyboard(requireContext(), requireView())
                    AlertDialog.Builder(requireContext())
//            .setTitle("")
                        .setMessage("Are you sure you want to leave without saving?")
                        .setPositiveButton("Leave") { _, _ ->
                            back()
                        }
                        .setNegativeButton("Save changes") { _, _ ->
                            doSave()
                        }
                        .create().show()
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_form, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save) {
            doSave()
        }
        return true
    }

    private fun doSave() {
        if (validate()) {
            save()
            //TODO keyboard hide
        }
    }

    abstract fun validate(): Boolean

    abstract fun save()

    inner class ResultObserver : Observer<MyResult<T>> {
        override fun onChanged(result: MyResult<T>?) {
            if (result != null) {
                if (result.isSuccess) {
                    back()
                } else if (result.isFailure) {
                    AlertDialog.Builder(requireContext())
                        .setMessage("Saving failed, try again.")
                        .setPositiveButton("Ok", null)
                        .create().show()
                }
            }
        }
    }
}