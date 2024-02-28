package com.dongnaoedu.carhome.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dongnaoedu.carhome.entity.CarBrandEntity

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
@Dao
interface CarBrandDao {

    @Query("DELETE FROM CarBrandEntity")
    suspend fun clearCarBrand()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarBrand(carBrandList: List<CarBrandEntity>)

    @Query("SELECT * FROM CarBrandEntity")
    fun getCarBrand(): PagingSource<Int, CarBrandEntity>

}