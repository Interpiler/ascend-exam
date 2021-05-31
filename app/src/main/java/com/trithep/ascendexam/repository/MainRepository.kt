package com.trithep.ascendexam.repository

import com.trithep.ascendexam.retrofit.ApiService

class MainRepository constructor(private val apiService: ApiService){
    fun getAllProduct() = apiService.getAllProduct()
    fun getProductById(id:Int) = apiService.getProductById(id)
}