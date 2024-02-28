package com.dongnaoedu.carhome.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.dongnaoedu.carhome.databinding.CarbrandItemBinding
import com.dongnaoedu.carhome.model.CarBrandItemModel

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
class CarBrandAdapter(private val context: Context) :
    PagingDataAdapter<CarBrandItemModel, BindingViewHolder>(object :
        DiffUtil.ItemCallback<CarBrandItemModel>() {
        override fun areItemsTheSame(
            oldItem: CarBrandItemModel,
            newItem: CarBrandItemModel
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: CarBrandItemModel,
            newItem: CarBrandItemModel
        ): Boolean {
            return oldItem == newItem
        }

    }) {
    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        getItem(position).let { item ->
            val binding = holder.binding as CarbrandItemBinding
            binding.carBrand = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val binding = CarbrandItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return BindingViewHolder(binding)
    }
}