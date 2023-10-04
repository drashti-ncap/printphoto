package com.mobile.cover.photo.editor.back.maker.activity.Usefull

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mobile.cover.photo.editor.back.maker.Commen.Share
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.activity.ModelListActivity
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.StickerView
import com.mobile.cover.photo.editor.back.maker.model.Cart
import kotlinx.android.synthetic.main.activity_jewellery_activity.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class jewellery_activity : PrintPhotoBaseActivity(), View.OnClickListener {
    //var progress: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jewellery_activity)
        val textView: TextView = findViewById<TextView>(R.id.title)
        textView.text = Share.locket_model_data.modalName
        Glide.with(this).load(Share.locket_model_data.n_mug_image).into(iv_locket_img)
        if (Share.locket_model_data.locket_details.size == 2) {
            ed_text_1.visibility = View.VISIBLE
            ed_text_2.visibility = View.VISIBLE
            ed_text_1.filters = arrayOf(InputFilter.LengthFilter(Share.locket_model_data.locket_details.get(0).locket_text_max_length.toInt()))
            ed_text_2.filters = arrayOf(InputFilter.LengthFilter(Share.locket_model_data.locket_details.get(1).locket_text_max_length.toInt()))

        } else {
            ed_text_1.filters = arrayOf(InputFilter.LengthFilter(Share.locket_model_data.locket_details.get(0).locket_text_max_length.toInt()))
            ed_text_2.visibility = View.GONE
            ll_ed_2.visibility = View.GONE
        }

        initlistener()
    }

    private fun initlistener() {
        btn_add_to_cart.setOnClickListener(this)
        id_back.setOnClickListener(this)
        id_reset.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (p0 == btn_add_to_cart) {
            if (Share.locket_model_data.locket_details.size == 2) {
                if (ed_text_1.text.toString().isEmpty() || ed_text_2.text.toString().isEmpty()) {
                    Toast.makeText(this@jewellery_activity, "Please enter text", Toast.LENGTH_LONG).show()
                    return
                }
            } else {
                if (ed_text_1.text.toString().isEmpty()) {
                    Toast.makeText(this@jewellery_activity, "Please enter text", Toast.LENGTH_LONG).show()
                    return
                }
            }
            if (!SharedPrefs.getBoolean(this, Share.key_reg_suc)) {
                val alertDialog = AlertDialog.Builder(this@jewellery_activity)
                alertDialog.setTitle(getString(R.string.log_in))
                alertDialog.setCancelable(false)
                alertDialog.setMessage(getString(R.string.need_login))
                alertDialog.setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    dialog.dismiss()
                    val intent = Intent(this@jewellery_activity, LogInActivity::class.java)
                    startActivity(intent)
                }
                alertDialog.setNegativeButton(getString(R.string.cancel)) { dialog, which -> dialog.dismiss() }

                alertDialog.create().show()

                return
            }
            val alertDialog = AlertDialog.Builder(this@jewellery_activity)
            alertDialog.setCancelable(false)
            alertDialog.setMessage("Are you sure ?")
            alertDialog.setPositiveButton(getString(R.string.yes)) { dialog, which ->
                dialog.dismiss()
                crateReq().execute()
            }
            alertDialog.setNegativeButton(getString(R.string.no)) { dialog, which -> dialog.dismiss() }

            alertDialog.create().show()
        }
        if (p0 == id_back) {
            hideKeyBoard(id_back, this)
            finish()
        }
        if (p0 == id_reset) {
            ed_text_1.setText("")
            ed_text_2.setText("")
        }
    }

    fun hideKeyBoard(view: View, mActivity: Activity) {
        val imm = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private inner class crateReq : AsyncTask<Void?, Void?, Void?>() {

        internal var builder: MultipartBody.Builder? = null

        override fun onPreExecute() {
            super.onPreExecute()
//            progress = ProgressDialog(this@jewellery_activity)
//            progress!!.setMessage(getString(R.string.loading))
//            progress!!.show()
            showProgressDialog(this@jewellery_activity)

            builder = MultipartBody.Builder()
        }

        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            if (builder != null) {
                val multipartBody = builder!!.build()
                val apiService = MainApiClient(this@jewellery_activity).apiInterface
                val cartCall = apiService.sendCart(multipartBody)
                cartCall!!.enqueue(object : Callback<Cart?> {
                    override fun onResponse(call: Call<Cart?>, response: Response<Cart?>) {

//                        if (progress != null && progress!!.isShowing)
//                            progress!!.dismiss()
                        hideProgressDialog()

                        if (response != null) {
                            if (response.body()!!.responseCode.equals("1", ignoreCase = true)) {
                                Share.upload = true
                                StickerView.mStickers.clear()
                                if (ModelListActivity.activity != null) {
                                    ModelListActivity.activity.finish()
                                }

                                finish()
                                overridePendingTransition(R.anim.app_right_in, R.anim.app_left_out)
                            } else {
                                val alertDialog = AlertDialog.Builder(this@jewellery_activity)
                                alertDialog.setTitle("Upload Failed")
                                alertDialog.setCancelable(false)
                                alertDialog.setMessage("Something went to wrong. Please try again Later")
                                alertDialog.setPositiveButton(getString(R.string.ok)) { dialog, which -> dialog.dismiss() }

                                alertDialog.create().show()
                            }
                        }
                        Log.d("response", "==>$response")
                    }

                    override fun onFailure(call: Call<Cart?>, t: Throwable) {
                        Log.d("response", "Faied==>$t")
//                        if (progress != null && progress!!.isShowing)
//                            progress!!.dismiss()
                        hideProgressDialog()

                        if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                            val alertDialog = AlertDialog.Builder(this@jewellery_activity).create()
                            alertDialog.setTitle(getString(R.string.time_out))
                            alertDialog.setMessage(getString(R.string.connect_time_out))
                            alertDialog.setButton(getString(R.string.retry)) { dialog, which ->
                                dialog.dismiss()
                                crateReq().execute()
                            }
                            alertDialog.show()
                        } else {
                            val alertDialog = AlertDialog.Builder(this@jewellery_activity).create()
                            alertDialog.setTitle(getString(R.string.internet_connection))
                            alertDialog.setMessage(getString(R.string.slow_connect))
                            alertDialog.setButton(getString(R.string.retry)) { dialog, which ->
                                dialog.dismiss()
                                crateReq().execute()
                            }
                            alertDialog.show()
                        }
                    }
                })
            }

        }

        override fun doInBackground(vararg voids: Void?): Void? {
            builder!!.setType(MultipartBody.FORM)
            builder!!.addFormDataPart("user_id", SharedPrefs.getString(this@jewellery_activity, Share.key_ + RegReq.id))
            builder!!.addFormDataPart("quantity", "1")
            builder!!.addFormDataPart("model_id", "" + Share.locket_model_data.modelId)
            builder!!.addFormDataPart("ln", Locale.getDefault().language)

            if (Share.locket_model_data.locket_details.size == 2) {
                builder!!.addFormDataPart("locket_text[0]", "" + ed_text_1.text.toString())
                builder!!.addFormDataPart("locket_text[1]", "" + ed_text_2.text.toString())
            } else {
                builder!!.addFormDataPart("locket_text[0]", "" + ed_text_1.text.toString())
            }

            return null
        }
    }

}
