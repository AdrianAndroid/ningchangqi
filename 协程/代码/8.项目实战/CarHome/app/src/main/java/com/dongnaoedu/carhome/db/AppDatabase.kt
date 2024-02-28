package com.dongnaoedu.carhome.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dongnaoedu.carhome.entity.CarBrandEntity

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
@Database(
    entities = [CarBrandEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carBrandDao(): CarBrandDao
}