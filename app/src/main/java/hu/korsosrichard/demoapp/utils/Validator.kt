package hu.korsosrichard.demoapp.utils

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import hu.korsosrichard.demoapp.R


object Validator {
    fun asRequired(text: CharSequence, errorView: TextView): Boolean {
        return asRequired(!TextUtils.isEmpty(text), errorView)
    }


    fun asRequired(isValid: Boolean, errorView: TextView): Boolean {
        errorView.text = errorView.context.getString(R.string.required_field)
        return if(!isValid){
            errorView.visibility = View.VISIBLE
            false
        } else {
            errorView.visibility = View.GONE
            true
        }
    }


}



