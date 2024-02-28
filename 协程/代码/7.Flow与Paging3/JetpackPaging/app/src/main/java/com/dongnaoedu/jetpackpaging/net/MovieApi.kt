package com.dongnaoedu.jetpackpaging.net

import com.dongnaoedu.jetpackpaging.model.Movies
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
interface MovieApi {

    @GET("pkds.do")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("pagesize") pageSize: Int
    ): Movies

}