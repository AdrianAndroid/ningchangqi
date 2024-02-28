package com.dongnaoedu.carhome.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dongnaoedu.carhome.databinding.CarbrandItemBinding
import com.dongnaoedu.carhome.databinding.NetworkStateItemBinding

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
class FooterAdapter(
    val adapter: CarBrandAdapter,
    val context: Context
) : LoadStateAdapter<NetWorkStateItemViewHolder>() {

    override fun onBindViewHolder(holder: NetWorkStateItemViewHolder, loadState: LoadState) {
        //水平居中
        val params = holder.itemView.layoutParams
        if (params is StaggeredGridLayoutManager.LayoutParams) {
            params.isFullSpan = true
        }
        holder.bindData(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetWorkStateItemViewHolder {
        val binding = NetworkStateItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return NetWorkStateItemViewHolder(binding) { adapter.retry() }
    }
}