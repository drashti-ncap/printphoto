package com.mobile.cover.photo.editor.back.maker.activity.Usefull

import android.content.Context
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.view.Surface
import android.view.View
import android.view.WindowManager
import android.widget.MediaController
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.getConfiguration
import com.mobile.cover.photo.editor.back.maker.extra.FullScreenMediaController
import kotlinx.android.synthetic.main.new_video.*


class VideoActivity : AppCompatActivity() {
    private var position: Int = 0
    private var moMediaController: MediaController? = null
    private var msVideoLink: String? = null


    private val isLandScape: Boolean
        get() {
            val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            val rotation = display.rotation

            return rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        AppGlobal.getInstance().trackScreenView(getClass().getName());
        setContentView(R.layout.new_video)
        SharedPrefs.save(this@VideoActivity, SharedPrefs.videoplay, "1")
        init()
    }

    private fun init() {

        val configurationData = getConfiguration()


        if (SharedPrefs.getString(this@VideoActivity, SharedPrefs.country_code).equals("IN", ignoreCase = true)) {
            msVideoLink = configurationData!!.tutorialLink
        } else {
            msVideoLink = configurationData!!.tutorial_link_international
        }
        videoView!!.setOnCompletionListener { finish() }
        ivBack!!.setOnClickListener { finish() }
        val fullScreen = intent.getStringExtra("fullScreenInd")
        if ("y" == fullScreen) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            supportActionBar!!.hide()
        }

        val videoUri = Uri.parse(msVideoLink)

        videoView!!.setVideoURI(videoUri)
        //        enterFullScreen();
        moMediaController = FullScreenMediaController(this)
        moMediaController!!.setAnchorView(videoView)

        videoView!!.setMediaController(moMediaController)
        videoView!!.start()
        if (isLandScape) {
            moMediaController = FullScreenMediaController(this)
        } else {
            moMediaController = MediaController(this)
        }

        videoView!!.setOnPreparedListener {
            llProgress!!.visibility = View.GONE
            videoView!!.start()
        }

    }

    private fun enterFullScreen() {

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
    }


    public override fun onSaveInstanceState(savedInstanceState: Bundle) {

        super.onSaveInstanceState(savedInstanceState)
        //we use onSaveInstanceState in order to store the video playback position for orientation change
        savedInstanceState.putInt("Position", videoView!!.currentPosition)
        videoView!!.pause()
    }

    public override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        super.onRestoreInstanceState(savedInstanceState)
        //we use onRestoreInstanceState in order to play the video playback from the stored position
        position = savedInstanceState.getInt("Position")
        videoView!!.seekTo(position)
    }

    override fun onStop() {
        super.onStop()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        videoView!!.stopPlayback()
    }

    public override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }

}
