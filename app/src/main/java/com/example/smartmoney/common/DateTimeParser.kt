package com.example.smartmoney.common

import org.joda.time.DateTime
import java.util.*

class DateTimeParser() {

    private var dateInMillis: Long = DateTime.now().millis

    fun isDateValid() : Boolean {
        return dateInMillis < DateTime.now().millis
    }

    fun getDateAsString(millis: Long): String {
        val userDate = DateTime(millis)
        val defaultDay = userDate.dayOfMonth().asText
        val defaultMonth = userDate.monthOfYear().asShortText
        val defaultYear = userDate.year().asText
        return "$defaultDay $defaultMonth $defaultYear"

    }

    fun getDateInMills(): Long {
        return dateInMillis!!
    }

    fun setTimeInMills(dayOfMonth: Int, month: Int, year: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)

        dateInMillis = calendar.timeInMillis
    }
}