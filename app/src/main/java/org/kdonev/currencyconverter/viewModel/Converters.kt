package org.kdonev.currencyconverter.viewModel

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseMethod

object Converters {

    @InverseMethod("convertStringToDouble")
    @JvmStatic fun convertDoubleToString(d : Double) = String.format("%.2f", d)

    @JvmStatic fun convertStringToDouble(s : String) = s.toDoubleOrNull()?.toDouble() ?: 0.0

    @BindingAdapter("android:text")
    @JvmStatic fun applyDoubleValue(textView : EditText, oldValue : Double, newValue : Double)
    {
        val delta = newValue - oldValue

        if (Math.abs(delta) > 0.01)
            textView.setText(convertDoubleToString(newValue))

        when {
            delta < -0.01 -> animateTextColor(textView, Color.RED)
            delta > 0.01 -> animateTextColor(textView, Color.GREEN)
        }
    }

    @InverseBindingAdapter( attribute="android:text")
    @JvmStatic fun getDoubleValue(textView: EditText) : Double
    {
        return convertStringToDouble(textView.text.toString())
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