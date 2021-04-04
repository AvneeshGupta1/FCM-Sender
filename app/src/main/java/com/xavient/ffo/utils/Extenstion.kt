package com.xavient.ffo.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager


fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}
fun Activity.hideKeyboard() {
    if (currentFocus == null) View(this).hideKeyboard() else currentFocus!!.hideKeyboard()
}

infix fun View.visibleIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

fun View.visibleIf(condition: Boolean, otherwise: Int = View.GONE) {
    visibility = if (condition) View.VISIBLE else otherwise
}

infix fun View.goneIf(condition: Boolean) {
    visibility = if (condition) View.GONE else View.VISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}