package com.mobile.cover.photo.editor.back.maker.activity.Usefull

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.cover.photo.editor.back.maker.Commen.Share
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.sticker_main_response
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.StickerAdapter
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient
import com.mobile.cover.photo.editor.back.maker.model.StickerModel
import com.mobile.cover.photo.editor.back.maker.model.new_StickerModel
import com.mobile.cover.photo.editor.back.maker.model.new_getdefault_images
import kotlinx.android.synthetic.main.activity_sticker.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class StickerActivity : AppCompatActivity() {
    internal lateinit var stickerAdapter: StickerAdapter
    internal lateinit var pd: ProgressDialog
    private var assetManager: AssetManager? = null
    private val stickerModels = ArrayList<new_StickerModel>()
    private val sqlist = ArrayList<new_getdefault_images>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_sticker)
        activity = this@StickerActivity
        setHeader()
        getDisplaySize()


        if (Share.stickerlist.size == 0) {
            findViews()
            getMainData()
        } else {
            findViews()
        }
        intView()
    }


    private fun findViews() {
        stickerAdapter = StickerAdapter(activity, Share.stickerlist)
        recyclerview.layoutManager = GridLayoutManager(activity, 4)
        val gridSpacingItemDecoration = GridSpacingItemDecoration(4, 16, true)
        recyclerview.addItemDecoration(gridSpacingItemDecoration)
        recyclerview.adapter = stickerAdapter
        stickerAdapter.setOnItemClickListener { v, position ->
            Log.e("POS", "onItemClickLister: $position")
            Share.sqlist_multiple_sticker = Share.stickerlist[position].sqlist
            val intent = Intent(applicationContext, Multiple_StickerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun intView() {
        assetManager = assets
    }

    private fun setHeader() {
        val imageView = findViewById<ImageView>(R.id.id_back)
        imageView.setOnClickListener { onBackPressed() }
    }

    private fun getDisplaySize() {
        val display = window.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        Log.e("STICKER", "" + size.x)
        Share.screenWidth = size.x
        Share.screenHeight = size.y
    }

    private fun getMainData() {


            sqlist.clear()
            Share.stickerlist.clear()
            pd = ProgressDialog.show(activity, "", getString(R.string.loading), true, false)
        val api = MainApiClient(this@StickerActivity).apiInterface


            val call = api.getstickers()


            call!!.enqueue(object : Callback<sticker_main_response?> {
                override fun onResponse(call: Call<sticker_main_response?>, response: Response<sticker_main_response?>) {
                    Log.e("RESPONE", "onResponse: $response")
                    val customimage_response = response.body()
                    Log.e("RESPONE", "onResponse: " + response.body()!!.responseCode)
                    if (customimage_response!!.responseCode.equals("1", ignoreCase = true)) {
                        pd.dismiss()

                        val datumList = customimage_response.data
                        for (datum in datumList) {
                            val getdefault_images = new_getdefault_images("" + datum.id!!, "" + datum.name, "" + datum.image, datum.stickers)
                            Share.stickerlist.add(getdefault_images)
                        }
                        stickerAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<sticker_main_response?>, t: Throwable) {
                    if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                        val alertDialog = AlertDialog.Builder(activity).create()
                        alertDialog.setTitle(getString(R.string.time_out))
                        alertDialog.setCancelable(false)
                        alertDialog.setMessage(getString(R.string.connect_time_out))
                        alertDialog.setButton(getString(R.string.ok)) { dialog, which ->
                            dialog.dismiss()
                            pd.dismiss()
                            getMainData()
                        }
                        alertDialog.show()
                    } else {
                        val alertDialog = AlertDialog.Builder(activity).create()
                        alertDialog.setTitle(getString(R.string.internet_connection))
                        alertDialog.setCancelable(false)
                        alertDialog.setMessage(getString(R.string.slow_connect))
                        alertDialog.setButton(getString(R.string.retry)) { dialog, which ->
                            dialog.dismiss()
                            pd.dismiss()
                            getMainData()
                        }
                        alertDialog.show()
                    }


                }
            })

    }

    inner class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f / spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }


    public override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }

    companion object {
        lateinit var activity: Activity
        var list = ArrayList<StickerModel>()
    }


}
