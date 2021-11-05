package com.example.smartmoney.common

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.smartmoney.R
import kotlin.properties.Delegates

class CustomHeader(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var headerTitle : String by Delegates.observable("") {
            _, _, new ->
        titleView.text = new
        invalidate()
    }

    var headerPageName : String by Delegates.observable("") {
            _, _, new ->
        pageNameView.text = new
        invalidate()
    }

    private lateinit var titleView: TextView
    private lateinit var pageNameView: TextView

    init {
        context?.let {
            inflate(context, R.layout.custom_header, this)
            titleView = this.findViewById(R.id.headerTitle)
            pageNameView = this.findViewById(R.id.headerPageName)

            val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomHeader)
            headerTitle = attributes.getString(R.styleable.CustomHeader_headerTitle)!!
            headerPageName = attributes.getString(R.styleable.CustomHeader_headerPageName)!!
            attributes.recycle()
        }
    }
}