package com.devkurt.markets.navigation.api

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

// nav3'ün onBack'i korumasız: stale bir dismiss size 1 iken gelirse stack boşalır
// ve NavDisplay crash eder; guard root'u korur, sistem geri tuşu OS'a düşer.
fun <T : NavKey> NavBackStack<T>.safePop() {
    if (size > 1) {
        removeAt(lastIndex)
    }
}

fun <T : NavKey> NavBackStack<T>.setAsRoot(vararg elements: T) {
    clear()
    elements.forEach { add(it) }
}

fun <T : NavKey> NavBackStack<T>.switchTo(element: T) {
    if (lastOrNull() == element) return
    remove(element)
    add(element)
}

fun <T : NavKey> NavBackStack<T>.clearExceptRoot() {
    if (size <= 1) return
    val root = first()
    clear()
    add(root)
}
