package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events

import android.content.Context
import android.os.Bundle
import com.facebook.appevents.AppEventsConstants
import com.facebook.appevents.AppEventsLogger
import java.util.*


const val SEARCH_BRAND_NAME = "Phone Brand"
const val SEARCH_BRAND_MODEL = "Brand Model"
const val SEARCH_SHOPPING_MALL = "Shopping Mall"
const val VIEW_MALL = "View Mall"
const val CURRENCY_TYPE = "INR"

fun Context.logSearchedEvent(contentType: String, searchString: String, success: Boolean) {
    val logger = AppEventsLogger.newLogger(this)
    val params = Bundle()
    params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, contentType)
    params.putString(AppEventsConstants.EVENT_PARAM_SEARCH_STRING, searchString)
    params.putInt(AppEventsConstants.EVENT_PARAM_SUCCESS, if (success) 1 else 0)
    logger.logEvent(AppEventsConstants.EVENT_NAME_SEARCHED, params)
}

fun Context.logViewedContentEvent(contentType: String, contentId: String, price: String) {
    val logger = AppEventsLogger.newLogger(this)
    val params = Bundle()
    params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, contentType)
    params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId)
    params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, CURRENCY_TYPE)
    logger.logEvent(AppEventsConstants.EVENT_NAME_VIEWED_CONTENT, price.toDouble(), params)
}

fun Context.logAddedToCartEvent(contentId: String?, contentType: String?, price: String) {
    val logger = AppEventsLogger.newLogger(this)
    val params = Bundle()
    params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, contentId)
    params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, contentType)
    params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, CURRENCY_TYPE)
    logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_CART, price.toDouble(), params)
}

fun Context.logPurchasedEvent(orderId: String?, price: String) {
    val logger = AppEventsLogger.newLogger(this)
    val params = Bundle()
    params.putString(AppEventsConstants.EVENT_PARAM_ORDER_ID, orderId)
    params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, CURRENCY_TYPE)
    logger.logPurchase(price.toBigDecimal(), Currency.getInstance(CURRENCY_TYPE), params)
}