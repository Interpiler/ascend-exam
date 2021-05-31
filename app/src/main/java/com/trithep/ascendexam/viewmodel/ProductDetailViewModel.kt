package com.trithep.ascendexam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trithep.ascendexam.model.Product
import com.trithep.ascendexam.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailViewModel constructor(private val repository: MainRepository) : ViewModel() {
    val product = MutableLiveData<Product>()
    val errorMessage = MutableLiveData<String>()

    fun getProductById(id: Int){
        val response = repository.getProductById(id)
        response.enqueue(object: Callback<Product>{
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                product.postValue(response.body())
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }
}