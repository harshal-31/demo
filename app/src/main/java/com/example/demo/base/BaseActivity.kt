package com.example.demo.base

import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar


/**
 * Created by Harshal Chaudhari on 18/12/19.
 */

 abstract class BaseActivity<T: ViewDataBinding, V: BaseViewModel>: AppCompatActivity(), BaseErrorCallBack {

    lateinit var binding: T
    lateinit var viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = ViewModelProvider(this).get(getViewModel())
        viewModel.setErrorCallback(this)
        binding.lifecycleOwner = this
        setUpUi()
        setUpListener()
        listenMutableEvent()
    }


    fun setToolbarForActivity(toolbar: Toolbar) {
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    fun setToolbarTitle(title: String) {
        supportActionBar?.setTitle(title)
    }

    val homeAsUpEnabled = {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onNetworkChanged(message: String) {
        if (::binding.isInitialized)
            Snackbar.make(binding.root, message,Snackbar.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.home -> { supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    abstract fun setUpUi()
    abstract fun setUpListener()
    abstract fun listenMutableEvent()
    abstract fun getViewModel(): Class<V>
    abstract fun getLayoutId(): Int
}