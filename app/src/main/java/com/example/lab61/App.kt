package com.example.lab61

import android.app.Application
import java.util.concurrent.Executors
import java.util.concurrent.ExecutorService

class App: Application() {
    val executor: ExecutorService = Executors.newFixedThreadPool(1)
}