package index

import org.w3c.dom.HTMLInputElement
import org.w3c.dom.Node
import org.w3c.dom.events.Event
import org.w3c.dom.get
import kotlin.browser.document

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

fun main() {
    reset()

    resetButton?.addEventListener("click", { event: Event? ->
        reset()
    })
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
