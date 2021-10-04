package com.example.smartmoney.common.util

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.google.android.material.dialog.MaterialDialogs

fun Context.dialogBuilder(owner: LifecycleOwner, title: Int) : MaterialDialog {
    return MaterialDialog(this)
        .lifecycleOwner(owner)
        .title(title)
        .cornerRadius(12F)
        .cancelOnTouchOutside(false)
}