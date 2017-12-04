package com.eberrydigital.chatview.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Date formatter of chat timeline separator.
 * Created by nakayama on 2017/01/13.
 */
class DateFormatter : ITimeFormatter {

    override fun getFormattedTimeText(createdAt: String): String {
        return TimeUtils.dateToString(createdAt, "yyyy-MM-dd'T'HH:mm:ss'Z'");
    }

    @Throws(Exception::class)
    override fun getParsedDate(date: String): String? {
        val sdf = SimpleDateFormat(DATEFORMAT, Locale.getDefault())
        var s2: String? = null
        val d: Date
        try {
            d = sdf.parse(date)
            s2 = SimpleDateFormat("MMM. dd, yyyy").format(d)

        } catch (e: ParseException) {

            e.printStackTrace()
        }
        return s2
    }

    override fun  getUTCdatetimeAsString(): String
    {
        val sdf =  SimpleDateFormat(DATEFORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        val utcTime = sdf.format( Date());
        return utcTime;
    }
}
