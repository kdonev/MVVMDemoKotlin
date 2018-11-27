package org.kdonev.currencyconverter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.kdonev.currencyconverter.model.ICurrencies
import org.kdonev.currencyconverter.modelDB.Currency
import org.kdonev.currencyconverter.viewModel.CurrenciesVM
import org.mockito.Mockito.mock

class TestModel : ICurrencies
{
    private val _currencies = MutableLiveData<List<Currency>>()

    init {
        _currencies.value = listOf(
            Currency("eur", 1.0),
            Currency("usd", 1.5),
            Currency("bgn", 1.95))
    }

    override fun all(): LiveData<List<Currency>> = _currencies

    override fun syncRates(onFinish: () -> Unit)
    {
        _currencies.value = listOf(
            Currency("eur", 1.0),
            Currency("usd", 1.6),
            Currency("bgn", 1.7))

        onFinish()
    }
}

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Test
    fun syncUpdateAmounts()
    {
        val model = TestModel()
        val vm = CurrenciesVM(model, null)

        vm.eurAmount = 10.0

        assertEquals(vm.usdAmount, 15.0, 0.01)
        assertEquals(vm.bgnAmount, 19.5, 0.01)

        vm.sync()

        assertEquals(vm.eurAmount, 10.0, 0.01)
        assertEquals(vm.usdAmount, 16.0, 0.01)
        assertEquals(vm.bgnAmount, 17.0, 0.01)
    }
}
