package com.dongnaoedu.carhome.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.dongnaoedu.carhome.db.AppDatabase
import com.dongnaoedu.carhome.entity.CarBrandEntity
import com.dongnaoedu.carhome.mapper.Mapper
import com.dongnaoedu.carhome.model.CarBrandItemModel
import com.dongnaoedu.carhome.remote.CarBrandRemoteMediator
import com.dongnaoedu.carhome.remote.CarBrandService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
class CarBrandRepositoryImpl(
    private val api: CarBrandService,
    private val database: AppDatabase,
    private val mapper2ItemModel: Mapper<CarBrandEntity, CarBrandItemModel>
) : Repository {

    override fun fetchCarBrandList(): Flow<PagingData<CarBrandItemModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 8,
                prefetchDistance = 1,
                initialLoadSize = 16
            ),
            remoteMediator = CarBrandRemoteMediator(api, database) // 请求网路数据，放入数据库
        ) {
            database.carBrandDao().getCarBrand()  // 从数据库拿到数据
        }.flow
            .flowOn(Dispatchers.IO).map { pagingData ->
                pagingData.map { mapper2ItemModel.map(it) }  // 对数据进行转换，给到UI显示
            }
    }
}