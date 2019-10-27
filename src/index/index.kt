package index

import org.w3c.dom.HTMLInputElement
import org.w3c.dom.get
import kotlin.browser.document

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
    producer?.checked = false
    ritsuko?.checked = false
    junjirou?.checked = false
    day?.value = "1"
    minHour?.value = "12"
    minMinute?.value = "0"
    maxHour?.value = "13"
    maxMinute?.value = "0"
}
