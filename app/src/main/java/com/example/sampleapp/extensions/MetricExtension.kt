package com.example.sampleapp.extensions

import android.content.res.Resources

val Int.dp: Int //px to dp
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int //dp to px
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
