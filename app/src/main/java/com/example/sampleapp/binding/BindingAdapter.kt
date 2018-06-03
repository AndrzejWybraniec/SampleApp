package com.example.sampleapp.binding

import android.databinding.BindingAdapter
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import org.w3c.dom.Text
import android.graphics.drawable.Drawable



@BindingAdapter("drawableTintColor")
fun bindColor(textView: TextView, colorable: Colorable?) {
    if(colorable!=null) {
        val drawables = textView.compoundDrawables
        drawables[0]?.setColorFilter(ContextCompat.getColor(textView.context, colorable.color), PorterDuff.Mode.MULTIPLY)
    }
}

@BindingAdapter("android:text")
fun bindText(textView: TextView, textable: Textable?) {
    if (textable == null) {
        textView.text = ""
    } else {
        textView.text = textView.context.resources.getString(textable.text)
    }
}
