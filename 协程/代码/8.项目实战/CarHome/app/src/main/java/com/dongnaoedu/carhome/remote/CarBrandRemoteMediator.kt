package com.dongnaoedu.carhome.remote

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dongnaoedu.carhome.db.AppDatabase
import com.dongnaoedu.carhome.entity.CarBrandEntity
import com.dongnaoedu.carhome.ext.isConnectedNetwork
import com.dongnaoedu.carhome.init.AppHelper
import com.dongnaoedu.carhome.init.SERVER_URL

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
@OptIn(ExperimentalPagingApi::class)
class CarBrandRemoteMediator(
    private val api: CarBrandService,
    private val database: AppDatabase
) : RemoteMediator<Int, CarBrandEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CarBrandEntity>
    ): MediatorResult {
        try {
            //第一步，判断 LoadType，根据LoadType计算page（当前页）
            Log.d("ning", "loadType = ${loadType}")
            val pageKey = when(loadType){
                // 首次访问 或者调用 PagingDataAdapter.refresh()
                LoadType.REFRESH -> null
                // 在当前加载的数据集的开头加载数据时
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                // 加载更多时触发
                LoadType.APPEND -> {
                    val lastItem: CarBrandEntity = state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem.page
                }
            }

            // 无网络，加载本地数据
            if (!AppHelper.mContext.isConnectedNetwork()){
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            //第二步，请求网络分页数据
            val page = pageKey ?: 0
            val result = api.fetchCarBrandList(
                page * state.config.pageSize,
                state.config.pageSize
            )

            val item = result.map {
                CarBrandEntity(
                    id = it.id,
                    name = it.name,
                    icon = "${SERVER_URL + "images/" + it.icon}",
                    page = page + 1
                )
            }

            val endOfPaginationReached = result.isEmpty()
            //第三步，插入数据库。

            val carBrandDao = database.carBrandDao()
            database.withTransaction {
                if (loadType == LoadType.REFRESH){
                    carBrandDao.clearCarBrand()
                }
                carBrandDao.insertCarBrand(item)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }
}