package com.movieapptask.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.movieapptask.R
import com.movieapptask.ui.main.MainActivity
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val delayTime = 3000L // 3 seconds and time animation
        CoroutineScope(Dispatchers.IO).launch {
            delay(delayTime)

            withContext(Dispatchers.Main) {
                openMain()
            }
        }
    }

    private fun openMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}