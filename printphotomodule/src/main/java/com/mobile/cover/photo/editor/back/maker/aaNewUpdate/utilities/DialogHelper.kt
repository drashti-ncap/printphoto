package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.Window
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.model.CancelReason
import org.jetbrains.anko.toast


fun Context.errorDialog(message: String, positive: OnPositive?) {


    var title: String? = null
    var msg: String? = null
    var positiveText: String? = null
    var negativeText: String? = null
    var isPositive = true

    if (message.contains("connect timed out") || message.contains("timeout")) {
        title = getString(R.string.time_out)
        msg = getString(R.string.connect_time_out)
        positiveText = getString(R.string.retry)
        isPositive = true
    } else if (message.contains("Handshake failed") || message.contains("Failed to connect to printphoto") || message.contains(getString(R.string.server_error))) {
        title = getString(R.string.server_error)
        msg = getString(R.string.server_under_maintenance)
        positiveText = getString(R.string.retry)
        isPositive = false
    } else {
        title = getString(R.string.internet_connection)
        msg = getString(R.string.slow_connect)
        positiveText = getString(R.string.retry)
        negativeText = getString(R.string.cancel)
        isPositive = true
    }

    showAlert(title, msg, positiveText, negativeText, object : OnPositive {
        override fun onYes(isPositive: Boolean) {
            positive?.onYes(isPositive)
        }
    })


}


fun Context.showAlert(title: String?, msg: String?, positiveText: String?, negativeText: String?, positive: OnPositive?) {


    if (!(this as Activity).window.decorView.rootView.isShown) {
        Log.e("showAlert", "isFinishing")
        return
    }


    val dialog = AlertDialog.Builder(this)
    var alert: AlertDialog? = null

    dialog.setCancelable(false)
    if (title != null) {
        // Initialize a new foreground color span instance
        val foregroundColorSpan = ForegroundColorSpan(ContextCompat.getColor(this, R.color.print_colorPrimaryDark))
        val ssBuilder = SpannableStringBuilder(title)
        ssBuilder.setSpan(foregroundColorSpan, 0, title.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        dialog.setTitle(ssBuilder)
    }
    if (msg != null) {
        dialog.setMessage(msg)
    }
    if (positiveText != null) {
        dialog.setPositiveButton(positiveText) { _, _ ->
            alert?.dismiss()
            positive?.onYes(true)
        }
    }
    if (negativeText != null) {
        dialog.setNegativeButton(negativeText) { _, _ ->
            alert?.dismiss()
            positive?.onYes(false)
        }
    }

    alert = dialog.create()
    alert.show()


    val textView = alert.findViewById<TextView>(android.R.id.message)
    try {
        /*   textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
           val face = Typeface.createFromAsset(assets, fontPath)
           textView.typeface = face*/
    } catch (e: Exception) {
        Log.e("showAlert", e.toString())
    }


}


/*
     * ToDo..Custom layout popup dialog
     */
fun Context.offerDialog(total_charge: Double,isCod : Boolean, onPositive: OnPositive) {

    val dialog = Dialog(this)
    dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.layout_off)

    val offerAMount = getOfferAmount()

    val ivBanner = dialog.findViewById<ImageView>(R.id.iv_banner)
    val tvHindi = dialog.findViewById<TextView>(R.id.tv_hindi)
    val tvTamil = dialog.findViewById<TextView>(R.id.tv_tamil)
    val tvTelugu = dialog.findViewById<TextView>(R.id.tv_kannad)
    val tvOfferAmount = dialog.findViewById<TextView>(R.id.tv_offer_amount)
    val tvOffer = dialog.findViewById<TextView>(R.id.tv_offer)
    val tvCod = dialog.findViewById<TextView>(R.id.tv_cod)
    val cardOffer: CardView = dialog.findViewById(R.id.card_offer)
    val cardCOD: CardView = dialog.findViewById(R.id.card_cod)

    setHeightWidth(ivBanner, 1000, 565)
    setHeightWidth(tvOfferAmount, 1000, 186)

    val formattedCODTotalAmt = String.format("%.2f", total_charge)
    val formattedOfferTotalAmt = String.format("%.2f", (total_charge - offerAMount))

    val codCharge = "₹$formattedCODTotalAmt \nCash On Deliver"
    val offerCharge = "₹$formattedOfferTotalAmt \nMake payment"
    val offerLabel = "₹$offerAMount OFF"

    val hindiOffer = "कार्ड द्वारा भुगतान करें ₹$offerAMount छूट प्राप्त करें"
    val teleguOffer = "కార్డు ద్వారా చెల్లించండి ₹$offerAMount తగ్గింపు పొందండి"
    val tamilOffer = "அட்டை மூலம் பணம் செலுத்துங்கள் ₹$offerAMount தள்ளுபடி கிடைக்கும்"

    tvHindi.text = hindiOffer
    tvTamil.text = tamilOffer
    tvTelugu.text = teleguOffer

    tvOfferAmount.text = offerLabel
    tvCod.text = codCharge
    tvOffer.text = offerCharge

    cardCOD.isVisible = isCod

    cardOffer.setOnClickListener {
        onPositive.onYes(true)
        dialog.dismiss()
    }
    cardCOD.setOnClickListener {
        onPositive.onYes(false)
        dialog.dismiss()
    }
    dialog.show()


}


/*
     * ToDo..Custom layout popup dialog
     */
fun Context.cancelOrderConfirmation(onPositive: OnOrderCancel) {

    val dialog = Dialog(this)
    dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.row_spinner)

    val width = (resources.displayMetrics.widthPixels * 0.95).toInt()
    val height = (resources.displayMetrics.heightPixels * 0.90).toInt()

    dialog.window!!.setLayout(width, WRAP_CONTENT)

    var selectedId = -1
    val spinner: Spinner = dialog.findViewById(R.id.spinner1) as Spinner
    val description: EditText = dialog.findViewById(R.id.editText1) as EditText
    val cancel = dialog.findViewById(R.id.tv_cancel) as TextView
    val dismissDialog = dialog.findViewById(R.id.iv_dismiss_dialog) as ImageView

    var reasons = getCancelReasons()
    if (reasons == null || reasons.isEmpty()) {
        toast("Something went wrong")
        return
    } else {
        reasons.add(0, CancelReason(-1, getString(R.string.select_cancel_reason)))
    }


    /*  if (reasons == null) {
          reasons = ArrayList()
          reasons.add(CancelReason(-1, getString(R.string.select_cancel_reason)))
          reasons.add(CancelReason(1, "Product is out of stock"))
          reasons.add(CancelReason(2, "Delivery address pincode is not serviceable"))
          reasons.add(CancelReason(3, "Call Not Pick Up"))
          reasons.add(CancelReason(4, "Photo Setting Was Not Properly"))
          reasons.add(CancelReason(5, "Photo Quality Was Very Dull & Blur"))
          reasons.add(CancelReason(6, "Pincode Wrong & Landmark Not Available"))
          reasons.add(CancelReason(7, "Customer says cancel my order"))
          reasons.add(CancelReason(8, "Very High Price"))
          reasons.add(CancelReason(9, "Call Out Of Reach OR Switch Off"))
          reasons.add(CancelReason(11, "Customer placed wrong model order"))
          reasons.add(CancelReason(12, "Not recieve confirmation call"))
          reasons.add(CancelReason(10, "Other"))
      } else {
          reasons.add(0, CancelReason(-1, getString(R.string.select_cancel_reason)))
      }*/

    val adapter = ReasonAdapter(this, reasons)
    spinner.adapter = adapter
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            selectedId = reasons[p2].id
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {

        }

    }
    cancel.setOnClickListener {

        if (selectedId == -1) {
            Toast.makeText(this, getString(R.string.select_valid_reason), Toast.LENGTH_SHORT).show()
        } else {
            val desc = description.text.toString()
            onPositive.onYes(CancelReason(selectedId, desc))
            dialog.dismiss()
        }

    }

    dismissDialog.setOnClickListener {
        dialog.dismiss()

    }
    dialog.show()

}


class ReasonAdapter(var context: Context, val reasons: List<CancelReason>) : BaseAdapter() {
    private var mInflater: LayoutInflater? = null
    override fun getCount(): Int {
        return reasons.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        var v = convertView
        if (v == null) {
            mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = mInflater!!.inflate(R.layout.row_textview, null)
            holder = ViewHolder(v)

            v.tag = holder
        } else {
            holder = v.tag as ViewHolder
        }
        holder.text.text = reasons[position].reason
        return v!!
    }

    internal class ViewHolder(v: View) {
        var text: TextView = v.findViewById<View>(R.id.textView1) as TextView
    }
}


interface OnOrderCancel {
    fun onYes(reason: CancelReason)
}

interface OnPositive {
    fun onYes(isPositive: Boolean)
}