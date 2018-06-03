package com.example.sampleapp.consts

import com.example.sampleapp.R
import com.example.sampleapp.binding.Textable

enum class UpdateListState :Textable{
    LIST_CREATED {
        override val text: Int
            get() = R.string.list_created
    },
    UPDATING {
        override val text: Int
            get() = R.string.updating
    },
    STOPPED{
        override val text: Int
            get() = R.string.stopped

    }
}