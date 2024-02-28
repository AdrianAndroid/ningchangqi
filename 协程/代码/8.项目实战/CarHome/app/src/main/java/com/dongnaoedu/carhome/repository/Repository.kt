package com.dongnaoedu.carhome.repository

import androidx.paging.PagingData
import com.dongnaoedu.carhome.model.CarBrandItemModel
import kotlinx.coroutines.flow.Flow

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
interface Repository {

    fun fetchCarBrandList(): Flow<PagingData<CarBrandItemModel>>

}