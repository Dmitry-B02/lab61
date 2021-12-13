package com.example.lab61

import android.app.Application
import android.util.Log
import java.util.concurrent.Executors
import java.util.concurrent.ExecutorService

class App: Application() {
    val executor: ExecutorService = Executors.newFixedThreadPool(1)

    override fun onCreate() {
        super.onCreate()
        Log.d("executor", "POOL WAS CREATED")
    }
}