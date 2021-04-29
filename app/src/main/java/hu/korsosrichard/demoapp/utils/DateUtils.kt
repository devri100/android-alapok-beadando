package hu.korsosrichard.demoapp.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.core.content.ContentProviderCompat.requireContext
import java.text.SimpleDateFormat
import java.util.*

fun defaultDateFormat() = SimpleDateFormat("yyyy.MM.dd.", Locale("hu"))

fun showDatePickerDialog(context: Context, date: Date?, onDatePick: (Date) -> Unit){
    val calendar = Calendar.getInstance(Locale("hu"))
    calendar.time = date ?: Date()

    DatePickerDialog(
        context,
        DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            onDatePick(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}