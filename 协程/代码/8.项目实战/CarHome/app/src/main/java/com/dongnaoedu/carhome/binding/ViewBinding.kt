package com.dongnaoedu.carhome.binding

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.dongnaoedu.carhome.R

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
@BindingAdapter("bindingAvator")
fun bindingAvator(imageView: ImageView, url: String) {
    Log.d("ning","loading image:$url")
    imageView.load(url){
        crossfade(true) //淡入淡出
        placeholder(R.mipmap.ic_launcher_round) //占位图
    }
}