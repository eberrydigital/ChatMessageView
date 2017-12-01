package com.eberrydigital.chatview.util


import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * time utility class
 * Created by nakayama on 2016/12/02.
 */
object TimeUtils {

    /***
     * Return formatted text of calendar
     * @param calendar Calendar object to format
     * @param format format text
     * @return formatted text
     */
    @SuppressLint("SimpleDateFormat")
    fun dateToString(date: String, format: String?): String {
        val sdf = SimpleDateFormat(format ?: "HH:mm", Locale.getDefault())
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
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
}
