package com.dongnaoedu.carhome.di

import android.app.Application
import androidx.room.Room
import com.dongnaoedu.carhome.db.AppDatabase
import com.dongnaoedu.carhome.db.CarBrandDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDataBase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "car_home.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideCarBrandDao(database: AppDatabase): CarBrandDao {
        return database.carBrandDao()
    }
}
