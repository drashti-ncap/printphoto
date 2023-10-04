package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.auth

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.jdrodi.BaseActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.messaging.FirebaseMessaging
import com.mobile.cover.photo.editor.back.maker.Commen.Share
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.otp_response_data
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.registration_main_response
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.autodetectionotp.AutoDetectOTP
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.getSendPriority
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.ForgetpasswordActivity
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.LogInActivity
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq
import kotlinx.android.synthetic.main.new_activity_phone_auth.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.TimeUnit


private const val TAG = "PhoneAuthActivity"
private const val KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress"

class PhoneAuthActivity : PrintPhotoBaseActivity(), View.OnClickListener {


    private var phoneNumber: String = ""
    private var mEmail: String = ""
   // private var pd: ProgressDialog? = null
    private var bulkOTP = 0
    private var isInternational = 1
    private var mAuth: FirebaseAuth? = null
    private var mVerificationId: String? = null
    private var mResendToken: ForceResendingToken? = null
    private var isRunning = false
    private var isBultOTP = false


    private val mCallbacks = object : OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.e(TAG, "onVerificationFailed->" + e.localizedMessage)
            if (isRunning) {
                isRunning = false
                countDownTimer.cancel()
            }

            sendbulkotp()
            /* showAlert(getString(R.string.alert), e.localizedMessage, getString(R.string.ok), null, object : OnPositive {
                 override fun onYes(isPositive: Boolean) {
                     if (isPositive) {
                         onBackPressed()
                     }
                 }
             })*/
        }

        override fun onCodeSent(verificationId: String, token: ForceResendingToken) {
            dismisDialog()
            Log.d(TAG, "onCodeSent:$verificationId")
            startTimer()
            mVerificationId = verificationId
            mResendToken = token
        }
    }

    private val countDownTimer: CountDownTimer = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            isRunning = true
            val remainingTime = getString(R.string.second_remaining) + millisUntilFinished / 1000
            tv_timer!!.text = remainingTime
        }

        override fun onFinish() {
            isRunning = false
            tv_timer!!.visibility = View.GONE
            ll_resend!!.visibility = View.VISIBLE
            buttonVerifyPhone!!.isEnabled = false
            buttonVerifyPhone!!.alpha = 0.5f
        }
    }

    lateinit var autoDetectOTP: AutoDetectOTP


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_activity_phone_auth)
        savedInstanceState?.let { onRestoreInstanceState(it) }
        autoDetectOTP = AutoDetectOTP(this)
        initData()
        initActions()
        getOtpMessage()
    }

    /**
     * Method is used to set the received otp sms into textview
     * @param receivedOtp
     * @param enteredOtp
     */
    private fun getOtpMessage() {
        autoDetectOTP.startSmsRetriver(object : AutoDetectOTP.SmsCallback {
            override fun connectionFailed() {
              Toast.makeText(this@PhoneAuthActivity, "Failed", Toast.LENGTH_SHORT).show()
            }

            override fun connectionSuccess(aVoid: Void) {
                Toast.makeText(this@PhoneAuthActivity, "Success", Toast.LENGTH_SHORT).show()
            }

            override fun smsCallback(sms: String) {
                if (sms.contains(":") && sms.contains(".")) {
                    var receivedOtp = sms.substring(sms.indexOf(":") + 1, sms.indexOf(".")).trim { it <= ' ' }
                    Toast.makeText(this@PhoneAuthActivity, "Success:$receivedOtp", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onStop() {
        super.onStop()
        autoDetectOTP.stopSmsReciever()
    }



    fun initActions() {
        id_back.setOnClickListener(this)
        id_reset.setOnClickListener(this)
        buttonVerifyPhone.setOnClickListener(this)
        buttonResend.setOnClickListener(this)

    }


    fun initData() {

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        hideKeyBoard(tv_title, this@PhoneAuthActivity)

        buttonVerifyPhone.isEnabled = false
        buttonVerifyPhone.alpha = 0.5f

        val countryCode = intent.getStringExtra("country_code")
        val number = intent.getStringExtra("mobno")
        mEmail = intent.getStringExtra("emailid")!!

        phoneNumber = countryCode!! + number

        mAuth = FirebaseAuth.getInstance()

        when (val lCountryCode = (SharedPrefs.getString(this@PhoneAuthActivity, SharedPrefs.country_code))) {
            "IN" -> {
                isInternational = 0

                Log.e("CHECKOTP", "initData: indian_sms-->"+Share.indian_sms_priority )
                if (Share.indian_sms_priority == "0") {
                    Log.e("CHECKOTP", "INDIA SMS_PRIORITY : Firebase")
                } else {
                    Log.e("CHECKOTP", "INDIA SMS_PRIORITY : Bulk")
                }

                if (Share.indian_sms_priority == "0") {
                    Log.e("CHECKOTP", "SMS_IN : Firebase")

                    fieldPhoneNumber.text = phoneNumber
                    tv_title.text = getString(R.string.we_sent_you_code_to_nverify_your_mobile_number)
                    sendFirebaseOTP(false)

                } else {
                    Log.e(TAG, "SMS_IN : Bulk")
                    fieldPhoneNumber.text = phoneNumber
                    tv_title.text = getString(R.string.we_sent_you_code_to_nverify_your_mobile_number)
                    sendbulkotp()
                }

            }
            else -> {

                var isMobile = false
                for (i in Share.country_mobile_code.indices) {
                    if (SharedPrefs.getString(this@PhoneAuthActivity, SharedPrefs.country_code).equals(Share.country_mobile_code[i].sortname, ignoreCase = true)) {
                        if (Share.country_mobile_code[i].is_branch == 1) {
                            isMobile = true
                        }
                    }
                }

                if (lCountryCode == "SA") {

                    if (Share.bulksms == "0") {
                        Log.e(TAG, "SA SMS_PRIORITY : Firebase")
                    } else {
                        Log.e(TAG, "SA SMS_PRIORITY : Bulk")
                    }

                    if (isMobile) {



                        if (Share.bulksms == "0") {
                            Log.e(TAG, "SMS_SA : Firebase")
                            fieldPhoneNumber.text = phoneNumber
                            tv_title.text = getString(R.string.we_sent_you_code_to_nverify_your_mobile_number)
                            sendFirebaseOTP(false)
                        } else {
                            Log.e(TAG, "SMS_SA : Bulk")
                            fieldPhoneNumber.text = phoneNumber
                            tv_title.text = getString(R.string.we_sent_you_code_to_nverify_your_mobile_number)
                            sendbulkotp()
                        }
                    } else {


                        Log.e(TAG, "SA SMS_PRIORITY : EMAIL")
                        fieldPhoneNumber.text = mEmail
                        tv_title.text = getString(R.string.otp_has_been_send_email)
                        sendbulkotp()
                    }
                } else {
                    if (isMobile) {


                        if (Share.bulksms == "0") {
                            Log.e(TAG, "INTERNATIONAL SMS_PRIORITY : Firebase")
                        } else {
                            Log.e(TAG, "INTERNATIONAL SMS_PRIORITY : Bulk")
                        }

                        if (Share.international_sms_priority == "0") {

                            Log.e(TAG, "SMS_OTHER : Firebase")

                            fieldPhoneNumber.text = phoneNumber
                            tv_title.text = getString(R.string.we_sent_you_code_to_nverify_your_mobile_number)
                            sendFirebaseOTP(false)
                        } else {
                            Log.e(TAG, "SMS_OTHER : Bulk")

                            fieldPhoneNumber.text = phoneNumber
                            tv_title.text = getString(R.string.we_sent_you_code_to_nverify_your_mobile_number)
                            sendbulkotp()
                        }
                    } else {

                        Log.e(TAG, "INTERNATIONAL SMS_PRIORITY : EMAIL")

                        fieldPhoneNumber.text = mEmail
                        tv_title.text = getString(R.string.otp_has_been_send_email)
                        sendbulkotp()
                    }
                }
            }
        }


    }

    override fun onClick(view: View) {
        when (view) {
            buttonVerifyPhone -> {
                val code = pinview!!.value
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(this, getString(R.string.please_enter_otp), Toast.LENGTH_SHORT).show()
                    return
                }
                verifyPhoneNumberWithCode(mVerificationId, code)
            }

            buttonResend -> {
                pinview!!.value = ""
                if (getSendPriority() == "0") {
                    sendFirebaseOTP(true)
                } else {
                    sendbulkotp()
                }
            }

            id_back -> {
                when {
                    Share.iscart -> {
                        Share.iscart = false
                        finish()
                    }
                    Share.isorder -> {
                        Share.iscart = false
                        Share.isorder = false
                    }
                    else -> {
                        finish()
                    }
                }
            }
            id_reset -> {
                if (!pinview.value.equals("", ignoreCase = true)) {
                    pinview.clear()
                }
            }
        }
    }

    private fun startTimer() {
        buttonVerifyPhone!!.isEnabled = true
        buttonVerifyPhone!!.alpha = 1.0f
        tv_timer!!.visibility = View.VISIBLE
        ll_resend!!.visibility = View.GONE
        countDownTimer.start()
    }

    fun hideKeyBoard(view: View?, mActivity: Activity) {
        val imm = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)
    }


    private fun sendbulkotp() {

        isBultOTP = true

//        if (pd == null)
//            pd = ProgressDialog.show(this@PhoneAuthActivity, "", getString(R.string.loading), true, false)
        showProgressDialog(this@PhoneAuthActivity)


        val api = MainApiClient(this@PhoneAuthActivity).apiInterface
        val number: String
        if (intent.getStringExtra("verify").equals("1", ignoreCase = true)) {
            isInternational = 1
            number = ""
        } else {
            mEmail = ""
            number = fieldPhoneNumber!!.text.toString()
        }
        val call = api.getotp(number, mEmail, Locale.getDefault().language, "" + isInternational)
        call!!.enqueue(object : Callback<otp_response_data?> {


            override fun onResponse(call: Call<otp_response_data?>, response: Response<otp_response_data?>) {
                Log.e(TAG, "onResponse: " + response.isSuccessful)
                if (response.isSuccessful) {
                    val responseData = response.body()
                    Log.e("RESPONSE", "onResponse: " + responseData!!.responseCode + "=======>" + responseData.responseMessage)
                    if (responseData.responseCode.equals("1", ignoreCase = true)) {
                        dismisDialog()
                        Log.e("OTP", "onResponse: " + responseData.getotp_data().otp)
                        bulkOTP = responseData.getotp_data().otp
                        startTimer()
                    } else if (responseData.responseCode.equals("0", ignoreCase = true)) {
                        dismisDialog()
                        Toast.makeText(this@PhoneAuthActivity, responseData.responseMessage, Toast.LENGTH_SHORT).show()
                    }
                    dismisDialog()
                } else {
                    dismisDialog()
                    Toast.makeText(this@PhoneAuthActivity, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<otp_response_data?>, t: Throwable) {
                dismisDialog()
                Log.e(TAG, "onFailure: ======>$t")
                Log.e(TAG, "onFailure: ======>" + t.message)
                Log.e(TAG, "onFailure: ======>" + t.localizedMessage)
            }
        })
    }

    private fun dismisDialog() {
//        if (pd != null)
//            pd?.dismiss()
//        pd = null
        hideProgressDialog()
    }

    private fun sendFirebaseOTP(isResend: Boolean) {
        Log.e("CHECKOTP", "send:===========>>>>>>>Sending OTP ")
        //pd = ProgressDialog.show(this@PhoneAuthActivity, "", getString(R.string.loading), true, false)
        showProgressDialog(this@PhoneAuthActivity)
        if (isResend) {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, mCallbacks, mResendToken)
        } else {
            if (!validatePhoneNumber()) {
                return
            }

            Log.e("CHECKOTP", "sendFirebaseOTP: no-->$phoneNumber")
            val options = PhoneAuthOptions.newBuilder(mAuth!!)
                .setPhoneNumber(phoneNumber) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(mCallbacks) // OnVerificationStateChangedCallbacks
                .build()
            try {
                PhoneAuthProvider.verifyPhoneNumber(options)
            }catch (e:FirebaseException){
                e.printStackTrace()
                Log.e("CHECKOTP", "sendFirebaseOTP: exception-->${e.message}")
            }

            //PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60.toLong(), TimeUnit.SECONDS, this, mCallbacks)
        }
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        // [END verify_with_code]
        if (isBultOTP) {
            val otp = code.toInt()
            if (bulkOTP == otp) {
                Share.bulksms = "0"
                if (Share.forgetpassword == 1) {
                    forgetpass()
                } else {
                    sendData()
                }
            } else {
                Toast.makeText(this, getString(R.string.please_enter_valid_otp), Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.e(TAG, "verifyPhoneNumberWithCode: $verificationId=========>$code")
            if (verificationId != null) {
                val credential = PhoneAuthProvider.getCredential(verificationId, code)
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(this, getString(R.string.please_enter_valid_otp), Toast.LENGTH_SHORT).show()
            }
        }
    }


    // [END resend_verification]
    // [START sign_in_with_phone]
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                if (Share.forgetpassword == 1) {
                    forgetpass()
                } else {
                    sendData()
                }
            } else {
                Log.w(TAG, "signInWithCredential:failure", task.exception)

                var alertDialog: AlertDialog? = null
                if (task.exception!!.localizedMessage.contains("network error")) {
                    if (alertDialog != null) {
                        alertDialog.dismiss()
                    }
                    alertDialog = AlertDialog.Builder(this@PhoneAuthActivity).create()
                    alertDialog.setTitle(getString(R.string.internet_connection))
                    alertDialog.setCancelable(false)
                    alertDialog.setMessage(getString(R.string.slow_connect))
                    alertDialog.setButton(getString(R.string.retry), DialogInterface.OnClickListener { dialog1, which ->
                        dialog1.dismiss()
                        signInWithPhoneAuthCredential(credential)
                    })
                    alertDialog.show()
                } else if (task.exception.toString().toLowerCase().contains("invalid")) {
                    if (alertDialog != null) {
                        alertDialog.dismiss()
                    }
                    alertDialog = AlertDialog.Builder(this@PhoneAuthActivity).create()
                    alertDialog.setTitle(getString(R.string.alert))
                    alertDialog.setMessage(getString(R.string.invalid_otp))
                    alertDialog.setCancelable(false)
                    alertDialog.setButton(getString(R.string.retry), DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                    alertDialog.show()
                } else if (task.exception.toString().toLowerCase().contains("expired")) {
                    if (alertDialog != null) {
                        alertDialog.dismiss()
                    }
                    alertDialog = AlertDialog.Builder(this@PhoneAuthActivity).create()
                    alertDialog.setTitle(getString(R.string.alert))
                    alertDialog.setMessage("" + task.exception)
                    alertDialog.setCancelable(false)
                    alertDialog.setButton(getString(R.string.ok), DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                    alertDialog.show()
                } else {
                    Toast.makeText(this@PhoneAuthActivity, getString(R.string.please_enter_valid_otp), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun sendData() {
        val fname = intent.getStringExtra("fname")
        val lname = intent.getStringExtra("lname")
        val mobile_no = intent.getStringExtra("mobno")
        val password = intent.getStringExtra("pswd")
        val emailid = intent.getStringExtra("emailid")
        var android_id = ""
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(TAG, msg)
            android_id=msg.toString()
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })

        Log.e(TAG, "sendData: $fname")
        Log.e(TAG, "sendData: $lname")
        Log.e(TAG, "sendData: $mobile_no")
        Log.e(TAG, "sendData: $password")
        Log.e(TAG, "sendData: $emailid")
        Log.e(TAG, "sendData: $android_id")
        //risk@ccavenue.com


        //pd = ProgressDialog.show(this@PhoneAuthActivity, "", resources.getString(R.string.registring), true, false)
        showProgressDialog(this@PhoneAuthActivity)

        val apiService = MainApiClient(this@PhoneAuthActivity).apiInterface
        val regResponseCall: Call<registration_main_response?>? = apiService.getRegResponse("$fname $lname", "" + emailid, "", mobile_no, "", intent.getStringExtra("city"), "", password, android_id, TimeZone.getDefault().id, Locale.getDefault().language)
        regResponseCall!!.enqueue(object : Callback<registration_main_response?> {
            override fun onResponse(call: Call<registration_main_response?>, response: Response<registration_main_response?>) {
                dismisDialog()
                if (response.body() != null) {
                    if (response.body()!!.responseCode.equals("1", ignoreCase = true)) {
                        if (response.body()!!.data == null) {
                            val alertDialog = AlertDialog.Builder(this@PhoneAuthActivity).create()
                            alertDialog.setTitle(getString(R.string.registration))
                            alertDialog.setCancelable(false)
                            alertDialog.setMessage(response.body()!!.responseMessage)
                            alertDialog.setButton(getString(R.string.ok)) { dialog, which ->
                                Share.iscart = false
                                Share.upload = false
                                Share.isorder = false
                                dialog.dismiss()
                            }
                            alertDialog.show()
                        } else {
                            SharedPrefs.savePref(this@PhoneAuthActivity, Share.key_reg_suc, false)
                            val regData = response.body()!!.data
                            if (regData != null) {
                                SharedPrefs.save(this@PhoneAuthActivity, Share.key_ + RegReq.name, regData.name)
                                SharedPrefs.save(this@PhoneAuthActivity, Share.key_ + RegReq.email, regData.email)
                                SharedPrefs.save(this@PhoneAuthActivity, Share.key_ + RegReq.mobile_1, regData.mobile1)
                                //                                SharedPrefs.save(PhoneAuthActivity.this, Share.key_ + RegReq.id, regData.getId());
                            }
                            val alertDialog = AlertDialog.Builder(this@PhoneAuthActivity).create()
                            alertDialog.setTitle(getString(R.string.registration))
                            alertDialog.setCancelable(false)
                            alertDialog.setMessage(getString(R.string.registration_successfull))
                            alertDialog.setButton(getString(R.string.ok)) { dialog, which ->
                                dialog.dismiss()
                                Share.isregistration = true
                                Share.isRegistrationSuccess = true
                                Share.tempPassword = password
                                /*   Share.iscart = false
                                   Share.upload = false
                                   Share.isorder = false*/
                                if (LogInActivity.logInActivity != null) {
                                    LogInActivity.logInActivity.finish()
                                }
                                finish()
                            }
                            alertDialog.show()
                        }
                    }
                } else {
                    if(response.body()!=null && response.body()!!.responseCode!=null) {
                        if (response.body()!!.responseCode.equals("0", ignoreCase = true)) {
                            val alertDialog = AlertDialog.Builder(this@PhoneAuthActivity).create()
                            alertDialog.setTitle(getString(R.string.registration))
                            alertDialog.setCancelable(false)
                            alertDialog.setMessage(response.body()!!.responseMessage)
                            alertDialog.setButton(getString(R.string.ok)) { dialog, which -> dialog.dismiss() }
                            alertDialog.show()
                        } else {
                            val alertDialog = AlertDialog.Builder(this@PhoneAuthActivity).create()
                            alertDialog.setTitle(getString(R.string.registration))
                            alertDialog.setCancelable(false)
                            alertDialog.setMessage(getString(R.string.number_has_already_registered))
                            alertDialog.setButton(getString(R.string.ok)) { dialog, which ->
                                dialog.dismiss()
                                finish()
                                enableViews(buttonVerifyPhone!!, buttonResend!!, fieldPhoneNumber!!)
                            }
                            alertDialog.show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<registration_main_response?>, t: Throwable) {
                Log.e("regRes", "F==>$t")
                dismisDialog()
                val alertDialog = AlertDialog.Builder(this@PhoneAuthActivity).create()
                alertDialog.setTitle(getString(R.string.registration))
                alertDialog.setCancelable(false)
                alertDialog.setMessage(getString(R.string.something_went_wrong_try_agaian))
                alertDialog.setButton(getString(R.string.ok)) { dialog, which -> dialog.dismiss() }
                alertDialog.show()
            }
        })
    }


    private fun forgetpass() {
        finish()
        if (SharedPrefs.getString(this@PhoneAuthActivity, SharedPrefs.country_code).equals("IN", ignoreCase = true)) {
            val intent = Intent(applicationContext, ForgetpasswordActivity::class.java)
            intent.putExtra("mobile", getIntent().getStringExtra("mobno"))
            startActivity(intent)
        } else {
            val intent = Intent(applicationContext, ForgetpasswordActivity::class.java)
            intent.putExtra("mobile", getIntent().getStringExtra("emailid"))
            startActivity(intent)
        }
    }


    private fun validatePhoneNumber(): Boolean {
        val phoneNumber = fieldPhoneNumber!!.text.toString()
        if (TextUtils.isEmpty(phoneNumber)) {
            fieldPhoneNumber!!.error = getString(R.string.invalid_mobile_num)
            fieldPhoneNumber!!.requestFocus()
            return false
        }
        return true
    }

    private fun enableViews(vararg views: View) {
        for (v in views) {
            v.isEnabled = true
        }
    }


    public override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }



}


