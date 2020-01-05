package com.example.demo.ui

import androidx.lifecycle.Observer
import com.example.demo.R
import com.example.demo.base.BaseActivity
import com.example.demo.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    val adapter = MainAdapter()
    val dialog = LocationFindingDialog()


    override fun setUpUi() {
        setToolbarForActivity(binding.include.toolbar)
        binding.include.headerTitle = getString(R.string.app_name)
        binding.viewModel = viewModel
        binding.adapter = adapter
        viewModel.callRestaurantsNearby(19.158567, 72.992374)
    }

    override fun setUpListener() {
        binding.fabClick.setOnClickListener {
            val ft = supportFragmentManager.beginTransaction()
            dialog.show(ft, getString(R.string.search_dialog))
        }
    }

    override fun listenMutableEvent() {
        viewModel.getNearbyRestaurant.observe(this, Observer {
            it?.let {
                adapter.clearList()
                adapter.setList(it.nearbyRestaurants.toMutableList())
            }
            binding.isShowProgress = true
        })


        viewModel.locItemClick.observe(this, Observer {
            it?.let {
                viewModel.callRestaurantsNearby(it.latitude ?: 0.0, it.longitude ?: 0.0)
                dialog.dismiss()
            }
        })
    }

    override fun getViewModel(): Class<MainViewModel> = MainViewModel::class.java
    override fun getLayoutId(): Int = R.layout.activity_main



}
