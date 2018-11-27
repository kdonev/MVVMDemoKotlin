package org.kdonev.currencyconverter.model

import androidx.lifecycle.LiveData
import org.kdonev.currencyconverter.modelDB.Currency

interface ICurrencies {
    fun all(): LiveData<List<Currency>>
    fun syncRates(onFinish : () -> Unit)
}