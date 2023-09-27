package com.mobile.cover.photo.editor.back.maker.activity.Usefull

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.cover.photo.editor.back.maker.Commen.Share
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.background_response
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.background_image_adapter
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient
import com.mobile.cover.photo.editor.back.maker.model.background_rv_images
import kotlinx.android.synthetic.main.activity_default_testing_image_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Background_image_activity : AppCompatActivity() {
    internal var progressDialog: ProgressDialog? = null
    internal lateinit var activity: Activity
    internal var sqlist: MutableList<background_rv_images> = ArrayList()
    internal lateinit var default_image_adapter: background_image_adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default_testing_image_activity)
        activity = this@Background_image_activity
        id_back.setOnClickListener { finish() }
        rv_list.setHasFixedSize(true)
        val mLayoutManager = GridLayoutManager(applicationContext, 3)

        val gridSpacingItemDecoration = GridSpacingItemDecoration(3, 27, true)
        rv_list.addItemDecoration(gridSpacingItemDecoration)

        rv_list.layoutManager = mLayoutManager
        rv_list.itemAnimator = DefaultItemAnimator()
        getDisplaySize()

        getbackgroundimagesData()

    }

    private fun getDisplaySize() {
        val display = window.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        Share.screenWidth = size.x
        Share.screenHeight = size.y
    }


    // TODO: 26/01/19 Method for getting backgroung images
    private fun getbackgroundimagesData() {

        progressDialog = ProgressDialog.show(this@Background_image_activity, "", activity.resources.getString(R.string.loading), true, false)
        progressDialog!!.show()
        sqlist.clear()
        Share.backgroundimage_list.clear()

        val apiService = MainApiClient(this@Background_image_activity).apiInterface

        Log.e("CATGEORYID", "getbackgroundimagesData: " + Share.main_category_id)
        val subMainModelCall = apiService.getbackground_images("" + Share.category_id, Locale.getDefault().language)
        subMainModelCall!!.enqueue(object : Callback<background_response?> {
            override fun onResponse(call: Call<background_response?>, response: Response<background_response?>) {
                if (response.body()!!.success!!) {
                    if (response.body()!!.data.size >= 0) {

                        //                        for (int i = 0; i < response.body().getData().size(); i++) {
                        //                            background_rv_images background_rv_images = new background_rv_images("" + response.body().getData().get(i).getId()
                        //                                    , "" + response.body().getData().get(i).getImage());
                        //                        }
                        Share.backgroundimage_list = response.body()!!.data
                        default_image_adapter = background_image_adapter(activity, response.body()!!.data)
                        rv_list.adapter = default_image_adapter
                        default_image_adapter.notifyDataSetChanged()
                        Log.e("SIZE", "onResponse: " + Share.backgroundimage_list.size)
                        Log.e("SIZE", "onResponse: " + response.body()!!.data.size)
                        progressDialog!!.dismiss()

                        if (response.body()!!.data.size == 0) {
                            tv_not_available.visibility = View.VISIBLE
                        } else {
                            tv_not_available.visibility = View.GONE
                        }
                    }

                } else {
                    Toast.makeText(activity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<background_response?>, t: Throwable) {
                Log.d("succus", "Fail==>$t")
                if (progressDialog != null && progressDialog!!.isShowing) {
                    progressDialog!!.dismiss()
                }

                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    val alertDialog = AlertDialog.Builder(activity).create()
                    alertDialog.setTitle(activity.resources.getString(R.string.time_out))
                    alertDialog.setMessage(activity.resources.getString(R.string.connect_time_out))
                    alertDialog.setCancelable(false)
                    alertDialog.setButton(activity.resources.getString(R.string.retry)) { dialog, which ->
                        dialog.dismiss()
                        getbackgroundimagesData()
                    }
                    alertDialog.show()
                } else {
                    val alertDialog = AlertDialog.Builder(activity).create()
                    alertDialog.setTitle(activity.resources.getString(R.string.internet_connection))
                    alertDialog.setMessage(activity.resources.getString(R.string.slow_connect))
                    alertDialog.setCancelable(false)
                    alertDialog.setButton(activity.resources.getString(R.string.retry)) { dialog, which ->
                        dialog.dismiss()
                        getbackgroundimagesData()
                    }
                    alertDialog.show()
                }
            }
        })

    }

    public override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }

    // TODO: 26/01/19 Recycler space management method
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


}
