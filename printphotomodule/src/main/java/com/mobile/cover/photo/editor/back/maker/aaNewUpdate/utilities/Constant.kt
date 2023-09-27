package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities

const val fontPath = ""
const val TAG_HTTP_REQUEST = "DEBUG_HTTP"

fun httpRequest(api: String) {
    println("$TAG_HTTP_REQUEST REQUEST: $api")
}

fun httpResponse(api: String) {
    println("$TAG_HTTP_REQUEST RESPONSE: $api")
}

fun httpFailure(api: String) {
    println("$TAG_HTTP_REQUEST RESPONSE: $api")
}