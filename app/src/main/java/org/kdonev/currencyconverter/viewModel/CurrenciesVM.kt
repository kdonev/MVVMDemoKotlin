package org.kdonev.currencyconverter.viewModel

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.*
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import org.kdonev.currencyconverter.model.Currencies
import org.kdonev.currencyconverter.model.ICurrencies
import org.kdonev.currencyconverter.model.IRatesLoader
import org.kdonev.currencyconverter.modelDB.Currency

class CurrenciesVM(val model: ICurrencies, val lifecycleOwner: LifecycleOwner?) : BaseObservable() {

    private val currencies = model.all()
    private var _eurAmount : Double = 1.0

    private val currenciesObserver = Observer<List<Currency>>{
        amountsChanged(BR._all)
    }

    init{
        if(lifecycleOwner != null)
            currencies.observe(lifecycleOwner, currenciesObserver)
        else
            currencies.observeForever(currenciesObserver)
    }

    private fun amountsChanged(fromFieldId : Int)
    {
        if (fromFieldId != BR.eurAmount)
            notifyPropertyChanged(BR.eurAmount)

        if (fromFieldId != BR.usdAmount)
            notifyPropertyChanged(BR.usdAmount)

        if (fromFieldId != BR.bgnAmount)
            notifyPropertyChanged(BR.bgnAmount)
    }

    private fun rateFor(name : String) =
        currencies.value?.find { c -> c.name.equals(name, true)}?.rate ?: 1.0

    private fun amount(name : String) = eurAmount * rateFor(name)

    private fun updateAmount(amount : Double, name : String, fromFieldId : Int)
    {
        updateEurAmount(amount / rateFor(name), fromFieldId)
    }

    private fun updateEurAmount(amount: Double, fromFieldId: Int)
    {
        if (Math.abs(amount - _eurAmount) > 0.01)
        {
            _eurAmount = amount;
            amountsChanged(fromFieldId);
        }
    }

    @get:Bindable
    var eurAmount : Double
        get() = _eurAmount
        set(value)
        {
            updateEurAmount(value, BR.eurAmount)
        }

    @get:Bindable
    var usdAmount : Double
        get() = amount("usd")
        set(value) = updateAmount(value, "usd", BR.usdAmount)

    @get:Bindable
    var bgnAmount : Double
        get() = amount("bgn")
        set(value) = updateAmount(value, "bgn", BR.bgnAmount)

    @get:Bindable
    var syncInProgress : Boolean = false
        set(value)
        {
            if(value != field)
            {
                field = value
                notifyPropertyChanged(BR.syncInProgress)
            }
        }

    fun sync()
    {
        syncInProgress = true
        model.syncRates(
            {
                syncInProgress = false
            }
        )
    }
}