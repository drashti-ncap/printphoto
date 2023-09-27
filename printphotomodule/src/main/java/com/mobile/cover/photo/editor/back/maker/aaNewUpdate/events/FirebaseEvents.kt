package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.events

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.other.final_status_response_data
import com.mobile.cover.photo.editor.back.maker.model.CartItem

const val ORDER_ID = "order_id"

fun Context.logAppOpenEvent() {
    val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    val params = Bundle()
    params.putString(FirebaseAnalytics.Event.APP_OPEN, FirebaseAnalytics.Event.APP_OPEN)
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, params)
}

fun Context.logAddToCartEvent(itemID: String, itemName: String, amount: Double) {
    val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    val params = Bundle()
    // Currency type
    params.putString(FirebaseAnalytics.Param.CURRENCY, CURRENCY_TYPE)
    // Item
    val item1 = Bundle()
    item1.putString(FirebaseAnalytics.Param.ITEM_ID, itemID)
    item1.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName)
    params.putParcelableArray(FirebaseAnalytics.Param.ITEMS, arrayOf(item1))
    // Total Amount
    params.putDouble(FirebaseAnalytics.Param.VALUE, amount)
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.ADD_TO_CART, params)
}

fun Context.logViewCartEvent(cartItems: MutableList<CartItem>, amount: Double) {
    val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    val params = Bundle()
    // Currency type
    params.putString(FirebaseAnalytics.Param.CURRENCY, CURRENCY_TYPE)
    // Items
    val items: MutableList<Bundle> = ArrayList()
    for (item in cartItems) {
        val item1 = Bundle()
        item1.putString(FirebaseAnalytics.Param.ITEM_ID, item.id.toString())
        item1.putString(FirebaseAnalytics.Param.ITEM_NAME, item.modelName)
        items.add(item1)
    }
    params.putParcelableArray(FirebaseAnalytics.Param.ITEMS, items.toTypedArray())
    // Total Amount
    params.putDouble(FirebaseAnalytics.Param.VALUE, amount)
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_CART, params)
}

fun Context.logPurchasedEvent(data: final_status_response_data) {
    val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    val params = Bundle()
    params.putString(FirebaseAnalytics.Param.CURRENCY, CURRENCY_TYPE)
    params.putString(ORDER_ID, data.orderId)
    params.putString(FirebaseAnalytics.Param.TRANSACTION_ID, data.transactionId)
    params.putDouble(FirebaseAnalytics.Param.VALUE, data.paid_amount)
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.PURCHASE, params)
}

