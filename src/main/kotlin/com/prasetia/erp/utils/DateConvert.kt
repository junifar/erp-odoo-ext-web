package com.prasetia.erp.utils

import java.text.SimpleDateFormat
import java.util.*

fun toSimpleString(date: Date?): String? = with(date ?: Date()) {
    SimpleDateFormat("M").format(this)
}
fun toSimpleDate(date: Date?): String? = with(date ?: Date()) {
    SimpleDateFormat("dd/MM/yyy").format(this)
}