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

}
