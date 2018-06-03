package com.example.sampleapp.binding

import android.support.annotation.StringRes

interface Textable {
    @get:StringRes
    val text: Int
}
