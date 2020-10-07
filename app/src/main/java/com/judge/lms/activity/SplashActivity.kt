package com.judge.lms.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.judge.lms.R

/**
 * Created by Rohit on 18-09-2020.
 */

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val linearLayout = findViewById<LinearLayout>(R.id.logo_text)
        val imageView = findViewById<ImageView>(R.id.logo)
        val animation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.shrink)
        imageView.animation = animation
        Handler(Looper.myLooper()!!).postDelayed({
            val animations = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.fade_in)
            linearLayout.animation = animations
            linearLayout.visibility = View.VISIBLE
        }, 1500)
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }, 3000)
        //Animation animation1 = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.slide_in_left)
    }
}