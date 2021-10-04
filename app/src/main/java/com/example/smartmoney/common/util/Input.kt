package com.example.smartmoney.common.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.smartmoney.R

fun View.bottomErrorMessage(view: View, errorMessage: String) {
    val viewAsViewGroup = view as ViewGroup
    viewAsViewGroup.removeBottomErrorMessage(this)

    val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val itemView = inflater.inflate(R.layout.widget_input_error, null)
    itemView.findViewById<TextView>(R.id.error).text = errorMessage

    viewAsViewGroup.addView(itemView)
}

fun View.removeBottomErrorMessage(view: View) {
    val viewAsViewGroup = view as ViewGroup
    if (viewAsViewGroup.childCount != 1) {
        viewAsViewGroup.removeViewAt(view.childCount-1)
    }
}