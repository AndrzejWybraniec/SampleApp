package com.example.sampleapp.binding

import android.support.annotation.ColorRes

interface Colorable {
    @get:ColorRes
    val color: Int
}