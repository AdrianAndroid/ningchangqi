package com.dongnaoedu.jetpackpaging.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.dongnaoedu.jetpackpaging.databinding.MovieLoadmoreBinding

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
class MovieLoadMoreAdapter(private val context: Context) : LoadStateAdapter<BindingViewHolder>() {

    override fun onBindViewHolder(holder: BindingViewHolder, loadState: LoadState) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BindingViewHolder {
        val binding = MovieLoadmoreBinding.inflate(LayoutInflater.from(context), parent, false)
        return BindingViewHolder(binding)
    }
}