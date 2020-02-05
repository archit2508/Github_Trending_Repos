package com.example.top_github.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager

class AppUtils{

    companion object{
        fun hideKeyboard(activity: Activity){
            val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            if (activity.currentFocus != null)
                inputMethodManager!!.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken, 0
                )
        }
    }
}