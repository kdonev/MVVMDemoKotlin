package org.kdonev.currencyconverter.modelDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrenciesDao {
    @Query("SELECT * FROM Currency")
    fun getAll() : LiveData<List<Currency>>

    @Query("SELECT * FROM Currency WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String) : Currency

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAll(list : List<Currency>)
}