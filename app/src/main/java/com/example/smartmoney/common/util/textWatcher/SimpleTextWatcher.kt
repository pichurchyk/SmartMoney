package com.example.smartmoney.common.util.textWatcher

import android.text.Editable

class SimpleTextWatcher(private val afterTextChangedCallback: (s: Editable?) -> (Unit))
    : TextWatcherAdapter() {
    override fun afterTextChanged(s: Editable?) {
        afterTextChangedCallback(s)
    }
}