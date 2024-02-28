package com.dongnaoedu.carhome.remote

import com.dongnaoedu.carhome.model.CarBrandItemModel
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
interface CarBrandService {

    @GET("carBrand.do")
    suspend fun fetchCarBrandList(
        @Query("since") since: Int,
        @Query("pagesize") pagesize: Int
    ): List<CarBrandItemModel>

}