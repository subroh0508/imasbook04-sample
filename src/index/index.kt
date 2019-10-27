package index

import kotlin.browser.document
import kotlin.dom.appendText

fun main() {
    document.getElementById("root")?.appendText("Hello, World!")
}
