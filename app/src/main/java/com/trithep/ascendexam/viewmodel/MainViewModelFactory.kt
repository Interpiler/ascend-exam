package com.trithep.ascendexam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trithep.ascendexam.repository.MainRepository

class MainViewModelFactory constructor(private val repository: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            MainViewModel(this.repository) as T
        } else if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)){
            ProductDetailViewModel(this.repository) as T
        } else{
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}