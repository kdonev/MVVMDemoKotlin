package org.kdonev.currencyconverter.model

import android.content.Context
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import org.kdonev.currencyconverter.modelDB.AppDatabase
import org.kdonev.currencyconverter.modelDB.Currency

class FixerRates(context : Context) : IRatesLoader{
    private val CurrencyApiUrl = "http://data.fixer.io/api/latest?access_key=5f21f2f65f0f9dd11d6c0b3ca9cb33e0"

    private val jsonRequests = Volley.newRequestQueue(context)

    override fun loadRates(onSuccess : (curs : List<Currency>) -> Unit, onError : (error : Exception) -> Unit)
    {
        val req = JsonObjectRequest(
            Request.Method.GET, CurrencyApiUrl, null,
            Response.Listener<JSONObject> { response ->
                if (response.getBoolean("success")) {
                    val rates = response.getJSONObject("rates")
                    val currencies = arrayListOf<Currency>()

                    for(k in rates.keys())
                    {
                        currencies.add(Currency(k, rates.getDouble(k)))
                    }

                    onSuccess(currencies)
                }
            },
            Response.ErrorListener { onError(it) })

        jsonRequests.add(req)
    }
}