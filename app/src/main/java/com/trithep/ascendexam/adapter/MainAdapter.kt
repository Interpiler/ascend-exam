package com.trithep.ascendexam.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.trithep.ascendexam.R
import com.trithep.ascendexam.databinding.AdapterProductBinding
import com.trithep.ascendexam.model.Product
import com.trithep.ascendexam.view.ProductDetailActivity


class MainAdapter: RecyclerView.Adapter<MainViewHolder>(){
    var products = mutableListOf<Product>()

    fun setProductList(products: List<Product>){
        this.products = products.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterProductBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val product = products[position]
        var testImage = "https://cdn.shopify.com/s/files/1/0384/4629/products/cookies-largebag-chocolatechip-web_1024x1024.jpg?v=1609889179"

        holder.binding.textProductName.text = product.title
        holder.binding.textProductPrice.text = String.format("%.2f", product.price)
        holder.binding.textNewProduct.visibility = if (product.isNewProduct) View.VISIBLE else View.GONE
        Glide.with(holder.itemView.context)
            .load(product.image)
            .centerCrop()
            .placeholder(getLoadingHolder(holder.itemView.context))
            .error(R.drawable.cloudnt_load_image)
            .into(holder.binding.imageProduct)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProductDetailActivity::class.java)
            intent.putExtra("id",product.id)
            startActivity(holder.itemView.context,intent,null)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    private fun getLoadingHolder(context: Context): CircularProgressDrawable{
        val circularProgress = CircularProgressDrawable(context)
        circularProgress.strokeWidth = 5f
        circularProgress.centerRadius = 30f
        circularProgress.start()
        return circularProgress
    }

}

class MainViewHolder(val binding: AdapterProductBinding) : RecyclerView.ViewHolder(binding.root) {

}