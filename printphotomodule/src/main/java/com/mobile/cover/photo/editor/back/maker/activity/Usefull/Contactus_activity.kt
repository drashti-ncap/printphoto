package com.mobile.cover.photo.editor.back.maker.activity.Usefull

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mobile.cover.photo.editor.back.maker.Commen.Share
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.checkout.PlaceOrderActivity
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.getConfiguration
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.sendWhatsappDirectMessage
import kotlinx.android.synthetic.main.activity_contactus.*

class Contactus_activity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactus)
        findviews()
        initlistener()

        if (SharedPrefs.getString(this, SharedPrefs.country_code) === "SA") {
            iv_help_wa.visibility = View.GONE
        }
    }

    private fun findviews() {

        val configurationData =getConfiguration()

        if (configurationData!=null){
            tv_timing_details.text = configurationData.contactSupportTiming
            tv_address_details.text = configurationData.contactAddress
            tv_company_mobile_number.text = configurationData.contactMobile
            tv_email_id.text = configurationData.contactEmail
        }



        id_help.setOnClickListener {
            val intent = Intent(this@Contactus_activity, VideoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initlistener() {
        ll_email.setOnClickListener(this)
        ll_call.setOnClickListener(this)
        ll_loc.setOnClickListener(this)
        id_back.setOnClickListener(this)
        iv_help_wa.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v === ll_email) {
            sendmail()
        }
        if (v === ll_call) {
            val configurationData =getConfiguration()
            whatsapp(configurationData!!.contactMobile)
        }
        if (v === ll_loc) {
            if (SharedPrefs.getString(this@Contactus_activity, SharedPrefs.country_code).equals("IN", ignoreCase = true)) {
                Geolocation()
            }
        }
        if (v === iv_help_wa) {
            sendWhatsappDirectMessage()
        }

        if (v === id_back) {
            finish()
        }
    }

    fun Geolocation() {
        val latitude = "21.234206"
        val longitude = "72.8095987"
        val geoUri = "https://www.google.com/maps/place/Printphoto.in+-+Customize+Mobile+Cover,+T-Shirt,+Mug/@$latitude,$longitude,17z/data=!4m8!1m2!2m1!1s!3m4!1s0x3be04fbe03eb2c4f:0x1a3824b4f04fe35c!8m2!3d$latitude!4d$longitude"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
        startActivity(intent)
    }

    fun sendmail() {
        val configurationData =getConfiguration()
        val intent = Intent(Intent.ACTION_SEND)
        val recipients = arrayOf(configurationData!!.contactEmail)
        intent.putExtra(Intent.EXTRA_EMAIL, recipients)
        intent.type = "text/html"
        intent.setPackage("com.google.android.gm")
        startActivity(Intent.createChooser(intent, "Send mail"))
    }

    @SuppressLint("NewApi")
    fun whatsapp(phone: String) {
        //        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phone + "&text=" + "Provide your complain here");
        //
        //        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        //
        //        startActivity(sendIntent);

        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phone")
        startActivity(intent)
    }

    public override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }


}
