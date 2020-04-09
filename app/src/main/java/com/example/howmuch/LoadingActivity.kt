package com.example.howmuch

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        loading()
    }
    private fun loading() {
        val handler = Handler()
        handler.postDelayed({
            finish()
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }, 2000)
    }
}
