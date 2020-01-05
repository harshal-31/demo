package com.example.demo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.*


/**
 * Created by Harshal Chaudhari on 18/12/19.
 */


 class SplashScreenActivity: AppCompatActivity() {

    private val job: Job
        get() = Job()

    private val ioScope = CoroutineScope(Dispatchers.IO + job)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        ioScope.launch {
            delay(3000)
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
 }