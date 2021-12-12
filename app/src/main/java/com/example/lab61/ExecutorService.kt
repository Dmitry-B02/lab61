package com.example.lab61

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ExecutorService

class ExecutorService : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private lateinit var executor: ExecutorService
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler = Handler(Looper.getMainLooper())
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    override fun onStart() {
        super.onStart()
        val app = App()
        executor = app.executor
        executor.execute(object: Runnable {
            override fun run() {
                if (!executor.isShutdown) {
                    textSecondsElapsed.post {
                        textSecondsElapsed.text = getString(R.string.main_str, secondsElapsed++)
                    }
                    handler.postDelayed(this, 1000)
                }
            }
        })
        Log.d("mainActivity", "OnStart: seconds = $secondsElapsed")
    }

    override fun onStop() {
        executor.shutdown()
        Log.d("mainActivity", "OnStop: seconds = $secondsElapsed")
        super.onStop()
    }

    companion object {
        var SECS = 0
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            SECS = secondsElapsed
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            secondsElapsed = SECS
        }
    }
}
