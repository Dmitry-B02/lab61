package com.example.lab61

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class Coroutines: AppCompatActivity() {

    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        Log.d("mainActivity", "OnStart: seconds = $secondsElapsed")


        lifecycleScope.launch {
            Log.d("mainActivity", "Coroutine is launched")
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (isActive) {
                    Log.d("mainActivity", "Coroutine works")
                    delay(1000)
                    textSecondsElapsed.text = getString(R.string.main_str, secondsElapsed++)
                }
            }
        }
    }

    companion object {
        var SECS = 0
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            SECS = secondsElapsed
        }
        Log.d("mainActivity", "Saved state: seconds = $secondsElapsed")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d("mainActivity", "OnStart: seconds = $secondsElapsed")
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            secondsElapsed = SECS
        }
    }
}