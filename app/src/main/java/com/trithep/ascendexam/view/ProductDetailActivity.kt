package com.trithep.ascendexam.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.trithep.ascendexam.R
import com.trithep.ascendexam.databinding.ActivityProductDetailBinding
import com.trithep.ascendexam.repository.MainRepository
import com.trithep.ascendexam.retrofit.ApiService
import com.trithep.ascendexam.viewmodel.MainViewModelFactory
import com.trithep.ascendexam.viewmodel.ProductDetailViewModel

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    lateinit var viewModel: ProductDetailViewModel
    private val apiService = ApiService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        val productId: Int = (intent.extras?.get("id") ?: 1) as Int

        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,MainViewModelFactory(MainRepository(apiService)))
            .get(ProductDetailViewModel::class.java)

        viewModel.product.observe(this, {
            binding.imageProductShadow.visibility = View.VISIBLE
            binding.textProductName.text = it.title + it.id.toString()
            binding.textProductPrice.text = String.format("%.2f", it.price)
            binding.textProductDetail.text = it.content
            binding.textNewProduct.visibility = if (it.isNewProduct) View.VISIBLE else View.GONE
            Glide.with(this).load(it.image).placeholder(getLoadingHolder(this))
                .error(R.drawable.cloudnt_load_image).centerCrop().into(binding.imageProduct)
        })

        viewModel.getProductById(productId)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }
    private fun getLoadingHolder(context: Context): CircularProgressDrawable {
        val circularProgress = CircularProgressDrawable(context)
        circularProgress.strokeWidth = 5f
        circularProgress.centerRadius = 30f
        circularProgress.start()
        return circularProgress
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}