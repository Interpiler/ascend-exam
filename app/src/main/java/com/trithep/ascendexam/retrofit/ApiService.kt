package com.trithep.ascendexam.retrofit

import com.trithep.ascendexam.model.Product
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("products")
    fun getAllProduct() : Call<List<Product>>

    @GET("products/{id}")
    fun getProductById(@Path("id") id: Int?) : Call<Product>

    companion object{
        var apiService: ApiService? = null
        private const val BASE_URL = "https://ecommerce-product-app.herokuapp.com/"

        fun getInstance() : ApiService{

            if (apiService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                apiService = retrofit.create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}