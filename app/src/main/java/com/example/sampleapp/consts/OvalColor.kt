package com.example.sampleapp.consts

import com.example.sampleapp.R
import com.example.sampleapp.binding.Colorable

enum class OvalColor:Colorable {
    BLUE{
        override val color:Int
        get() = R.color.element_blue
    },
    RED{
        override val color: Int
            get() = R.color.element_red

    }
}