package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.deeplinks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.cover.photo.editor.back.maker.R
import kotlinx.android.synthetic.main.activity_product.*


class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        handleIntent(intent)
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
            val productId = params[params.size - 1]
            val productIdText = "Product Id: $productId"
            tv_pid.text = productIdText
            //logProductEvent(productId)
        }
    }


}