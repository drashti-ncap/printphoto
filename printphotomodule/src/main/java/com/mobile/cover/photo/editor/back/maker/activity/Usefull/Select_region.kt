package com.mobile.cover.photo.editor.back.maker.activity.Usefull

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.cover.photo.editor.back.maker.Commen.NetworkManager.alertDialog
import com.mobile.cover.photo.editor.back.maker.Commen.Share
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.ARG_IS_CART
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.ARG_IS_OFFER
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.ARG_POS
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.HomeMainActivity
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.checkout.SelectAddressActivity
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.getConfiguration
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.saveConfiguration
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.region_adapter
import com.mobile.cover.photo.editor.back.maker.model.mallstatusresponse
import com.mobile.cover.photo.editor.back.maker.model.region_model_data
import kotlinx.android.synthetic.main.activity_select_region.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Select_region : PrintPhotoBaseActivity() {
    internal var spinnercodeArray = ArrayList<region_model_data>()
    internal var selected_pos: Int = 0
    internal lateinit var region_adapter: region_adapter
//    var pd: ProgressDialog? = null

    val TAG ="Select_region"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_region)

        spinnercodeArray.clear()
        Log.e(TAG, "onCreate:==== "+Share.country_mobile_code)
        for (country in Share.country_mobile_code.indices) {
            val region_model_data = region_model_data()
            region_model_data.region_code = Share.country_mobile_code[country].sortname
            region_model_data.region_name = Share.country_mobile_code[country].name
            region_model_data.select = false
            spinnercodeArray.add(region_model_data)
        }

        findviews()
    }

    override fun onResume() {
        super.onResume()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    private fun get_all_configurations() {

        if(isFinishing && isDestroyed){
            return
        }


            //pd = ProgressDialog.show(this@Select_region, "", getString(R.string.loading), true, false)
        showProgressDialog(this@Select_region)
        val api = MainApiClient(this@Select_region).apiInterface
            val call = api.get_mall_enable_status(SharedPrefs.getString(this@Select_region, SharedPrefs.country_code))

            call!!.enqueue(object : Callback<mallstatusresponse?> {
                override fun onResponse(call: Call<mallstatusresponse?>, response: Response<mallstatusresponse?>) {
                    if (response.code() == 200) {
                        if (response.body()!!.responseCode.equals("1", ignoreCase = true)) {
                            Share.mall_enable = response.body()!!.mallEnableStatus.mall_status_enable.toString()
                            saveConfiguration(this@Select_region,response.body()!!.mallEnableStatus)
                            val intent = Intent(this@Select_region, HomeMainActivity::class.java)
                            val isCart = getIntent().getBooleanExtra(ARG_IS_CART, false)
                            val isOffer = intent.getBooleanExtra(ARG_IS_OFFER, false)
                            val whichClick = intent.getIntExtra(ARG_POS, -1)
                            Log.i(TAG, "isCart: $isCart")
                            intent.putExtra(ARG_IS_CART, isCart)
                            intent.putExtra(ARG_IS_OFFER, isOffer)
                            intent.putExtra(ARG_POS, whichClick)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@Select_region, response.body()!!.responseMessage, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        //pd?.dismiss()
                        hideProgressDialog()
                        if (alertDialog != null) {
                            alertDialog?.dismiss()
                        }
                        alertDialog = AlertDialog.Builder(this@Select_region).create()
                        alertDialog?.setTitle(getString(R.string.server_error))
                        alertDialog?.setCancelable(false)
                        alertDialog?.setMessage(getString(R.string.server_under_maintenance))
                        alertDialog?.setButton(getString(R.string.ok)) { dialog, which ->
                            dialog.dismiss()
                            finish()
                        }
                        alertDialog?.show()
                    }
                }

                override fun onFailure(call: Call<mallstatusresponse?>, t: Throwable) {
                    if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                        if (alertDialog != null) {
                            alertDialog?.dismiss()
                        }
                        alertDialog = AlertDialog.Builder(this@Select_region).create()
                        alertDialog?.setTitle(getString(R.string.time_out))
                        alertDialog?.setCancelable(false)
                        alertDialog?.setMessage(getString(R.string.connect_time_out))
                        alertDialog?.setButton(getString(R.string.ok)) { dialog, which ->
                            dialog.dismiss()
                            //pd?.dismiss()
                            hideProgressDialog()
                            get_all_configurations()
                        }
                        alertDialog?.show()
                    } else if (t.toString().contains("Handshake failed") || t.toString().contains("Failed to connect to printphoto")) {
                        if (alertDialog != null) {
                            alertDialog?.dismiss()
                        }
                        alertDialog = AlertDialog.Builder(this@Select_region).create()
                        alertDialog?.setTitle(getString(R.string.server_error))
                        alertDialog?.setCancelable(false)
                        alertDialog?.setMessage(getString(R.string.server_under_maintenance))
                        alertDialog?.setButton(getString(R.string.ok)) { dialog, which ->
                            dialog.dismiss()
                            //pd?.dismiss()
                            hideProgressDialog()
                            finish()
                        }
                        alertDialog?.show()
                    } else {
                        val alertDialog = AlertDialog.Builder(this@Select_region)
                        alertDialog.setTitle(getString(R.string.internet_connection))
                        alertDialog.setCancelable(false)
                        alertDialog.setMessage(getString(R.string.slow_connect))
                        alertDialog.setPositiveButton(getString(R.string.retry)) { dialog, which ->
                            dialog.dismiss()
                            //pd?.dismiss()
                            hideProgressDialog()
                            get_all_configurations()
                        }
                        alertDialog.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                            dialog.dismiss()
                            //pd?.dismiss()
                            hideProgressDialog()
                            finish()
                        }
                        alertDialog.show()
                    }
                }
            })

    }


    private fun findviews() {
        iv_done.setOnClickListener {
            val check = ArrayList<Boolean>()
            for (i in spinnercodeArray.indices) {
                if (spinnercodeArray[i].select!!) {
                    Share.countryCodeValue = spinnercodeArray[i].region_code
                    SharedPrefs.save(this@Select_region, SharedPrefs.country_code, spinnercodeArray[i].region_code)
                    check.add(true)
                }
            }

            if (check.size == 1) {
                get_all_configurations()
            } else {
                Toast.makeText(this@Select_region, getString(R.string.select_region), Toast.LENGTH_SHORT).show()
            }
        }
        region_adapter = region_adapter(this@Select_region, spinnercodeArray, tv_no_fnd)
        val linear_layout = LinearLayoutManager(this@Select_region)
        rv_region_recycler.layoutManager = linear_layout
        rv_region_recycler.adapter = region_adapter

        Share.region_search_array.clear()
        Share.region_search_array.addAll(spinnercodeArray)
        ed_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                spinnercodeArray = region_adapter.filter(charSequence.toString())
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })

        btn_continue.setOnClickListener {
            for (i in Share.country_mobile_code.indices) {
                if (i == selected_pos) {
                    Share.countryCodeValue = Share.country_mobile_code[i].sortname
                    SharedPrefs.save(this@Select_region, SharedPrefs.country_code, Share.country_mobile_code[i].sortname)
                }
            }
            val intent = Intent(this@Select_region, HomeMainActivity::class.java)
            startActivity(intent)
        }
    }

}
