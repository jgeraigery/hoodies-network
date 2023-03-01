package com.gap.hoodies_network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gap.hoodies_network.config.*
import com.gap.hoodies_network.core.HoodiesNetworkError
import com.gap.hoodies_network.core.HoodiesNetworkClient
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.MalformedURLException
import java.util.HashMap

class PutRequestViewModel : ViewModel() {

    private var mobileHttpClient: HoodiesNetworkClient = HoodiesNetworkClient.Builder().baseUrl("https://httpbin.org/").build()

    internal var putResponse = MutableLiveData<String>()

    internal fun sendPutRequest() {
        viewModelScope.launch(Dispatchers.Main) {
            val result: Result<String, HoodiesNetworkError> = withContext(Dispatchers.IO) {
                mobileHttpClient.putRaw("put", "")
            }
            when (result) {
                is Success -> {
                    putResponse.postValue(result.value)
                }
                is Failure -> {
                    putResponse.postValue(getError(result.reason))
                }
            }
        }
    }

    fun getError(HoodiesNetworkError: HoodiesNetworkError): String {
        return "Code Error: " + HoodiesNetworkError.code.toString() + " Message: " + HoodiesNetworkError.message
    }

}