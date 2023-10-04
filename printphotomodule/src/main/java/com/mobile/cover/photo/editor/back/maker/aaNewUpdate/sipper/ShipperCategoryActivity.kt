package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.sipper

import android.Manifest
import android.Manifest.permission
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Point
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.cover.photo.editor.back.maker.Commen.Share
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.mug_image_response_data
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.mug_image_response
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.sipper.adapter.ShipperAdapter
import com.mobile.cover.photo.editor.back.maker.mainapplication
import com.mobile.cover.photo.editor.back.maker.testing_modules.ImageData
import com.mobile.cover.photo.editor.back.maker.testing_modules.dimension_kajal_maulik.activity.Shipper_MainActivity
import com.mobile.cover.photo.editor.back.maker.utility.PathUtil
import kotlinx.android.synthetic.main.activity_mug_images_category_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URISyntaxException

class ShipperCategoryActivity : PrintPhotoBaseActivity() {
    internal var alertDialog: AlertDialog? = null
//    internal lateinit var pd: ProgressDialog
    internal lateinit var shipperAdapter: ShipperAdapter
    private var application: mainapplication? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mug_images_category_activity)
        mActivity = this@ShipperCategoryActivity
        getDisplaySize()

        title_name.text = getString(R.string.shipper_bottle)
        id_back.setOnClickListener {
            finish()
            Share.login_back =
                !SharedPrefs.getString(this@ShipperCategoryActivity, SharedPrefs.last_country_code)
                    .equals(
                        SharedPrefs.getString(
                            this@ShipperCategoryActivity,
                            SharedPrefs.country_code
                        ), ignoreCase = true
                    )
        }
        rv_list.setHasFixedSize(true)
        val mLayoutManager = GridLayoutManager(applicationContext, 2)

        val gridSpacingItemDecoration = GridSpacingItemDecoration(2, 12, true)
        rv_list.addItemDecoration(gridSpacingItemDecoration)

        rv_list.layoutManager = mLayoutManager
        rv_list.itemAnimator = DefaultItemAnimator()

        ll_test.visibility = View.GONE
        if (Share.shipper_bottle_image_array.size == 0) {
            getMainData()
        } else {
            shipperAdapter = ShipperAdapter(mActivity, Share.shipper_bottle_image_array)
            rv_list.adapter = shipperAdapter

            shipperAdapter.setOnItemClickListener { v, position ->
                if (checkAndRequestPermissionsStorage(2)) {
                    if (position != 0) {
                        val intent = Intent(Intent.ACTION_GET_CONTENT)
                        intent.type = "image/*"
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                        intent.putExtra("from", "" + 0)
                        Toast.makeText(
                            this@ShipperCategoryActivity,
                            "Select " + Share.itemnum + " image/images",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivityForResult(intent, 111)
                    }
                }
            }

        }


    }

    private fun getDisplaySize() {
        val display = window.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        Share.screenWidth = size.x
        Share.screenHeight = size.y
    }

    override fun onResume() {
        super.onResume()
        getDisplaySize()
    }

    override fun onBackPressed() {
        finish()
        Share.login_back =
            !SharedPrefs.getString(this@ShipperCategoryActivity, SharedPrefs.last_country_code)
                .equals(
                    SharedPrefs.getString(
                        this@ShipperCategoryActivity,
                        SharedPrefs.country_code
                    ), ignoreCase = true
                )
    }

    private fun checkAndRequestPermissionsCamera(code: Int): Boolean {

        if (ContextCompat.checkSelfPermission(
                this@ShipperCategoryActivity,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@ShipperCategoryActivity, arrayOf(Manifest.permission.CAMERA),
                code
            )
            return false
        } else {
            return true
        }
    }

    private fun checkAndRequestPermissionsStorage(code: Int): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    (this as Activity),
                    arrayOf(permission.READ_MEDIA_IMAGES),
                    code
                )
                false
            } else {
                true
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    (this as Activity),
                    arrayOf(permission.READ_EXTERNAL_STORAGE),
                    code
                )
                false
            } else {
                true
            }
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                    this,
                    WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    (this as Activity),
                    arrayOf(permission.READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE),
                    code
                )
                false
            } else {
                true
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (permissions.size == 0) {
            return
        }
        var allPermissionsGranted = true
        if (grantResults.size > 0) {
            for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false
                    break
                }
            }
        }
        if (!allPermissionsGranted) {
            var somePermissionsForeverDenied = false
            for (permission in permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    //denied
                    Log.e("denied", permission)
                    if (requestCode == 1) {
                        ActivityCompat.requestPermissions(
                            this@ShipperCategoryActivity,
                            arrayOf(Manifest.permission.CAMERA),
                            1
                        )
                    }
                    if (requestCode == 2) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            ActivityCompat.requestPermissions(
                                this@ShipperCategoryActivity,
                                arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                                2
                            )
                        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            ActivityCompat.requestPermissions(
                                this@ShipperCategoryActivity,
                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                2
                            )
                        } else {
                            ActivityCompat.requestPermissions(
                                this@ShipperCategoryActivity,
                                arrayOf(
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    WRITE_EXTERNAL_STORAGE
                                ),
                                2
                            )
                        }

                    }

                } else {

                    if (ActivityCompat.checkSelfPermission(
                            this,
                            permission
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        //allowed
                        Log.e("allowed", permission)
                        //                        if (requestCode==2){
                        //                            Log.e("GRANTED", "checkAndRequestPermissionsStorage:=======> " );
                        //                        }

                    } else {
                        //set to never ask again
                        Log.e("set to never ask again", permission)
                        somePermissionsForeverDenied = true
                    }
                }
            }
            if (somePermissionsForeverDenied) {

                if (requestCode == 1) {
                    val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle(getString(R.string.permission_required))
                        .setMessage(getString(R.string.permission_sentence))
                        .setPositiveButton(getString(R.string.cancel)) { dialog, which -> }
                        .setNegativeButton(getString(R.string.ok)) { dialog, which ->
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", packageName, null)
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                        .setCancelable(false)
                        .create()
                        .show()
                }
                if (requestCode == 2) {
                    val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle(getString(R.string.permission_required))
                        .setMessage(getString(R.string.permission_sentence_storage))
                        .setPositiveButton(getString(R.string.cancel)) { dialog, which -> }
                        .setNegativeButton(getString(R.string.ok)) { dialog, which ->
                            val intent = Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", packageName, null)
                            )
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                        .setCancelable(false)
                        .create()
                        .show()
                }

            }
        } else {
            when (requestCode) {
                1 -> {
                }
                else -> {
                }
            }
        }
    }

    private fun getMainData() {

        //pd = ProgressDialog.show(mActivity, "", getString(R.string.loading), true, false)
        showProgressDialog(mActivity)

        Share.mall_main_category_data.clear()

        val apiService = MainApiClient(mActivity!!).apiInterface
        val call = apiService.get_shipper_images()

        call!!.enqueue(object : Callback<mug_image_response?> {
            override fun onResponse(
                call: Call<mug_image_response?>,
                response: Response<mug_image_response?>
            ) {
                if (response.code() == 200) {
                    //pd.dismiss()
                    hideProgressDialog()
                    val mall_new_main_model = response.body()
                    if (mall_new_main_model!!.success!!) {
                        Share.shipper_bottle_image_array.addAll(mall_new_main_model.data)

                        Share.shipper_bottle_image_array.add(0, mug_image_response_data(0, "", ""))
                        shipperAdapter = ShipperAdapter(mActivity, Share.shipper_bottle_image_array)
                        rv_list.adapter = shipperAdapter

                        shipperAdapter.setOnItemClickListener { v, position ->
                            if (checkAndRequestPermissionsStorage(2)) {
                                if (position != 0) {
                                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                                    intent.type = "image/*"
                                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                                    intent.putExtra("from", "" + 0)
                                    Toast.makeText(
                                        this@ShipperCategoryActivity,
                                        "Select " + Share.itemnum + " image/images",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    startActivityForResult(intent, 111)
                                }
                            }
                        }
                    } else {
                        Toast.makeText(mActivity, mall_new_main_model.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    //pd.dismiss()
                    hideProgressDialog()
                    if (alertDialog != null) {
                        alertDialog!!.dismiss()
                    }
                    alertDialog = AlertDialog.Builder(mActivity).create()
                    alertDialog!!.setTitle(getString(R.string.server_error))
                    alertDialog!!.setCancelable(false)
                    alertDialog!!.setMessage(getString(R.string.server_under_maintenance))
                    alertDialog!!.setButton(getString(R.string.ok)) { dialog, which ->
                        dialog.dismiss()
                        finish()
                    }
                    alertDialog!!.show()
                }
            }

            override fun onFailure(call: Call<mug_image_response?>, t: Throwable) {
                error_dialogs(t)
            }
        })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111) {
            if (resultCode == Activity.RESULT_OK) {
                val temp = ArrayList<String>()
                this.application = mainapplication.getsInstance()
                application!!.selectedImages.clear()
                mainapplication.org_selectedImages.clear()
                mainapplication.temp_org_selectedImages.clear()
                mainapplication.selectedImages.clear()
                Log.e("SELECTEDDATA", "onActivityResult: share item num==>" + Share.itemnum)
                if (data!!.clipData != null) {
                    Log.e(
                        "SELECTEDDATA", "onActivityResult: image size==>" + data.clipData!!
                            .itemCount
                    )
                    for (i in 0 until Share.itemnum) {
                        try {
                            val filePath = PathUtil.getPath(
                                this, data.clipData!!
                                    .getItemAt(i).uri
                            )
                            Log.e(
                                "SELECTEDDATA",
                                "onActivityResult: file path-->$filePath"
                            )
                            temp.add(filePath)
                        } catch (e: URISyntaxException) {
                            Log.e("SELECTEDDATA", "onActivityResult: exception-->" + e.message)
                            e.printStackTrace()
                        }
                    }
                    Log.e("SELECTEDDATA", "onActivityResult: temp size-->" + temp.size)
                    for (i in temp.indices) {
                        val imageData = ImageData()
                        imageData.setTemp_imagePath(temp[i])
                        imageData.setImagePath(temp[i])
                        imageData.setTemp_org_imagePath(temp[i])
                        application!!.addSelectedImage(imageData)
                    }
                } else {
                    Log.e("SELECTEDDATA", "onActivityResult: image ==>" + data.data)
                    try {
                        val filePath = PathUtil.getPath(this, data.data)
                        Log.e("SELECTEDDATA", "onActivityResult: file path-->$filePath")
                        temp.add(filePath)
                    } catch (e: URISyntaxException) {
                        Log.e("SELECTEDDATA", "onActivityResult: exception-->" + e.message)
                        e.printStackTrace()
                    }
                    for (i in temp.indices) {
                        val imageData = ImageData()
                        imageData.setTemp_imagePath(temp[i])
                        imageData.setImagePath(temp[i])
                        imageData.setTemp_org_imagePath(temp[i])
                        application!!.addSelectedImage(imageData)
                    }
                }
                val intent = Intent(this, Shipper_MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun error_dialogs(t: Throwable) {
        //pd.dismiss()
        hideProgressDialog()
        Log.e("MESSAGE", "error_dialogs: " + t.message)
        Log.e("MESSAGE", "error_dialogs: " + t.localizedMessage)
        if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
            if (alertDialog != null) {
                alertDialog!!.dismiss()
            }
            alertDialog = AlertDialog.Builder(mActivity).create()
            alertDialog!!.setTitle(getString(R.string.time_out))
            alertDialog!!.setCancelable(false)
            alertDialog!!.setMessage(getString(R.string.connect_time_out))
            alertDialog!!.setButton(getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
                getMainData()
            }
            alertDialog!!.show()
        } else {
            if (alertDialog != null) {
                alertDialog!!.dismiss()
            }
            alertDialog = AlertDialog.Builder(mActivity).create()
            alertDialog!!.setTitle(getString(R.string.internet_connection))
            alertDialog!!.setCancelable(false)
            alertDialog!!.setMessage(getString(R.string.slow_connect))
            alertDialog!!.setButton(getString(R.string.retry)) { dialog, which ->
                dialog.dismiss()
                getMainData()
            }
            alertDialog!!.show()
        }
    }

    inner class GridSpacingItemDecoration(
        private val spanCount: Int,
        private val spacing: Int,
        private val includeEdge: Boolean
    ) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column

            if (includeEdge) {
                outRect.left =
                    spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right =
                    (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right =
                    spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f / spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }

    companion object {
        var mActivity: Activity? = null
    }
}
