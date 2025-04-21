package com.fastrise.app.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.fastrise.app.R

class SplashActivity : AppCompatActivity() {
    private var imageSplash: ImageView? = null
    private var timerStarted = false
    private var animator: Animation? = null
    var value: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen)

        setUpXMLVariables()
    }

    fun setUpXMLVariables() {
        imageSplash = findViewById(R.id.imageSplash)
        animator = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        animator?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                timerStarted = true
            }

            override fun onAnimationEnd(animation: Animation) {
                timerStarted = false
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        }

        )
        imageSplash?.startAnimation(animator)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (timerStarted) {
            animator!!.cancel()
        }
    }
}
