package com.example.smartmoney.common

import org.joda.time.DateTime
import java.util.*

class DateTimeParser() {

    private var dateInMillis: Long = DateTime.now().millis

    fun getDateAsString(dayOfMonth: Int, month: Int, year: Int): String {
        val defaultDate = DateTime.now()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)

        val userDate = DateTime(calendar.timeInMillis)

        return when (userDate.millis > defaultDate.millis) {
            true -> {
                val defaultDay = defaultDate.dayOfMonth().asText
                val defaultMonth = defaultDate.monthOfYear().asShortText
                val defaultYear = defaultDate.year().asText
                "$defaultDay $defaultMonth $defaultYear"
            }
            false -> {
                val userDay = userDate.dayOfMonth().asText
                val userMonth = userDate.monthOfYear().asShortText
                val userYear = userDate.year().asText
                "$userDay $userMonth $userYear"
            }
        }
    }

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