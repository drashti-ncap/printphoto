package com.mobile.cover.photo.editor.back.maker.activity.Usefull

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.analytics.FirebaseAnalytics
import com.mobile.cover.photo.editor.back.maker.Commen.GlobalData
import com.mobile.cover.photo.editor.back.maker.Commen.Share
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.PhoneAlbumImagesAdapter
import kotlinx.android.synthetic.main.activity_albumimages.*

import java.util.ArrayList

class AlbumImagesActivity : AppCompatActivity() {
    private var gridLayoutManager: GridLayoutManager? = null
    private var albumAdapter: PhoneAlbumImagesAdapter? = null
    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onResume() {
        super.onResume()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albumimages)
        activity = this@AlbumImagesActivity
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        setToolbar()
        initView()
    }

    private fun initView() {
        gridLayoutManager = GridLayoutManager(activity, 3)
        rcv_album_images!!.layoutManager = gridLayoutManager
        val al_album_images = Share.lst_album_image
        albumAdapter = PhoneAlbumImagesAdapter(this, al_album_images)
        rcv_album_images!!.adapter = albumAdapter
    }

    private fun setToolbar() {
        try {
            val albumName = intent.extras!!.getString(GlobalData.KEYNAME.ALBUM_NAME)
            val toolbar = findViewById<Toolbar>(R.id.toolbar)
            toolbar.setTitleTextColor(Color.WHITE)

            val tv_title = toolbar.findViewById<TextView>(R.id.tv_title)
            tv_title.text = albumName

            val iv_back = toolbar.findViewById<ImageView>(R.id.iv_back)
            iv_back.setOnClickListener { onBackPressed() }
            setSupportActionBar(toolbar)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    public override fun onPause() {
        super.onPause()
    }

    public override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        GlobalData.isFromHomeAgain = false
        super.onBackPressed()
        finish()
    }

    companion object {

        lateinit var activity: Activity
    }
}
