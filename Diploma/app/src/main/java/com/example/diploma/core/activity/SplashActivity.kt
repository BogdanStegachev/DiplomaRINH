package com.example.diploma.core.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.diploma.core.classes.AccountManager
import java.io.File


class SplashActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var animation: Animation
    private lateinit var progressBar: ProgressBar
    private lateinit var layout: ConstraintLayout
    private val SPLASH_DURATION: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.diploma.R.layout.activity_splash)
        progressBar = findViewById(com.example.diploma.R.id.progressBar)
        layout = findViewById(com.example.diploma.R.id.splashLayout)
        imageView = findViewById(com.example.diploma.R.id.imageView)
        AccountManager.context = applicationContext
        animation = AnimationUtils.loadAnimation(baseContext, com.example.diploma.R.anim.rotate)

    }

    private fun initFunctionality() {
        layout.postDelayed(Runnable {
            progressBar.visibility = View.GONE
            imageView.startAnimation(animation)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                override fun onAnimationEnd(animation: Animation) {
                    nextActivity(this@SplashActivity)
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
        }, SPLASH_DURATION)
    }

    override fun onResume() {
        super.onResume()
        initFunctionality()
    }

    fun nextActivity(activity: Activity) {
        val mainActivity = Intent(activity, MainActivity::class.java)
        val loginActivity = Intent(activity, LoginActivity::class.java)
        val User = File(applicationContext.filesDir, "User.dat")
        if(User.exists())
            activity.startActivity(mainActivity)
        else
            activity.startActivity(loginActivity)
    }
}
