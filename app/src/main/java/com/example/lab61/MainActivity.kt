package com.example.lab61

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private lateinit var bgThread: Thread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    override fun onStart() {
        super.onStart()
        Log.d("mainActivity", "OnStart: seconds = $secondsElapsed")
        startBgThread()
        bgThread.start()
    }

    override fun onStop() {
        bgThread.interrupt()
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

    private fun startBgThread() {
        bgThread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                Log.d("mainActivity", "${Thread.currentThread()} is iterating")
                try {
                    Thread.sleep(1000)
                    textSecondsElapsed.post {
                        textSecondsElapsed.text = getString(R.string.main_str, secondsElapsed++)
                    }
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            }
        }
    }
}
