package com.dream.freshnews.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lixingming on 25/03/2018.
 */
object DateTimeUtil {

    fun toLocalDateTime(datetime: String, inputFormat: String = "yyyy-MM-dd'T'HH:mm:ss'Z'",
                        outputFormat: String = "yyyy-MM-dd HH:mm:ss"): String {
        var result: String
        try {
            val inputDateFormater = SimpleDateFormat(inputFormat, Locale.US)
            inputDateFormater.timeZone = SimpleTimeZone(0, "UTC")

            val outputDateFormater = SimpleDateFormat(outputFormat, Locale.US)

            val date = inputDateFormater.parse(datetime)

            result = outputDateFormater.format(date!!)
        } catch (e: Exception) {
            result = if (datetime.length > 10) datetime.substring(0, 10) else ""
        }

        return result
    }
}