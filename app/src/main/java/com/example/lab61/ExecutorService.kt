package com.example.lab61

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

class ExecutorService: AppCompatActivity() {
    private var secondsElapsed = 0
    private lateinit var textSecondsElapsed: TextView
    private lateinit var future: Future<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    companion object {
        var SECS = 0
    }

    override fun onStart() {
        super.onStart()
        future = runExecutor((applicationContext as App).executor)
        Log.d("mainActivity", "OnStart: seconds = $secondsElapsed")
    }

    override fun onStop() {
        Log.d("mainActivity", "OnStop: seconds = $secondsElapsed")
        future.cancel(true)
        super.onStop()
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

    private fun runExecutor(executorService: ExecutorService): Future<*> {
        return executorService.submit {
            while (true) {
                Thread.sleep(1000)
                runOnUiThread {
                    textSecondsElapsed.text = getString(R.string.main_str, secondsElapsed++)
                }
            }
        }
    }
}
