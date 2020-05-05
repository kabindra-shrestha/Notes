package com.kabindra.note.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.util.*

class Utils {

    companion object {
        fun showSnackBar(rootView: View, mMessage: String) {
            Snackbar.make(rootView, mMessage, Snackbar.LENGTH_LONG).show()
        }

        fun showToast(context: Context, mMessage: String) {
            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show()
        }

        fun getDateAndTime(createdAt: Long?): String {
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = createdAt!!

            val mYear: Int = calendar.get(Calendar.YEAR)
            val mMonth: Int = calendar.get(Calendar.MONTH) + 1
            val mDay: Int = calendar.get(Calendar.DAY_OF_MONTH)
            val mHour: Int = calendar.get(Calendar.HOUR)
            val mMinutes: Int = calendar.get(Calendar.MINUTE)
            val mAMPM: Int = calendar.get(Calendar.AM_PM)

            val mAMPMString: String
            if (mAMPM == 0) {
                mAMPMString = "AM";
            } else {
                mAMPMString = "PM";
            }

            return "$mMonth/$mDay/$mYear $mHour:$mMinutes $mAMPMString"
        }
    }

}