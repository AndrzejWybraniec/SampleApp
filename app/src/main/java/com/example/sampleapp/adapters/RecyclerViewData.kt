package com.example.sampleapp.adapters

import android.databinding.ObservableList

abstract class RecyclerViewData<T> {
    abstract val itemBindID: Int
    abstract val items: ObservableList<T>
    abstract fun itemLayoutID(viewType: Int): Int
}