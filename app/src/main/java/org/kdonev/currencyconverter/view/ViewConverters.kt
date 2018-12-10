package org.kdonev.currencyconverter.view

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import org.kdonev.currencyconverter.viewModel.Converters

object ViewConverters {
    @BindingAdapter("android:text")
    @JvmStatic fun applyDoubleValue(textView : EditText, oldValue : Double, newValue : Double)
    {
        val delta = newValue - oldValue

        if (Math.abs(delta) > 0.01)
            textView.setText(Converters.convertDoubleToString(newValue))

        when {
            delta < -0.01 -> animateTextColor(textView, Color.RED)
            delta > 0.01 -> animateTextColor(textView, Color.GREEN)
        }
    }

    @InverseBindingAdapter( attribute="android:text")
    @JvmStatic fun getDoubleValue(textView: EditText) : Double
    {
        return Converters.convertStringToDouble(textView.text.toString())
    }

    fun animateTextColor(textView: EditText, fromColor : Int)
    {
        var defaultTextColor = Color.BLACK
        var animator = ObjectAnimator.ofObject(textView,
            "textColor",
            ArgbEvaluator(),
            fromColor,
            defaultTextColor)
        animator.duration = 1000
        animator.start()
    }
}
