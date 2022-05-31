package com.mycounter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycounter.databinding.ItemPriceBinding
import com.mycounter.model.Price

class PriceAdapter(private val listPrice: ArrayList<Price>) :
    RecyclerView.Adapter<PriceAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPrice[position])
    }

    override fun getItemCount() = listPrice.size

    inner class ViewHolder(private val binding: ItemPriceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(price: Price) {
            with(binding) {
                tvPulsePrice.text = price.pulsePrice.toString()
                tvPaidPrice.text = StringBuilder("Rp${price.paidPrice}")
                root.setOnClickListener { onItemClickCallback.onItemClicked(price) }
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Price)
    }
}