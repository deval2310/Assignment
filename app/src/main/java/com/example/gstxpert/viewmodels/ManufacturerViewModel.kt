package com.example.gstxpert.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.gstxpert.models.Card

class ManufacturerViewModel(application: Application): AndroidViewModel(application) {

    private val _result = MutableLiveData<List<Card>>()
    val result: LiveData<List<Card>> = _result

    fun calculateGstForManufacturer(amount: Double, profit: Double, gst: Double) {
        viewModelScope.launch {
            val gstAmount = withContext(Dispatchers.Default) {
                val GST = amount * (gst / 100)
                val profitCalculated = GST * (profit / 100)
                val total = amount + amount * (profit / 100)
                val totalGst = GST + profitCalculated
                val CGST = totalGst / 2
                val SGST = totalGst / 2
                listOf(
                    Card("Total Cost of Production", total.toString()),
                    Card("CGST", CGST.toString()),
                    Card("SGST", SGST.toString()),
                    Card("Total Tax", totalGst.toString())
                )
            }
            _result.postValue(gstAmount)
        }
    }
}
