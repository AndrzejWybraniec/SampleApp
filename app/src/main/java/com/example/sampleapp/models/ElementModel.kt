package com.example.sampleapp.models

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.sampleapp.BR
import com.example.sampleapp.binding.Colorable
import com.example.sampleapp.consts.OvalColor


data class ElementModel(var color:OvalColor, private var _counter:Int) : BaseObservable(){
    var counter: Int
        @Bindable get() = _counter
        set(value) {
            _counter = value
            notifyPropertyChanged(BR.counter)
        }
}