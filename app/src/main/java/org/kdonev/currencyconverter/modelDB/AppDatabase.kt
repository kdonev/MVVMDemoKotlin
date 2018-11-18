package org.kdonev.currencyconverter.modelDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Currency::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currenciesDao(): CurrenciesDao
}