package com.trithep.ascendexam.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.trithep.ascendexam.model.Product
import com.trithep.ascendexam.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {
    val productList = MutableLiveData<List<Product>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllProduct() {
        val response = repository.getAllProduct()
        response.enqueue(object : Callback<List<Product>>{
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                Log.d("MainViewModel", "response: ${response.body()}")
                productList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e("MainViewModel", t.message?:"")
                errorMessage.postValue(t.message)
            }
        })
    }


    fun isNetworkConnected(context: Context): Boolean {
        Log.d("MainViewModel", "Checking connection")
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }


}