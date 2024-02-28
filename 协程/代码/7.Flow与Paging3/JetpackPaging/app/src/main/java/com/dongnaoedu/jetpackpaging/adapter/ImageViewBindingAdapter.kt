package com.dongnaoedu.jetpackpaging.adapter

import android.graphics.Color
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.dongnaoedu.jetpackpaging.R
import com.squareup.picasso.Picasso

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
class ImageViewBindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("image")
        fun setImage(imageView: ImageView, url: String) {
            if (!TextUtils.isEmpty(url)) {
                Picasso.get().load(url).placeholder(R.drawable.ic_launcher_background)
                    .into(imageView)
            } else {
                imageView.setBackgroundColor(Color.GRAY)
            }
        }
    }

}