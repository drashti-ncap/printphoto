package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobile.cover.photo.editor.back.maker.Commen.OnSingleClickListener
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.numberverify
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient
import kotlinx.android.synthetic.main.activity_change_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ChangePasswordActivity : PrintPhotoBaseActivity() {

  //  private var moProgressDialog: ProgressDialog? = null

    private var isTaskRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        initView()
    }

    private fun initView() {
        iv_reset!!.setOnClickListener {
            etOldPassword!!.setText("")
            etNewPassword!!.setText("")
            etConfirmPassword!!.setText("")

            etOldPassword!!.error = null
            etNewPassword!!.error = null
            etConfirmPassword!!.error = null
        }

        llChangePassword!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View?) {
                if (isTaskRunning) {
                    Toast.makeText(this@ChangePasswordActivity, getString(R.string.please_wait), Toast.LENGTH_LONG).show()
                } else {
                    validateControls()
                }
            }
        })

        ivBack!!.setOnClickListener { onBackPressed() }

    }

    private fun validateControls() {
        etOldPassword!!.error = null
        etNewPassword!!.error = null
        etConfirmPassword!!.error = null

        var isValid = true

        if (etOldPassword!!.text.toString().trim { it <= ' ' }.isEmpty()) {
            etOldPassword!!.error = getString(R.string.change_enter_old_pass)
            isValid = false
        }
        if (etNewPassword!!.text.toString().trim { it <= ' ' }.isEmpty()) {
            etNewPassword!!.error = getString(R.string.change_enter_new_pass)
            isValid = false
        }
        if (etConfirmPassword!!.text.toString().trim { it <= ' ' }.isEmpty()) {
            etConfirmPassword!!.error = getString(R.string.change_confirm_pass)
            isValid = false
        }
        if (etNewPassword!!.text.toString().trim { it <= ' ' } != etConfirmPassword!!.text.toString().trim { it <= ' ' }) {
            etNewPassword!!.error = getString(R.string.pass_does_not_match)
            etConfirmPassword!!.error = getString(R.string.pass_does_not_match)
            isValid = false
        }
        if (etNewPassword!!.text.toString().trim { it <= ' ' }.length < 6) {
            etNewPassword!!.error = getString(R.string.pass_limit)
            isValid = false
        }


        if (isValid) {
            ChangePasswordAPICall()
        }

    }

    private fun ChangePasswordAPICall() {


//        moProgressDialog = ProgressDialog.show(this@ChangePasswordActivity, "", getString(R.string.loading), true, false)
//        moProgressDialog!!.show()
        showProgressDialog(this@ChangePasswordActivity)
        val api = MainApiClient(this@ChangePasswordActivity).apiInterface

        val call = api.changePassword(SharedPrefs.getString(this, SharedPrefs.uid), etOldPassword!!.text.toString().trim { it <= ' ' }, etNewPassword!!.text.toString().trim { it <= ' ' }, Locale.getDefault().language)
        isTaskRunning = true
        call!!.enqueue(object : Callback<numberverify?> {
            val TAG = "test"

            override fun onResponse(call: Call<numberverify?>, response: Response<numberverify?>) {
                Log.e(TAG, "onResponse: " + response.isSuccessful)
                if (response.isSuccessful) {
                    val responseData = response.body()
                    Log.e("RESPONSE", "onResponse: " + responseData!!.responseCode)
                    if (responseData.responseCode.equals("1", ignoreCase = true)) {
                        Toast.makeText(this@ChangePasswordActivity, getString(R.string.pass_change_successfully), Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@ChangePasswordActivity, responseData.responseMessage, Toast.LENGTH_SHORT).show()
                    }
                    //moProgressDialog!!.dismiss()
                    hideProgressDialog()
                } else {
//                    moProgressDialog!!.dismiss()
                    hideProgressDialog()
                    Toast.makeText(this@ChangePasswordActivity, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
                }
                isTaskRunning = false
            }

            override fun onFailure(call: Call<numberverify?>, t: Throwable) {
//                moProgressDialog!!.dismiss()
                hideProgressDialog()
                Log.e(TAG, "onFailure: ======>$t")
                Log.e(TAG, "onFailure: ======>" + t.message)
                Log.e(TAG, "onFailure: ======>" + t.localizedMessage)
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    val alertDialog = AlertDialog.Builder(this@ChangePasswordActivity).create()
                    alertDialog.setTitle(getString(R.string.time_out))
                    alertDialog.setMessage(getString(R.string.connect_time_out))
                    alertDialog.setButton(getString(R.string.retry)) { dialog, which ->
                        dialog.dismiss()
                        ChangePasswordAPICall()
                    }
                    alertDialog.show()
                } else {
                    val alertDialog = AlertDialog.Builder(this@ChangePasswordActivity).create()
                    alertDialog.setTitle(getString(R.string.internet_connection))
                    alertDialog.setMessage(getString(R.string.slow_connect))
                    alertDialog.setButton(getString(R.string.retry)) { dialog, which ->
                        dialog.dismiss()
                        ChangePasswordAPICall()
                    }
                    alertDialog.show()
                }
                isTaskRunning = false
            }
        })

    }

    public override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }

    companion object {

        private val TAG = "EditRegDataActivity"
    }


}
