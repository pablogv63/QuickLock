package com.pablogv63.quicklock.ui.credentials.add.components

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.util.*

object ExpirationDatePickerDialog {
    operator fun invoke(
        context: Context,
        onDialogOk: (String) -> Unit
    ): DatePickerDialog {
        // Date picker
        // From: https://www.geeksforgeeks.org/date-picker-in-android-using-jetpack-compose/
        // Declaring integer values
        // for year, month and day
        val mYear: Int
        val mMonth: Int
        val mDay: Int

        // Initializing a Calendar
        val mCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()

        // Declaring DatePickerDialog and setting
        // initial values as current values (present year, month and day)
        val mDatePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                onDialogOk("$mDayOfMonth/${mMonth + 1}/$mYear")
            }, mYear, mMonth, mDay
        )
        return mDatePickerDialog
    }
}