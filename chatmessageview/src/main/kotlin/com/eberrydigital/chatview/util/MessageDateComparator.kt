package com.eberrydigital.chatview.util


import com.eberrydigital.chatview.models.Message
import java.util.*

class MessageDateComparator : Comparator<Message> {
    override fun compare(first: Message, second: Message): Int {
        if (first.createdAt.before(second.createdAt)) {
            return -1
        }
        return if (first.createdAt.after(second.createdAt)) {
            1
        } else 0
    }
}
