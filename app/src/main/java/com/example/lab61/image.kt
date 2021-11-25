package com.example.lab61

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import com.example.lab61.databinding.ImgBinding
import java.io.BufferedInputStream
import java.net.URL


class image: AppCompatActivity() {
    val binding = ImgBinding.inflate(layoutInflater)
    private lateinit var executor: ExecutorService
    private lateinit var mIcon_val: Bitmap
    private val bitmapData = MutableLiveData<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (binding.image.drawable == null) {
            loadImage()
        }

        bitmapData.observe(this) { value ->
            if (value != null) {
                binding.image.setImageBitmap(value)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        executor.shutdown()
    }

    private fun loadImage() {
        executor = Executors.newFixedThreadPool(1)
        executor.execute {
            val newurl = URL("https://ibb.co/brfwFVP")
            val stream = newurl.openConnection().getInputStream()
            stream.use {
                mIcon_val = BitmapFactory.decodeStream(stream)
                bitmapData.postValue(mIcon_val)
            }
        }
    }
}