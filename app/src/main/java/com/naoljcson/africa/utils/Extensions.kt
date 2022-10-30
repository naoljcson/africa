package com.naoljcson.africa.utils

import android.view.View

fun String.toJPG() = "$this.jpg"
fun String.toMp4() = "$this.mp4"

fun View.show() {
    this.visibility = View.VISIBLE
}
fun View.hide() {
    this.visibility = View.GONE
}