package com.example.sampleapp.adapters

import android.databinding.ObservableList
import java.lang.ref.WeakReference


class WeakReferenceOnListChangedCallback<T>(
        bindingRecyclerViewAdapter: DataBoundRecyclerViewAdapter<T>
) : ObservableList.OnListChangedCallback<ObservableList<T>>() {
    private val adapterReference = WeakReference(bindingRecyclerViewAdapter)

    override fun onChanged(sender: ObservableList<T>) {
        adapterReference.get()?.notifyDataSetChanged()
    }

    override fun onItemRangeChanged(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
        adapterReference.get()?.notifyItemRangeChanged(positionStart, itemCount)
    }

    override fun onItemRangeInserted(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
        adapterReference.get()?.notifyItemRangeInserted(positionStart, itemCount)
    }

    override fun onItemRangeMoved(sender: ObservableList<T>, fromPosition: Int, toPosition: Int, itemCount: Int) {
        adapterReference.get()?.notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemRangeRemoved(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
        adapterReference.get()?.notifyItemRangeRemoved(positionStart, itemCount)
    }
}