package org.kdonev.currencyconverter.model

import android.content.Context
import android.util.Log
import androidx.room.Room
import org.kdonev.currencyconverter.modelDB.AppDatabase
import org.kdonev.currencyconverter.modelDB.Currency

class Currencies(val context: Context, val ratesLoader: IRatesLoader)
{
    private val appDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "currenices_db"
    ).allowMainThreadQueries().build()

    fun all() = appDatabase.currenciesDao().getAll()

    fun syncRates(onFinish : () -> Unit)
    {
        ratesLoader.loadRates(
            {
                appDatabase.currenciesDao().updateAll(it)
                onFinish()
            },
            {
                Log.e("Currencies", it.toString())
                onFinish()
            })
    }
}