package com.sdslabs.phpmastery

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.sdslabs.phpmastery.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animateSplash()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 2800)
    }

    private fun animateSplash() {
        // Logo animate in
        binding.splashLogo.alpha = 0f
        binding.splashLogo.translationY = 60f

        val fadeIn = ObjectAnimator.ofFloat(binding.splashLogo, View.ALPHA, 0f, 1f)
        val slideUp = ObjectAnimator.ofFloat(binding.splashLogo, View.TRANSLATION_Y, 60f, 0f)
        slideUp.interpolator = AccelerateDecelerateInterpolator()

        AnimatorSet().apply {
            playTogether(fadeIn, slideUp)
            duration = 700
            start()
        }

        // Progress bar animation
        Handler(Looper.getMainLooper()).postDelayed({
            ObjectAnimator.ofInt(binding.splashProgress, "progress", 0, 100).apply {
                duration = 2000
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }
        }, 300)

        // Orb floating animation
        ObjectAnimator.ofFloat(binding.splashOrb, View.TRANSLATION_Y, 0f, -30f, 0f).apply {
            duration = 3000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
    }
}
