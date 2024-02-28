package com.dongnaoedu.carhome.di

import android.app.Application
import androidx.room.Room
import com.dongnaoedu.carhome.db.AppDatabase
import com.dongnaoedu.carhome.db.CarBrandDao
import com.dongnaoedu.carhome.mapper.Entity2ItemModelMapper
import com.dongnaoedu.carhome.remote.CarBrandService
import com.dongnaoedu.carhome.repository.CarBrandRepositoryImpl
import com.dongnaoedu.carhome.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
@InstallIn(ActivityComponent::class)
@Module
object RepositoryModule {

    @ActivityScoped
    @Provides
    fun provideCarBrandRepository(
        api: CarBrandService,
        database: AppDatabase
    ): Repository {
        return CarBrandRepositoryImpl(api, database, Entity2ItemModelMapper())
    }
}
