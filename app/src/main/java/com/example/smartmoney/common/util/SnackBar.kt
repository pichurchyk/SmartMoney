package com.example.smartmoney.common.util

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun Context.snackbar(view: View, text: String) {

    hideKeyboard(view)

    val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
    snackbar.show()
}