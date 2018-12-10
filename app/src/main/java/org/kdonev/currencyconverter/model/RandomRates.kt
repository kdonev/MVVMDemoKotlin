package org.kdonev.currencyconverter.model

import org.kdonev.currencyconverter.modelDB.Currency
import kotlin.random.Random

class RandomRates : IRatesLoader {
    override fun loadRates(onSuccess: (curs: List<Currency>) -> Unit, onError: (error: Exception) -> Unit) {
        Thread(Runnable {
            Thread.sleep(2000)
            onSuccess(
                listOf<Currency>(
                    Currency("EUR", 1.0),
                    Currency("USD", Random.nextDouble(1.1, 1.3)),
                    Currency("BGN", Random.nextDouble(1.5, 2.5))
                )
            )
        }).start()
    }
}