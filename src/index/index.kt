package index

import kotlinx.html.div
import kotlinx.html.dom.create
import kotlinx.html.js.div
import kotlinx.html.li
import kotlinx.html.p
import kotlinx.html.ul
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.Node
import org.w3c.dom.events.Event
import org.w3c.dom.get
import org.w3c.fetch.CORS
import org.w3c.fetch.RequestInit
import org.w3c.fetch.RequestMode
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.clear
import kotlin.js.Date
import kotlin.js.Promise

val submitButton: Node?
    get() = document.getElementsByName("submit")[0]
val resetButton: Node?
    get() = document.getElementsByName("reset")[0]
val producer: HTMLInputElement?
    get() = document.getElementsByName("producer")[0] as? HTMLInputElement
val ritsuko: HTMLInputElement?
    get() = document.getElementsByName("ritsuko")[0] as? HTMLInputElement
val junjirou: HTMLInputElement?
    get() = document.getElementsByName("junjirou")[0] as? HTMLInputElement
val day: HTMLInputElement?
    get() = document.getElementsByName("day")[0] as? HTMLInputElement
val minHour: HTMLInputElement?
    get() = document.getElementsByName("minHour")[0] as? HTMLInputElement
val minMinute: HTMLInputElement?
    get() = document.getElementsByName("minMinute")[0] as? HTMLInputElement
val maxHour: HTMLInputElement?
    get() = document.getElementsByName("maxHour")[0] as? HTMLInputElement
val maxMinute: HTMLInputElement?
    get() = document.getElementsByName("maxMinute")[0] as? HTMLInputElement

const val THIRTY_MIN = 1800000

fun main() {
    reset()

    submitButton?.addEventListener("click", { event: Event? ->
        val dayValue = day?.value ?: "1"
        val minHourValue = minHour?.value ?: "0"
        val minMinuteValue = minMinute?.value ?: "0"
        val maxHourValue = maxHour?.value ?: "0"
        val maxMinuteValue = maxMinute?.value ?: "0"

        val minTime = dateTimeFormat(day = dayValue, hour = minHourValue, minute = minMinuteValue)
        val maxTime = dateTimeFormat(day = dayValue, hour = maxHourValue, minute = maxMinuteValue)

        val queries = listOf(
                "names[]=${listOfNotNull(producer, ritsuko, junjirou).filter { it.checked }.joinToString("&names[]=") { it.name }}",
                "from=${minTime}%2B09:00",
                "to=${maxTime}%2B09:00"
        )

        request(queries.joinToString("&"))
                .then {
                    document.getElementById("result")?.clear()
                    document.getElementById("result")?.appendChild(
                            resultElement(Date.parse(minTime).toLong(), Date.parse(maxTime).toLong(), it)
                    )
                }
    })

    resetButton?.addEventListener("click", { event: Event? ->
        reset()
    })
}

fun request(query: String): Promise<List<CalendarEvent>> =
        window.fetch(
                "http://localhost:5000/imasbook04-sample/us-central1/schedules?${query}",
                RequestInit(mode = RequestMode.CORS)
        )
                .then { response -> response.json() }
                .then { body -> body.unsafeCast<Array<Array<dynamic>>>().flatten().map { CalendarEvent.fromDynamic(it) } }

fun resultElement(minTime: Long, maxTime: Long, events: List<CalendarEvent>) = document.create.div {
    val producerEvents = events.filter { it.name == "プロデューサーさん" }
    val ritsukoEvents = events.filter { it.name == "律子さん" }
    val junjirouEvents = events.filter { it.name == "社長" }

    fun kotlinx.html.FlowContent.eventSummary(name: String, events: List<CalendarEvent>) {
        div { +"${name}[${events.size}件]" }
        if (events.isNotEmpty()) {
            ul {
                events.forEach {
                    li { +"${it.summary}: ${it.startTimeString()} - ${it.endTimeString()}" }
                }
            }
        }
    }

    fun checkFreeTimes(offset: Long, events: List<CalendarEvent>): Boolean =
            events.filterNot { it.end.getTime().toLong() < offset || (offset + THIRTY_MIN) < it.start.getTime().toLong() }.isEmpty()

    p {
        eventSummary("プロデューサーさん", producerEvents)
        eventSummary("律子さん", ritsukoEvents)
        eventSummary("社長", junjirouEvents)
    }

    val freeTimes: MutableList<Long> = mutableListOf()
    for (offset in minTime..maxTime step 1800000) {
        if (checkFreeTimes(offset, events)) {
            freeTimes.add(offset)
        }
    }

    p {
        if (freeTimes.isNotEmpty()) {
            div { +"この時間が空いてます！" }
            ul {
                freeTimes.forEach { offset ->
                    li { +"${Date(offset).toLocaleTimeString("ja-JP")} - ${Date(offset + THIRTY_MIN).toLocaleTimeString("ja-JP")}" }
                }
            }
        } else {
            div { +"空いている時間が見つかりませんでした🥺" }
        }
    }
}

fun reset(
        checked: Boolean = false,
        dayValue: String = "1",
        minHourValue: String = "12",
        minMinuteValue: String = "0",
        maxHourValue: String = "13",
        maxMinuteValue: String = "0"
) {
    producer?.checked = checked
    ritsuko?.checked = checked
    junjirou?.checked = checked
    day?.value = dayValue
    minHour?.value = minHourValue
    minMinute?.value = minMinuteValue
    maxHour?.value = maxHourValue
    maxMinute?.value = maxMinuteValue
}

fun dateTimeFormat(
        year: String = "2019",
        month: String = "12",
        day: String = "1",
        hour: String = "0",
        minute: String = "0",
        second: String = "0"
): String {
    val y = "000${year}"
    val mon = "0${month}"
    val d = "0${day}"
    val h = "0${hour}"
    val min = "0${minute}"
    val s = "0${second}"

    return y.substring(y.length - 4) + "-" +
            mon.substring(mon.length - 2) + "-" +
            d.substring(d.length - 2) + "T" +
            h.substring(h.length - 2) + ":" +
            min.substring(min.length - 2) + ":" +
            s.substring(s.length - 2)
}
