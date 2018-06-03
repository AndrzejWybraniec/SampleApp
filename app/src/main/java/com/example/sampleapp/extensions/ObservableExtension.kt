package com.example.sampleapp.extensions

import android.databinding.ObservableBoolean
import android.databinding.ObservableField

var <T> ObservableField<T>.value: T?
    get() {
        return get()
    }
    set(value) {
        set(value)
    }


var ObservableBoolean.value: Boolean
    get() {
        return get()
    }
    set(value) {
        set(value)
    }