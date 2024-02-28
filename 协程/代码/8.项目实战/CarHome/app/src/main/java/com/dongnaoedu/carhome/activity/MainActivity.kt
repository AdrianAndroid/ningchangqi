package com.dongnaoedu.carhome.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.dongnaoedu.carhome.adapter.CarBrandAdapter
import com.dongnaoedu.carhome.adapter.FooterAdapter
import com.dongnaoedu.carhome.databinding.ActivityMainBinding
import com.dongnaoedu.carhome.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mViewModel: MainViewModel by viewModels()
    private val mCarBrandAdapter by lazy { CarBrandAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        mBinding.recyleView.adapter =
            mCarBrandAdapter.withLoadStateFooter(FooterAdapter(mCarBrandAdapter, this))

        mViewModel.data.observe(this, {
            mCarBrandAdapter.submitData(lifecycle, it)
            mBinding.swiperRefresh.isEnabled = false
        })

        lifecycleScope.launchWhenCreated {
            mCarBrandAdapter.loadStateFlow.collectLatest { state ->
                mBinding.swiperRefresh.isRefreshing = state.refresh is LoadState.Loading
            }
        }

    }
}