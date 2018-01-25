package com.eberrydigital.chatview.util


import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * time utility class
 * Created by nakayama on 2016/12/02.
 */
object TimeUtils {
    var DATEFORMAT: String = "yyyy-MM-dd'T'HH:mm:ss"
    /***
     * Return formatted text of calendar
     * @param calendar Calendar object to format
     * @param format format text
     * @return formatted text
     */
    @SuppressLint("SimpleDateFormat")
    fun dateToString(date: String, format: String?): String {
        val sdf = SimpleDateFormat(format ?: "HH:mm")
        return sdf.format(date)
    }

    /**
     * Return time difference days
     * @param prev previous date
     * @param target target date
     * @return time difference days
     */
    @JvmStatic
    fun getDiffDays(prev: Calendar, target: Calendar): Int {
        val timeDiff = prev.timeInMillis - target.timeInMillis
        val millisOfDay = 1000 * 60 * 60 * 24
        return (timeDiff / millisOfDay).toInt()
    }

    @Throws(Exception::class)
    fun getParsedDate(date: String, format: String? = "MMM. dd, yyyy"): String? {
        val sdf = SimpleDateFormat(DATEFORMAT, Locale.getDefault())
        var s2: String? = null
        val d: Date
        try {
            d = sdf.parse(date)
            s2 = SimpleDateFormat(format).format(d)

        } catch (e: ParseException) {

            e.printStackTrace()
        }
        return s2
    }

    @JvmOverloads
    fun getCurrentDateTimeAsString(chosenTimezone: TimeZone = TimeZone.getDefault()): String {
        val sdf = SimpleDateFormat(DATEFORMAT).apply {
            timeZone = chosenTimezone
        }
        return sdf.format(Date())
    }
}
