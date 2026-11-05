package com.example.shutdownprank

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView

class MainActivity : Activity() {

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var root: FrameLayout
    private lateinit var label: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Tam ekran + ekranı açık tut
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        hideSystemBars()

        root = FrameLayout(this).apply { setBackgroundColor(Color.parseColor("#202124")) }
        label = TextView(this).apply {
            setTextColor(Color.WHITE)
            textSize = 22f
            gravity = Gravity.CENTER
            typeface = Typeface.DEFAULT_BOLD
        }
        root.addView(
            label,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )
        setContentView(root)

        startPrank()
    }

    private fun startPrank() {
        // 1) Sahte "kapatılıyor" ekranı
        label.text = "Kapatılıyor…"
        handler.postDelayed({
            // 2) Ekran tamamen kararır (telefon kapanmış gibi)
            label.text = ""
            root.setBackgroundColor(Color.BLACK)
        }, 2000)

        // 3) Birkaç saniye bekle, sahte açılış logosu
        handler.postDelayed({
            label.text = "android"
            label.textSize = 40f
            label.setTextColor(Color.parseColor("#3DDC84"))
        }, 6500)

        // 4) Açılış animasyonu bitti, normale dön
        handler.postDelayed({
            finish() // Uygulamayı kapat, gerçek ana ekran görünür
        }, 10000)
    }

    private fun hideSystemBars() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            )
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
