package com.dongnaoedu.carhome.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dongnaoedu.carhome.model.CarBrandItemModel
import com.dongnaoedu.carhome.repository.CarBrandRepositoryImpl
import com.dongnaoedu.carhome.repository.Repository

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
class MainViewModel @ViewModelInject constructor(
    private val carBrandRepository: Repository
) : ViewModel() {

    val data: LiveData<PagingData<CarBrandItemModel>> =
        carBrandRepository
            .fetchCarBrandList()
            .cachedIn(viewModelScope)
            .asLiveData()

}