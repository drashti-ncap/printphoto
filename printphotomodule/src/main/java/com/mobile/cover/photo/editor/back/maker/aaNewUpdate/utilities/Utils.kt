package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Uri
import android.text.InputFilter
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mobile.cover.photo.editor.back.maker.R
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.nio.charset.Charset

fun truncate(str: String, len: Int): String? {
    return if (str.length > len) {
        str.substring(0, len) + "..."
    } else {
        str
    }
}

fun Context.rateApp() {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
    } catch (e: ActivityNotFoundException) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
    }
}

fun setEditTextMaxLength(editText: EditText, length: Int) {
    val filterArray: Array<InputFilter?> = arrayOfNulls<InputFilter>(1)
    filterArray[0] = InputFilter.LengthFilter(length)
    editText.filters = filterArray
}

fun Context.getBaseUrl(): String {
    val encoded = getString(R.string.base_url_live)
    val dataDec: ByteArray = Base64.decode(encoded, Base64.DEFAULT)
    var decodedString = ""
    try {
        decodedString = String(dataDec, Charset.forName("UTF-8"))
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
    } finally {
        return decodedString
    }
}

fun Context.getBaseUrlPaytm(): String {
    val encoded = getString(R.string.base_url_paytm)
    val dataDec: ByteArray = Base64.decode(encoded, Base64.DEFAULT)
    var decodedString = ""
    try {
        decodedString = String(dataDec, Charset.forName("UTF-8"))
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
    } finally {
        return decodedString
    }
}

fun Context.getUrlBitmap(imagePath: String): Bitmap? {
    return Glide.with(this).asBitmap().load(imagePath).submit().get()
}

/**
 * ToDo.. Return true if internet or wi-fi connection is working fine
 *
 *
 * Required permission
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
 * <uses-permission android:name="android.permission.INTERNET"></uses-permission>
 *
 * @param mContext The context
 * @return true if you have the internet connection, or false if not.
 */
fun Context.isOnline(): Boolean {
    var haveConnectedWifi = false
    var haveConnectedMobile = false
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    @SuppressLint("MissingPermission") val activeNetwork = cm.activeNetworkInfo
    if (activeNetwork != null) { // connected to the internet
        if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
            if (activeNetwork.isConnected) haveConnectedWifi = true
        } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
            if (activeNetwork.isConnected) haveConnectedMobile = true
        }
    }
    return haveConnectedWifi || haveConnectedMobile
}


fun Context.sendWhatsappDirectMessage() {
    try {
        val fullNumber: String = getWANumber()!!
        val textMsg = ""
        val sb = StringBuilder()
        sb.append("whatsapp://send/?text=")
        sb.append(URLEncoder.encode(textMsg, "UTF-8"))
        sb.append("&phone=")
        sb.append(fullNumber)
        val intent = Intent("android.intent.action.VIEW", Uri.parse(sb.toString()))
        startActivity(intent)
    } catch (unused: ActivityNotFoundException) {
        Toast.makeText(this, R.string.update_whatsapp, Toast.LENGTH_SHORT).show()
    } catch (unused2: UnsupportedEncodingException) {
        Toast.makeText(this, R.string.text_error, Toast.LENGTH_SHORT).show()
    }
}

fun Context.sendWhatsappDirectMessageHome() {
    try {
        val fullNumber: String = getWAHome()!!
        val textMsg = ""
        val sb = StringBuilder()
        sb.append("whatsapp://send/?text=")
        sb.append(URLEncoder.encode(textMsg, "UTF-8"))
        sb.append("&phone=")
        sb.append(fullNumber)
        val intent = Intent("android.intent.action.VIEW", Uri.parse(sb.toString()))
        startActivity(intent)
    } catch (unused: ActivityNotFoundException) {
        Toast.makeText(this, R.string.update_whatsapp, Toast.LENGTH_SHORT).show()
    } catch (unused2: UnsupportedEncodingException) {
        Toast.makeText(this, R.string.text_error, Toast.LENGTH_SHORT).show()
    }
}

private const val SOURCE_SCALE_WIDTH = 1080 // scale width of ui
private const val SOURCE_SCALE_HEIGHT = 1920 // scale height of ui

/**
 * ToDo.. Set width and height of view
 *
 * @param view     The view whose width and height are to be set
 * @param v_width  The width to be set
 * @param v_height The height to be set
 */
fun Context.setHeightWidth(view: View, v_width: Int, v_height: Int) {
    if (v_width == v_height) {
        setHeightWidth(view, v_width)
    } else {
        val dm = resources.displayMetrics
        val width = dm.widthPixels * v_width / SOURCE_SCALE_WIDTH
        val height = dm.heightPixels * v_height / SOURCE_SCALE_HEIGHT
        view.layoutParams.width = width
        view.layoutParams.height = height
    }
}

/**
 * ToDo.. Set width and height of view
 *
 * @param view     The view whose width and height are to be set
 * @param v_width_height  The width and height to be set
 */
fun Context.setHeightWidth(view: View, v_width_height: Int) {
    val dm = resources.displayMetrics
    val width = dm.widthPixels * v_width_height / SOURCE_SCALE_WIDTH
    val height = dm.widthPixels * v_width_height / SOURCE_SCALE_WIDTH
    view.layoutParams.width = width
    view.layoutParams.height = height
}