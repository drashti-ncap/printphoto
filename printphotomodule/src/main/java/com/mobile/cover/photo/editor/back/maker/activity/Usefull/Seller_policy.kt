package com.mobile.cover.photo.editor.back.maker.activity.Usefull

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Base64
import androidx.appcompat.app.AppCompatActivity
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.policy_response_dashboard
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient
import kotlinx.android.synthetic.main.activity_seller_policy.*
import retrofit2.Call
import retrofit2.Callback
import java.nio.charset.StandardCharsets

class Seller_policy : PrintPhotoBaseActivity() {
  //  internal lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_policy)
        findviews()
        getMainData()
    }

    private fun findviews() {
        id_back.setOnClickListener { finish() }
    }

    private fun getMainData() {


        //pd = ProgressDialog.show(this@Seller_policy, "", getString(R.string.loading), true, false)
        showProgressDialog(this@Seller_policy)
        val api = MainApiClient(this@Seller_policy).apiInterface


        val call = api.getprivacypolicy()


        call!!.enqueue(object : Callback<policy_response_dashboard?> {
            override fun onResponse(call: Call<policy_response_dashboard?>, response: retrofit2.Response<policy_response_dashboard?>) {
                val customimage_response = response.body()
                if (customimage_response!!.responseCode.equals("1", ignoreCase = true)) {
                    val data = Base64.decode(customimage_response.privacyPolicy, Base64.DEFAULT)
                    val text = String(data, StandardCharsets.UTF_8)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tv_policy.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        tv_policy.text = Html.fromHtml(text)
                    }
                    //pd.dismiss()
                    hideProgressDialog()

                }
            }

            override fun onFailure(call: Call<policy_response_dashboard?>, t: Throwable) {
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    val alertDialog = AlertDialog.Builder(this@Seller_policy).create()
                    alertDialog.setTitle(getString(R.string.time_out))
                    alertDialog.setCancelable(false)
                    alertDialog.setMessage(getString(R.string.connect_time_out))
                    alertDialog.setButton(getString(R.string.ok)) { dialog, which ->
                        dialog.dismiss()
                        //pd.dismiss()
                        hideProgressDialog()
                        getMainData()
                    }
                    alertDialog.show()
                } else {
                    val alertDialog = AlertDialog.Builder(this@Seller_policy)
                    alertDialog.setTitle(getString(R.string.internet_connection))
                    alertDialog.setCancelable(false)
                    alertDialog.setMessage(getString(R.string.slow_connect))
                    alertDialog.setPositiveButton(getString(R.string.retry)) { dialog, which ->
                        dialog.dismiss()
                        //pd.dismiss()
                        hideProgressDialog()
                        getMainData()
                    }
                    alertDialog.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
                        dialog.dismiss()
                        //pd.dismiss()
                        hideProgressDialog()
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


}
