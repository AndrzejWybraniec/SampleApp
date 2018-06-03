package com.example.sampleapp.adapters

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class DataBoundRecyclerViewAdapter<T>(
        private val dataProvider: RecyclerViewData<T>
) : RecyclerView.Adapter<DataBoundRecyclerViewAdapter<T>.DataBoundViewHolder>() {

    init {
        dataProvider.items.addOnListChangedCallback(
                WeakReferenceOnListChangedCallback(this)
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding =
                DataBindingUtil.inflate(inflater, dataProvider.itemLayoutID(viewType), parent, false)
        return DataBoundViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder, position: Int) {
        val item = dataProvider.items[position]
        holder.binding.setVariable(dataProvider.itemBindID, item)
    }

    override fun getItemCount(): Int = dataProvider.items.size

    inner class DataBoundViewHolder(val binding: ViewDataBinding) :
            RecyclerView.ViewHolder(binding.root)

}