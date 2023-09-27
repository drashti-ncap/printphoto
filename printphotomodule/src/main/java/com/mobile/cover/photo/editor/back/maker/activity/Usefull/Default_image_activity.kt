package com.mobile.cover.photo.editor.back.maker.activity.Usefull

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.jdrodi.utilities.Log
import com.example.jdrodi.utilities.RvGridSpacingItemDecoration
import com.mobile.cover.photo.editor.back.maker.Commen.Share
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.customimage_response
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.default_image_adapter
import com.mobile.cover.photo.editor.back.maker.model.case_images_data
import com.mobile.cover.photo.editor.back.maker.model.get_images
import com.mobile.cover.photo.editor.back.maker.model.getdefault_images
import kotlinx.android.synthetic.main.activity_default_image_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Default_image_activity : AppCompatActivity(), View.OnClickListener {
    internal lateinit var pd: ProgressDialog
    internal lateinit var default_image_adapter: default_image_adapter
    private var sqlist: MutableList<getdefault_images> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default_image_activity)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        activity = this@Default_image_activity
        Initviews()
        Initlistener()
    }

    private fun Initviews() {
        iv_help.setOnClickListener {
            val intent = Intent(this@Default_image_activity, VideoActivity::class.java)
            startActivity(intent)
        }
        rv_default_images.setOnTouchListener { v, event ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            false
        }


        ed_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                sqlist = default_image_adapter.filter(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })

        default_image_adapter = default_image_adapter(this@Default_image_activity, Share.default_image_sqlist, sqlist_images, tv_no_fnd)
        rv_default_images.itemAnimator = DefaultItemAnimator()
        rv_default_images.addItemDecoration(RvGridSpacingItemDecoration(3, 20, true))
        rv_default_images.adapter = default_image_adapter
    }

    private fun Initlistener() {
        id_back.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v === id_back) {
            finish()
            Share.login_back = !SharedPrefs.getString(this@Default_image_activity, SharedPrefs.last_country_code).equals(SharedPrefs.getString(this@Default_image_activity, SharedPrefs.country_code), ignoreCase = true)
        }
    }

    override fun onBackPressed() {
        finish()
        Share.login_back = !SharedPrefs.getString(this@Default_image_activity, SharedPrefs.last_country_code).equals(SharedPrefs.getString(this@Default_image_activity, SharedPrefs.country_code), ignoreCase = true)
    }

    override fun onResume() {
        super.onResume()
        getDisplaySize()
        ed_search.setText("")
        if (Share.default_image_sqlist.size == 0) {
            getMainData()
        } else {
            default_image_adapter.notifyDataSetChanged()
        }
        System.gc()
    }

    private fun getDisplaySize() {
        val display = window.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        Share.screenWidth = size.x
        Share.screenHeight = size.y
    }


    private fun getMainData() {


        sqlist.clear()
        Share.default_image_sqlist.clear()
        sqlist_images.clear()
        Share.subDataArrayList_category.clear()
        pd = ProgressDialog.show(this@Default_image_activity, "", getString(R.string.loading), true, false)
        val api = MainApiClient(this@Default_image_activity).apiInterface
        var user_id: String
        if (SharedPrefs.getString(this@Default_image_activity, SharedPrefs.uid).equals("uid", ignoreCase = true) || SharedPrefs.getString(this@Default_image_activity, SharedPrefs.uid).equals("", ignoreCase = true)) {
            user_id = ""
        } else {
            user_id = SharedPrefs.getString(this@Default_image_activity, SharedPrefs.uid)
        }
        val call = api.getcaseimages(1, SharedPrefs.getString(this@Default_image_activity, SharedPrefs.country_code), user_id, Locale.getDefault().language)



        Log.i("TAG_2", "Platform: 1")
        Log.i("TAG_2", "Country code: ${SharedPrefs.getString(this@Default_image_activity, SharedPrefs.country_code)}")
        Log.i("TAG_2", "UID: $user_id")
        Log.i("TAG_2", "Language: ${Locale.getDefault().language}")


        call!!.enqueue(object : Callback<customimage_response?> {
            override fun onResponse(call: Call<customimage_response?>, response: Response<customimage_response?>) {

                if (response.body()!!.success) {
                    pd.dismiss()

                    val isEditable = response.body()!!.isEditable
                    val datumList = response.body()!!.data
                    for (datum in datumList) {
                        val getdefault_images = getdefault_images("" + datum.id!!, "" + datum.name, "" + datum.image, "" + datum.flag)
                        sqlist.add(getdefault_images)
                    }

                    /* if (!isEditable) {
                         sqlist.removeAt(0) // For removing custom photo editor
                     }*/


                    Share.default_image_sqlist.addAll(sqlist)
                    Share.subDataArrayList_category.addAll(sqlist)



                    default_image_adapter.notifyDataSetChanged()
                    default_image_adapter.setEditable(isEditable)
                    //  default_image_adapter.setEditable(false)
                }

            }

            override fun onFailure(call: Call<customimage_response?>, t: Throwable) {
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    val alertDialog = AlertDialog.Builder(this@Default_image_activity).create()
                    alertDialog.setTitle(getString(R.string.time_out))
                    alertDialog.setCancelable(false)
                    alertDialog.setMessage(getString(R.string.connect_time_out))
                    alertDialog.setButton(getString(R.string.retry)) { dialog, which ->
                        dialog.dismiss()
                        pd.dismiss()
                        getMainData()
                    }
                    alertDialog.show()
                } else {
                    val alertDialog = AlertDialog.Builder(this@Default_image_activity)
                    alertDialog.setTitle(getString(R.string.internet_connection))
                    alertDialog.setCancelable(false)
                    alertDialog.setMessage(getString(R.string.slow_connect))
                    alertDialog.setPositiveButton(getString(R.string.retry)) { dialog, which ->
                        dialog.dismiss()
                        pd.dismiss()
                        getMainData()
                    }
                    alertDialog.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                        dialog.dismiss()
                        pd.dismiss()
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

    companion object {
        var sqlist_images: MutableList<get_images> = ArrayList()
        var imagedata: List<case_images_data>? = null
        var activity: Activity? = null
        var model_name: String? = null
        var model_id: String? = null
        var total_amount: String? = null
        var paid_amount: String? = null
        var width: String? = null
        var height: String? = null
    }
}
