package com.example.smartmoney.common.util

import android.view.View

fun View.toggleVisibility(isVisible: Boolean = false, notVisible: Int = View.GONE) {
    this.visibility = if (isVisible) View.VISIBLE else notVisible
}