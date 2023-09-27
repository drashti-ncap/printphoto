package com.mobile.cover.photo.editor.back.maker.activity.Usefull

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.mobile.cover.photo.editor.back.maker.Commen.Share
import com.mobile.cover.photo.editor.back.maker.R
import kotlinx.android.synthetic.main.activity_zoom_view_activity.*

class Zoom_view_activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom_view_activity)
        Share.view_x = iv_touch_imageview.x
        Share.view_y = iv_touch_imageview.y
        iv_back.setOnClickListener { finish() }
        zoom_progress.visibility = View.VISIBLE
        Glide.with(this@Zoom_view_activity).asBitmap().load(intent.getStringExtra("url")).into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                iv_touch_imageview.setImageBitmap(resource)
                zoom_progress.visibility = View.GONE
            }
        })
    }
}





