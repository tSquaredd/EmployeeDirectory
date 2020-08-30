package com.tsquaredapps.employeedirectory.ext

import android.content.res.Resources
import android.view.View

fun View.setAsVisible() {
    visibility = View.VISIBLE
}

fun View.setAsGone() {
    visibility = View.GONE
}

fun Int.pxToDp() = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()