package index

import kotlin.js.Date

class CalendarEvent(
        val name: String,
        val summary: String,
        val start: Date,
        val end: Date
) {
    companion object {
        fun fromDynamic(obj: dynamic): CalendarEvent = CalendarEvent(
                name = when (obj.organizer.displayName) {
                    "imasbook04_producer" -> "プロデューサーさん"
                    "imasbook04_ritsuko" -> "律子さん"
                    "imasbook04_junjirou" -> "社長"
                    else -> "？？？"
                },
                summary = obj.summary as String,
                start = Date(Date.parse(obj.start.dateTime as String)),
                end = Date(Date.parse(obj.end.dateTime as String))
        )
    }
}
