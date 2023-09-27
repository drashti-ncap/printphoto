package com.mobile.cover.photo.editor.back.maker.aaNewUpdate

import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.text.format.Formatter
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ads.narayan.admob.AppOpenManager
import com.facebook.applinks.AppLinkData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import com.mobile.cover.photo.editor.back.maker.Alarm_notification.CAlarmReceiver
import com.mobile.cover.photo.editor.back.maker.BuildConfig
import com.mobile.cover.photo.editor.back.maker.Commen.GlobalData
import com.mobile.cover.photo.editor.back.maker.Commen.Share
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.setting_model_main_response
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.APIService
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.URL_CONFIG
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events.logAppOpenEvent
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.*
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.Select_region
import com.onesignal.OSPermissionObserver
import com.onesignal.OSPermissionStateChanges
import com.onesignal.OneSignal
import kotlinx.android.synthetic.main.activity_splash_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Double
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.Boolean
import kotlin.Exception
import kotlin.IllegalStateException
import kotlin.RuntimeException
import kotlin.String
import kotlin.Throwable
import kotlin.system.exitProcess

const val ARG_IS_CART = "arg_cart"
const val ARG_IS_OFFER = "arg_offer"

class SplashScreen(val context: Activity) : AppCompatActivity(), OSPermissionObserver {
    internal var TAG = "SplashScreen"
    internal var pInfo: PackageInfo? = null
    internal lateinit var alarmManager: AlarmManager
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var pendingIntent: PendingIntent? = null
    var pd: ProgressDialog? = null
    private var isCart = false
    private var isOffer = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_splash_screen)
        handleIntent(intent)
        initData()
        logAppOpenEvent()

    }


    fun initData() {
        if(intent!=null) {
            isOffer = intent.getBooleanExtra(ARG_IS_OFFER, false)
        }
        AppOpenManager.getInstance().setEnableScreenContentCallback(true)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context)
        OneSignal.addPermissionObserver(this);
        loggers()

        generatefacebookhashkey()


        generate_firabase_token_id()

        AppLinkData.fetchDeferredAppLinkData(context) { appLinkData ->
        }
        initviews()
    }


    fun initviews() {
        val display = context.window.windowManager.defaultDisplay
        GlobalData.screenHeight = display.height
        GlobalData.screenWidth = display.width
        SharedPrefs.save(context, SharedPrefs.SCREEN_HEIGHT, GlobalData.screenHeight)
        SharedPrefs.save(context, SharedPrefs.SCREEN_WIDTH, GlobalData.screenWidth)

        try {
            pInfo = context.packageManager.getPackageInfo(/*context.packageName*/"com.mobile.cover.photo.editor.back.maker", 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        get_all_configurations()

        //id_appversion.text = "Version : " + pInfo!!.versionName
    }


    override fun onOSPermissionChanged(stateChanges: OSPermissionStateChanges) {
        if (stateChanges.from.areNotificationsEnabled() &&
            !stateChanges.to.areNotificationsEnabled()
        ) {
            AlertDialog.Builder(context)
                .setMessage("Notifications Disabled!")
                .show()
        }
        Log.i("Debug", "onOSPermissionChanged: $stateChanges")
    }

    fun get_all_configurations() {

        pd = ProgressDialog.show(context, "", context.getString(R.string.loading), true, false)
        val mainApiClient = MainApiClient(context)
        val apiService = mainApiClient.client!!.create(APIService::class.java)
        Log.e(
            "CHECKCODE",
            "get_all_configurations: country code-->" + SharedPrefs.getString(
                context,
                SharedPrefs.country_code
            )
        )
        val call = apiService.get_configurations(
            SharedPrefs.getString(
                context,
                SharedPrefs.country_code
            )
        )
        //   val call = api.get_configurations("IN")
        println("\n")
        httpRequest(URL_CONFIG)

        call!!.enqueue(object : Callback<setting_model_main_response?> {
            override fun onResponse(
                call: Call<setting_model_main_response?>,
                response: Response<setting_model_main_response?>
            ) {


                if (response.code() == 200) {

                    httpResponse("$URL_CONFIG: a $response")
                    Log.e("CHECKRESPONSE", "onResponse: response==>"+ response.body()!!.responseCode)
                    if (response.body()!!.responseCode.equals("1", ignoreCase = true)) {
                        Log.e(
                            TAG,
                            "onResponse: " + Double.valueOf(response.body()!!.data.androidVersion)
                        )
                        Log.e(TAG, "onResponse: " + Double.valueOf(pInfo!!.versionName))
                        if (Double.valueOf(response.body()!!.data.androidVersion) <= Double.valueOf(
                                pInfo!!.versionName)
                        ) {
                            Log.e(TAG, "onResponse:=========>Version matched")



                            dismissDialog()
                            saveProductIdType(context,response.body()!!.data.productType)
                            saveConfiguration(context,response.body()!!.data)
                            saveCancelReasons(context,response.body()!!.data.cancel_reasons)
                            Share.bulksms = response.body()!!.data.bulkSms
                            Share.mall_enable = response.body()!!.data.mall_status_enable.toString()
                            Share.complain_ticket_url = response.body()!!.data.tickets_url
                            Share.currency_details = response.body()!!.data.currencies
                            Log.e(TAG, "onCreate: " + response.body()!!.data.countries)
                            Share.country_mobile_code = response.body()!!.data.countries
                            Log.e(TAG, "onCreate: " + Share.country_mobile_code)
                            Share.international_sms_priority =
                                response.body()!!.data.international_sms_priority
                            Share.indian_sms_priority = response.body()!!.data.indian_sms_priority


                            saveWAIndia(context,response.body()!!.data.wa_india)
                            saveWASaudi(context,response.body()!!.data.wa_saudi)
                            saveWAInternational(context,response.body()!!.data.wa_internatinal)
                            saveWAHome(context,response.body()!!.data.wa_home)
                            saveOfferAmount(context,response.body()!!.data.prepaidoffer.toInt())
                            saveSendPriority(context,response.body()!!.data.resend_sms_priority)


                            redirectToNext()

                        } else {
                            if (response.body()!!.data.androidForcefullyUpdate.equals(
                                    "1",
                                    ignoreCase = true
                                )
                            ) {

                                dismissDialog()
                                Log.e(TAG, "onResponse:=========>Version not matched")

                                val title = getString(R.string.youAreNotUpdatedTitle)
                                val msg = getString(R.string.youAreNotUpdatedMessage)
                                val positiveText = "Update"
                                showAlert(title, msg, positiveText, null, object : OnPositive {
                                    override fun onYes(isPositive: Boolean) {
                                        if (isPositive) {
                                            rateApp()
                                        }
                                    }
                                })

                            } else {
                                dismissDialog()
                                saveProductIdType(context,response.body()!!.data.productType)
                                saveConfiguration(context,response.body()!!.data)
                                saveCancelReasons(context,response.body()!!.data.cancel_reasons)
                                Share.bulksms = response.body()!!.data.bulkSms
                                Share.complain_ticket_url = response.body()!!.data.tickets_url
                                redirectToNext()
                            }
                        }
                    } else {
                        dismissDialog()
                        httpResponse("$URL_CONFIG: Failure")
                        errorDialog("APPLOCK", object : OnPositive {
                            override fun onYes(isPositive: Boolean) {
                                if (isPositive) {
                                    get_all_configurations()
                                } else {
                                    exitProcess(0)
                                }

                            }
                        })
                    }
                } else {
                    dismissDialog()
                    httpFailure("$URL_CONFIG: xyz" + (getString(R.string.server_error)))
                    errorDialog(getString(R.string.server_error), object : OnPositive {
                        override fun onYes(isPositive: Boolean) {
                            if (isPositive) {
                                get_all_configurations()
                            } else {
                                exitProcess(0)
                            }

                        }
                    })
                }
            }

            override fun onFailure(call: Call<setting_model_main_response?>, t: Throwable) {
                dismissDialog()
                httpFailure("$URL_CONFIG: abc ${t.localizedMessage}")
                errorDialog(t.toString(), object : OnPositive {
                    override fun onYes(isPositive: Boolean) {
                        if (isPositive) {
                            get_all_configurations()
                        } else {
                            exitProcess(0)
                        }
                    }
                })
            }
        })

    }

    fun redirectToNext() {
        if (SharedPrefs.getString(context ,SharedPrefs.country_code)
                .equals("", ignoreCase = true)
        ) {
            val intent = Intent(context, Select_region::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra(ARG_IS_CART, isCart)
            intent.putExtra(ARG_IS_OFFER, isOffer)
            context.startActivity(intent)
        } else {
            Share.countryCodeValue =
                SharedPrefs.getString(context, SharedPrefs.country_code)
            val intent = Intent(context, HomeMainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra(ARG_IS_CART, isCart)
            intent.putExtra(ARG_IS_OFFER, isOffer)
            context.startActivity(intent)
        }
    }


    private fun generate_firabase_token_id() {
        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val regid = task.result

                // Log and toast

            })
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            Log.e("TAG", "device_token1 :$e")
        } catch (e: Exception) {
            Log.e("TAG", "device_token2 :$e")
            e.printStackTrace()
        }

    }


    private fun generatefacebookhashkey() {
        val info: PackageInfo
        try {
            info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                Log.e("hash key", something)
            }
        } catch (e1: PackageManager.NameNotFoundException) {
            Log.e("name not found", e1.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.e("no such an algorithm", e.toString())
        } catch (e: Exception) {
            Log.e("exception", e.toString())
        }

    }

    private fun loggers() {
        val params_for_tvremote = Bundle()
        params_for_tvremote.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "PrintPhoto")
        mFirebaseAnalytics!!.logEvent("Print_PHOTO", params_for_tvremote)
    }

    override fun onBackPressed() {
        exitdialog()
    }


    fun exitdialog() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setCancelable(false)
        alertDialog.setMessage(getString(R.string.are_you_exit))
        alertDialog.setNegativeButton(getString(R.string.no)) { dialog, which -> dialog.dismiss() }
        alertDialog.setPositiveButton("EXIT") { dialog, which ->
            if (SharedPrefs.getInt(applicationContext, SharedPrefs.CART_COUNT) == 0) {
                alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val myIntent = Intent(applicationContext, CAlarmReceiver::class.java)
                myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                pendingIntent = PendingIntent.getBroadcast(
                    applicationContext, 0, myIntent, PendingIntent.FLAG_IMMUTABLE
                )

                alarmManager.cancel(pendingIntent)
                dialog.dismiss()
                finish()
            } else if (SharedPrefs.getInt(applicationContext, SharedPrefs.CART_COUNT) > 0) {

                val preff = SharedPrefs()
                SharedPrefs.save(applicationContext, "noti_count", 0)

                val updateTime = Calendar.getInstance()
                updateTime.timeZone = TimeZone.getDefault()
                updateTime.set(Calendar.HOUR_OF_DAY, 12)
                updateTime.set(Calendar.MINUTE, 30)
                val myIntent = Intent(
                    applicationContext,
                    CAlarmReceiver::class.java
                ) //get class from LocalNotification folder
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                pendingIntent = PendingIntent.getBroadcast(
                    applicationContext,
                    0,
                    myIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )

                alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + AlarmManager.INTERVAL_DAY,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )
                dialog.dismiss()
                finish()
            } else {
                dialog.dismiss()
                finish()
            }
        }
        alertDialog.create().show()
    }

    public override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }

    /*  companion object {
          @Volatile
          var instance : SplashScreen ?= null

          fun getInstance() = instance ?: synchronized(this) {
              instance ?: SplashScreen().also { instance = it }
          }
      }*/
    companion object {
        // Singleton instance
        @Volatile
        private var instance: SplashScreen? = null

        // Get the singleton instance
       /* fun getInstance(): SplashScreen {
            return instance ?: synchronized(this) {
                instance ?: SplashScreen().also { instance = it }
            }
        }*/
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        val appLinkAction = intent.action
        val appLinkData: Uri? = intent.data
        if (Intent.ACTION_VIEW == appLinkAction && appLinkData != null) {
            val params = appLinkData.pathSegments
            Log.i(TAG, "URI: $appLinkData")
            val linkType = params[params.size - 1]
            Log.i(TAG, "linkType: $linkType")
            if (linkType == "1") {
                isCart = true
            }
            Log.i(TAG, "isCart: $isCart")
        } else {
            Log.i(TAG, "handleIntent: null")
        }


    }


    fun dismissDialog() {
        try {
            if (isDestroyed) {
                return
            }
            if (pd != null && pd!!.isShowing) {
                pd!!.dismiss()
            }
        } catch (e: java.lang.Exception) {
            Log.e("Dismiss Dialog", e.toString())
        }
    }
}



