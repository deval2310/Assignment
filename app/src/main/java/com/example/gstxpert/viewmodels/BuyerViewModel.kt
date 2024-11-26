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

class BuyerViewModel(application: Application): AndroidViewModel(application) {

    private val _result = MutableLiveData<List<Card>>()
    val result: LiveData<List<Card>> = _result

    fun calculateGst(amount: Double, gst: Double) {
        viewModelScope.launch {
            val gstAmount = withContext(Dispatchers.Default) {
                val GST = amount * (gst / 100)
                val total = amount + GST
                val CGST = GST / 2
                val SGST = GST / 2
                listOf(
                    Card("Total Cost", total.toString()),
                    Card("CGST", CGST.toString()),
                    Card("SGST", SGST.toString()),
                    Card("Total Tax", GST.toString())
                )
            }
            _result.postValue(gstAmount)
        }
    }
}
