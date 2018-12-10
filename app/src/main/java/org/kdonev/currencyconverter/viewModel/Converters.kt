package org.kdonev.currencyconverter.viewModel

import androidx.databinding.InverseMethod

object Converters {

    @InverseMethod("convertStringToDouble")
    @JvmStatic fun convertDoubleToString(d : Double) = String.format("%.2f", d)

    @JvmStatic fun convertStringToDouble(s : String) = s.toDoubleOrNull()?.toDouble() ?: 0.0


}