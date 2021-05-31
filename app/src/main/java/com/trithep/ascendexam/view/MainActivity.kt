package com.trithep.ascendexam.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.trithep.ascendexam.R
import com.trithep.ascendexam.adapter.MainAdapter
import com.trithep.ascendexam.databinding.ActivityMainBinding
import com.trithep.ascendexam.repository.MainRepository
import com.trithep.ascendexam.retrofit.ApiService
import com.trithep.ascendexam.viewmodel.MainViewModel
import com.trithep.ascendexam.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private val apiService = ApiService.getInstance()
    private val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,MainViewModelFactory(MainRepository(apiService)))
            .get(MainViewModel::class.java)

        binding.ryMain.adapter = adapter

        viewModel.productList.observe(this, {
            Log.d("MainActivity", "onCreate: $it")
            if (it != null) {
                adapter.setProductList(it)
                binding.imageNoProductFound.visibility = View.INVISIBLE
            }else{
                binding.imageNoProductFound.visibility = View.VISIBLE
            }
            binding.swipeContainer.isRefreshing = false
        })

        viewModel.errorMessage.observe(this,{
            if (!viewModel.isNetworkConnected(this)){
                showNoInternetConnection()
            }
            binding.swipeContainer.isRefreshing = false
        })

        binding.swipeContainer.setOnRefreshListener {
            viewModel.getAllProduct()
        }

        supportActionBar?.apply {
            supportActionBar?.title = "Products"
        }

        binding.swipeContainer.isRefreshing = true
        viewModel.getAllProduct()
    }

    private fun showNoInternetConnection(){
        Snackbar.make(binding.root, "No Internet connection", Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry"){
                viewModel.getAllProduct()
                binding.swipeContainer.isRefreshing = true
            }
            .show()
    }
}