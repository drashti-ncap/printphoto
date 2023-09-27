package com.mobile.cover.photo.editor.back.maker.activity.Usefull

import android.os.Bundle
import android.os.Handler

import androidx.appcompat.app.AppCompatActivity

import com.mobile.cover.photo.editor.back.maker.R

class finished_activity : AppCompatActivity() {

    internal lateinit var runnable: Runnable
    private val timeoutHandler = Handler()
    private var is_pause = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished_activity)
        runnable = Runnable { finish() }
        timeoutHandler.postDelayed(runnable, 1500)
    }

    override fun onPause() {
        super.onPause()
        timeoutHandler.removeCallbacks(runnable)
        is_pause = true
    }

    override fun onStop() {
        super.onStop()
        is_pause = true
        timeoutHandler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        is_pause = true
        timeoutHandler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        if (is_pause) {

            is_pause = false
            finish()
        }
    }
}
