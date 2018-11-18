package org.kdonev.currencyconverter.model

import org.kdonev.currencyconverter.modelDB.Currency

interface IRatesLoader {
    fun loadRates(onSuccess : (curs : List<Currency>) -> Unit, onError : (error : Exception) -> Unit)
}