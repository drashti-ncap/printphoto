package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities

import android.content.Context
import com.example.jdrodi.utilities.SPHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.ProductType
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.setting_response_data
import com.mobile.cover.photo.editor.back.maker.model.CancelReason
import com.mobile.cover.photo.editor.back.maker.model.model_details_data

private const val isTesting = true


// KEYS
private const val KEY_WA_INDIA = "wa_india"
private const val KEY_WA_SAUDI = "wa_saudi"
private const val KEY_WA_INTERNATIONAL = "wa_international"
private const val KEY_WA_HOME = "wa_home"
private const val KEY_PRODUCT_ID_TYPE = "product_id_type"
private const val KEY_MODEL_DATA = "model_data"
private const val KEY_CONFIGURATIONS = "configurations_data"
private const val KEY_PRODUCT_ID = "product_id"
private const val KEY_CASE_ID = "case_id"
private const val KEY_OFFER_AMOUNT = "offer_amount"
private const val KEY_MODEL_DETAILS = "model_details"
private const val KEY_CANCEL_REASONS = "cancel_reasons"
private const val KEY_RESEND_PRIORITY = "resend_priority"

fun Context.getWANumber(): String? {
    return when (SharedPrefs.getString(this, SharedPrefs.country_code)) {
        "IN" -> {
            getWAIndia()
        }
        "SA" -> {
            getWASaudi()
        }
        else -> {
            getWAInternational()
        }
    }
}


fun saveWAIndia(mContext: Context,waNumber: String) {
    SPHelper(mContext).save(KEY_WA_INDIA, waNumber)
}

fun Context.getWAIndia(): String? {
    return SPHelper(this).getString(KEY_WA_INDIA, "")
}


fun saveWASaudi(mContext: Context,waNumber: String) {
    SPHelper(mContext).save(KEY_WA_SAUDI, waNumber)
}

fun Context.getWASaudi(): String? {
    return SPHelper(this).getString(KEY_WA_SAUDI, "")
}


fun saveWAInternational(mContext: Context,waNumber: String) {
    SPHelper(mContext).save(KEY_WA_INTERNATIONAL, waNumber)
}

fun Context.getWAInternational(): String? {
    return SPHelper(this).getString(KEY_WA_INTERNATIONAL, "")
}


fun saveWAHome(mContext: Context,waNumber: String) {
    SPHelper(mContext).save(KEY_WA_HOME, waNumber)
}

fun Context.getWAHome(): String? {
    return SPHelper(this).getString(KEY_WA_HOME, "")
}


// Resend sms priority
fun saveSendPriority(mContext: Context,waNumber: String) {
    SPHelper(mContext).save(KEY_RESEND_PRIORITY, waNumber)
}

fun Context.getSendPriority(): String? {
    return SPHelper(this).getString(KEY_RESEND_PRIORITY, "")
}


/**
 * ToDo: Product Id Type
 */
fun saveProductIdType(context: Context,productType: ProductType) {
    val jsonString = Gson().toJson(productType)
    SPHelper(context).save(KEY_PRODUCT_ID_TYPE, jsonString)
}

fun Context.getProductIdType(): ProductType? {
    val jsonString = SPHelper(this).getString(KEY_PRODUCT_ID_TYPE, "")
    return if (jsonString.isNullOrEmpty() || jsonString.isNullOrBlank()) {
        null
    } else {
        val itemType = object : TypeToken<ProductType>() {}.type
        Gson().fromJson<ProductType>(jsonString, itemType)
    }

}

/**
 * ToDo: Model List
 */


fun Context.saveModelDetailsData(categories: List<model_details_data>) {
    val jsonString = Gson().toJson(categories)
    SPHelper(this).save(KEY_MODEL_DETAILS, jsonString)
}

fun Context.getModelDetailsData(): List<model_details_data> {
    val categories = SPHelper(this).getString(KEY_MODEL_DETAILS, "")
    return if (categories.isNullOrEmpty() || categories.isNullOrBlank()) {
        emptyList()
    } else {
        val itemType = object : TypeToken<List<model_details_data>>() {}.type
        Gson().fromJson(categories, itemType)
    }
}


/**
 * ToDo: Model Data
 */
fun Context.saveModelData(modelData: model_details_data) {
    val jsonString = Gson().toJson(modelData)
    SPHelper(this).save(KEY_MODEL_DATA, jsonString)
}

fun Context.getModelData(): model_details_data? {
    val jsonString = SPHelper(this).getString(KEY_MODEL_DATA, "")
    return if (jsonString.isNullOrEmpty() || jsonString.isNullOrBlank()) {
        null
    } else {
        val itemType = object : TypeToken<model_details_data>() {}.type
        Gson().fromJson<model_details_data>(jsonString, itemType)
    }

}


/**
 * ToDo: Configuration Data
 */
fun saveConfiguration(mContext: Context,configurationData: setting_response_data) {
    val jsonString = Gson().toJson(configurationData)
    SPHelper(mContext).save(KEY_CONFIGURATIONS, jsonString)
}

fun Context.getConfiguration(): setting_response_data? {
    val jsonString = SPHelper(this).getString(KEY_CONFIGURATIONS, "")
    return if (jsonString.isNullOrEmpty() || jsonString.isNullOrBlank()) {
        null
    } else {
        val itemType = object : TypeToken<setting_response_data>() {}.type
        Gson().fromJson<setting_response_data>(jsonString, itemType)
    }

}

/**
 * ToDo: Model List
 */


fun saveCancelReasons(mContext: Context,cancel_reasons: List<CancelReason>) {
    val jsonString = Gson().toJson(cancel_reasons)
    SPHelper(mContext).save(KEY_CANCEL_REASONS, jsonString)
}

fun Context.getCancelReasons(): ArrayList<CancelReason>? {
    val categories = SPHelper(this).getString(KEY_CANCEL_REASONS, "")
    return if (categories.isNullOrEmpty() || categories.isNullOrBlank()) {
        null
    } else {
        val itemType = object : TypeToken<List<CancelReason>>() {}.type
        Gson().fromJson(categories, itemType)
    }
}


/**
 * ToDo: Product id
 */
fun Context.saveProductId(product_id: Int) {
    SPHelper(this).save(KEY_PRODUCT_ID, product_id)
}

fun Context.getProductId(): Int {
    return SPHelper(this).getInt(KEY_PRODUCT_ID, 0)
}

/**
 * ToDo: Case id
 */
fun Context.saveCaseId(product_id: Int) {
    SPHelper(this).save(KEY_CASE_ID, product_id)
}

fun Context.getCaseId(): Int {
    return SPHelper(this).getInt(KEY_CASE_ID, 0)
}


/**
 * ToDo: Offer Amount
 */
fun saveOfferAmount(mContext: Context,product_id: Int) {
    SPHelper(mContext).save(KEY_OFFER_AMOUNT, product_id)
}

fun Context.getOfferAmount(): Int {
    return SPHelper(this).getInt(KEY_OFFER_AMOUNT, 0)
}
