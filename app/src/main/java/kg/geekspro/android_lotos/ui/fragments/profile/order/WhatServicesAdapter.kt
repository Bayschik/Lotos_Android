package kg.geekspro.android_lotos.ui.fragments.profile.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kg.geekspro.android_lotos.databinding.ItemWhatServicesBinding

class WhatServicesAdapter:Adapter<WhatServicesAdapter.ServicesViewHolder>() {
    private val orderList = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        return ServicesViewHolder(ItemWhatServicesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = orderList.size

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        holder.bind(orderList[position])
    }

    fun order(servicesData: String) {
        orderList.clear()
        orderList.add(servicesData)
        notifyDataSetChanged()
    }

    inner class ServicesViewHolder(private val binding:ItemWhatServicesBinding):ViewHolder(binding.root){
        fun bind(order: String) {
            binding.apply {
                tvService.text = order
            }
        }

    }
}