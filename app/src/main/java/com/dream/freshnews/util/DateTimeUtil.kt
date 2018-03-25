package com.dream.freshnews.util

import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lixingming on 25/03/2018.
 */
object DateTimeUtil {

    fun toLocalDateTime(datetime: String, inputFormat: String = "yyyy-MM-dd'T'HH:mm:ss'Z'",
                        outputFormat: String = "yyyy-MM-dd HH:mm:ss"): String {
        val inputDateFormater = SimpleDateFormat(inputFormat)
        inputDateFormater.timeZone = SimpleTimeZone(0, "UTC")

        val outputDateFormater = SimpleDateFormat(outputFormat)

        val date = inputDateFormater.parse(datetime)

        return outputDateFormater.format(date)
    }
}