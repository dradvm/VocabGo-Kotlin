package com.example.vocabgo.common.helpers

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class DateHelper {
    companion object {
        fun formatJoinDate(dateString: String): String {
            // Parse theo ISO-8601 (tự hiểu T và Z)
            val dateTime = OffsetDateTime.parse(dateString, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

            val months = listOf(
                "một", "hai", "ba", "tư", "năm", "sáu",
                "bảy", "tám", "chín", "mười", "mười một", "mười hai"
            )

            val monthName = months[dateTime.monthValue - 1]
            val year = dateTime.year

            return "Đã tham gia tháng $monthName $year"
        }
    }
}