package com.example.smartmoney.common

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.smartmoney.R
import kotlin.properties.Delegates

class CustomSettingsSection(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    var title : String by Delegates.observable("") {
        _, _, new ->
        titleView.text = new
        invalidate()
    }

    var text : String by Delegates.observable("") {
            _, _, new ->
        textView.text = new
        invalidate()
    }

    var isEditable : Boolean by Delegates.observable(false) {
        _, _, new ->
        if (new) {
            editBtn.visibility = View.VISIBLE
        }
        else {
            editBtn.visibility = View.GONE
        }
    }

    private lateinit var titleView: TextView
    private lateinit var textView: TextView
    private lateinit var editBtn: TextView

    init {
        context?.let {
            inflate(context, R.layout.custom_settings_section, this)
            titleView = this.findViewById(R.id.sectionTitle)
            textView = this.findViewById(R.id.sectionText)
            editBtn = this.findViewById(R.id.edit)

            val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomSettingsSection)
            title = attributes.getString(R.styleable.CustomSettingsSection_title)!!
            text = attributes.getString(R.styleable.CustomSettingsSection_text)!!
            isEditable = attributes.getBoolean(R.styleable.CustomSettingsSection_isEditable, false)
            attributes.recycle()
        }
    }
}