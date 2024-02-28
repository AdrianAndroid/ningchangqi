package com.dongnaoedu.jetpackpaging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dongnaoedu.jetpackpaging.model.Movie
import com.dongnaoedu.jetpackpaging.paging.MoviePagingSource
import kotlinx.coroutines.flow.Flow

/**
 *
 * @author ningchuanqi
 * @version V1.0
 */
class MovieViewModel : ViewModel() {

    private val movies by lazy {
        Pager(
            config = PagingConfig(
                pageSize = 8,
                prefetchDistance = 1,
                initialLoadSize = 16
            ),
            pagingSourceFactory = {MoviePagingSource()}
        ).flow.cachedIn(viewModelScope)
    }

    fun loadMovie() : Flow<PagingData<Movie>> = movies

}