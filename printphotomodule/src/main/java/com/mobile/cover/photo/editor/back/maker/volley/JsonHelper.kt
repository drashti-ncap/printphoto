package com.mobile.cover.photo.editor.back.maker.volley

import android.content.Context
import com.example.jdrodi.utilities.SPHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.setting_model_main_response
import com.mobile.cover.photo.editor.back.maker.model.new_main_model


private const val isTesting = false

// Default Values
private const val DEFAULT_COUNTRY_CODE = ""



// KEYS
private const val KEY_BASE_URL = "base_url"
private const val KEY_DEVELOPER_BASE_URL = "dev_base_url"
private const val KEY_PAYTM_BASE_URL = "paytm_base_url"

/**
 * ToDo: Base URL's
 */
fun Context.saveBaseURL(baseURL: String) {
    SPHelper(this).save(KEY_BASE_URL, baseURL)
}

fun Context.getBaseURL(): String {
    var baseURL = SPHelper(this).getString(KEY_BASE_URL, "")
    if (isTesting) {
        baseURL = SPHelper(this).getString(KEY_DEVELOPER_BASE_URL, "")
    }
    return if (baseURL.isNullOrEmpty() || baseURL.isNullOrBlank()) {
        DEFAULT_COUNTRY_CODE
    } else {
        baseURL
    }
}

fun Context.saveDevBaseURL(baseURL: String) {
    SPHelper(this).save(KEY_DEVELOPER_BASE_URL, baseURL)
}

fun Context.savePaytmBaseURL(baseURL: String) {
    SPHelper(this).save(KEY_PAYTM_BASE_URL, baseURL)
}

fun Context.getPaytmBaseURL(): String {
    val baseURL = SPHelper(this).getString(KEY_PAYTM_BASE_URL, "")
    return if (baseURL.isNullOrEmpty() || baseURL.isNullOrBlank()) {
        DEFAULT_COUNTRY_CODE
    } else {
        baseURL
    }
}

fun Context.getConfigurationData(jsonString: String?): setting_model_main_response {
    val itemType = object : TypeToken<setting_model_main_response>() {}.type
    return Gson().fromJson<setting_model_main_response>(jsonString, itemType)
}


fun Context.getCategories(jsonString: String?): new_main_model {
    val itemType = object : TypeToken<new_main_model>() {}.type
    return Gson().fromJson<new_main_model>(jsonString, itemType)
}