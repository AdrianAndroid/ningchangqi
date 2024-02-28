package com.dongnaoedu.jetpackpaging.model

import com.google.gson.annotations.SerializedName

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
data class Movies(
    @SerializedName("subjects")
    val movieList: List<Movie>,
    @SerializedName("has_more")
    val hasMore: Boolean
)