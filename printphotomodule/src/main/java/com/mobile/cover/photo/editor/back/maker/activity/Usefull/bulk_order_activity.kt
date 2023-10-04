package com.mobile.cover.photo.editor.back.maker.activity.Usefull

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobile.cover.photo.editor.back.maker.Commen.Share
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs
import com.mobile.cover.photo.editor.back.maker.Pojoclasses.response.main_response_bulk
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.PrintPhotoBaseActivity
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq
import com.mobile.cover.photo.editor.back.maker.model.bulk_category_model
import com.mobile.cover.photo.editor.back.maker.model.new_main_model
import kotlinx.android.synthetic.main.activity_bulk_order_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class bulk_order_activity : PrintPhotoBaseActivity(), View.OnClickListener {
   // internal lateinit var pd: ProgressDialog
    internal var products: MutableList<String> = ArrayList()
    internal var price_range: MutableList<String> = ArrayList()
    internal var spinnercodeArray = ArrayList<String>()
    internal var country_code: Int? = null
    internal var code_position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bulk_order_activity)
        spinnercodeArray.clear()
        for (country in Share.country_mobile_code.indices) {
            spinnercodeArray.add("+" + Share.country_mobile_code[country].phonecode + " (" + Share.country_mobile_code[country].sortname + ")" + " " + Share.country_mobile_code[country].name)
        }
        findviews()
        initlistener()
    }

    private fun findviews() {
        id_ed_fname.setText(intent.getStringExtra("fname") + " " + intent.getStringExtra("lname"))
        id_ed_mobile_no.setText(intent.getStringExtra("mobile_1"))
        id_ed_email_id.setText(intent.getStringExtra("email"))
        if (Share.main_category_data.size != 0) {
            Share.bulk_category_list.clear()
            for (i in Share.main_category_data.indices) {
                if (i == 0) {
                    val bulk_category = bulk_category_model()
                    bulk_category.id = Share.main_category_data[i].id
                    bulk_category.name = Share.main_category_data[i].name
                    Share.bulk_category_list.add(bulk_category)
                } else {
                    if (Share.main_category_data[i].allChilds.size != 0) {
                        for (j in 0 until Share.main_category_data[i].allChilds.size) {
                            val bulk_category = bulk_category_model()
                            bulk_category.id = Share.main_category_data[i].allChilds[j].id
                            bulk_category.name = Share.main_category_data[i].allChilds[j].name
                            Share.bulk_category_list.add(bulk_category)
                        }
                    } else {
                        val bulk_category = bulk_category_model()
                        bulk_category.id = Share.main_category_data[i].id
                        bulk_category.name = Share.main_category_data[i].name
                        Share.bulk_category_list.add(bulk_category)
                    }
                }

            }
        } else {
            getMainData()
        }
        price_range.add(applicationContext.resources.getString(R.string.select_quantity))
        price_range.add("5 - 10")
        price_range.add("10 - 25")
        price_range.add("25 - 50")
        price_range.add("50 - 75")
        price_range.add("75 - 100")
        price_range.add("100 or above")
        sp_code.isEnabled = false

        val code_adapter = ArrayAdapter(applicationContext, R.layout.simple_spinner_print_item, spinnercodeArray)
        sp_code.adapter = code_adapter

        sp_code.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                code_position = position
                country_code = Integer.parseInt(sp_code.selectedItem.toString().replace("[^0-9]".toRegex(), ""))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        if (Share.bulk_category_list.size != 0) {
            products.clear()
            products.add(applicationContext.getString(R.string.select_product))
            for (i in Share.bulk_category_list.indices) {
                products.add(Share.bulk_category_list[i].name)
            }
        }

        val product_adapter = ArrayAdapter(applicationContext, R.layout.simple_spinner_print_item, products)
        sp_product.adapter = product_adapter
        val qty_adapter = ArrayAdapter(applicationContext, R.layout.simple_spinner_print_item, price_range)
        sp_quantity.adapter = qty_adapter

    }

    private fun getMainData() {


        //pd = ProgressDialog.show(this@bulk_order_activity, "", applicationContext.resources.getString(R.string.loading), true, false)
        showProgressDialog(this@bulk_order_activity)
        val mainApiClient = MainApiClient(this@bulk_order_activity)
        val apiService = MainApiClient(this@bulk_order_activity).apiInterface


        val call = apiService.get_new_MainCatagaries(SharedPrefs.getString(this@bulk_order_activity, SharedPrefs.country_code), SharedPrefs.getString(this@bulk_order_activity, Share.key_ + RegReq.id), Locale.getDefault().language)


        call!!.enqueue(object : Callback<new_main_model?> {
            override fun onResponse(call: Call<new_main_model?>, response: Response<new_main_model?>) {

                Log.e("SERVER_RESPONSE", "onResponse: " + response.code())
                Log.e("SERVER_RESPONSE", "onResponse: " + response.message())
                Log.e("SERVER_RESPONSE", "onResponse: " + response.errorBody()!!)
                if (response.code() == 200) {
                    //pd.dismiss()
                    hideProgressDialog()
                    val new_main_model = response.body()
                    if (new_main_model!!.responseCode.equals("1", ignoreCase = true)) {
                        Share.main_category_data.clear()
                        Share.main_category_data.addAll(new_main_model.allChilds)
                        Share.bulk_category_list.clear()
                        for (i in Share.main_category_data.indices) {
                            if (i == 0) {
                                val bulk_category = bulk_category_model()
                                bulk_category.id = Share.main_category_data[i].id
                                bulk_category.name = Share.main_category_data[i].name
                                Share.bulk_category_list.add(bulk_category)
                            } else {
                                if (Share.main_category_data[i].allChilds.size != 0) {
                                    for (j in 0 until Share.main_category_data[i].allChilds.size) {
                                        val bulk_category = bulk_category_model()
                                        bulk_category.id = Share.main_category_data[i].allChilds[j].id
                                        bulk_category.name = Share.main_category_data[i].allChilds[j].name
                                        Share.bulk_category_list.add(bulk_category)
                                    }
                                } else {
                                    val bulk_category = bulk_category_model()
                                    bulk_category.id = Share.main_category_data[i].id
                                    bulk_category.name = Share.main_category_data[i].name
                                    Share.bulk_category_list.add(bulk_category)
                                }
                            }

                        }
                    } else {
                        Toast.makeText(this@bulk_order_activity, new_main_model.responseMessage, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val alertDialog = AlertDialog.Builder(this@bulk_order_activity).create()
                    alertDialog.setTitle(applicationContext.resources.getString(R.string.server_error))
                    alertDialog.setCancelable(false)
                    alertDialog.setMessage(applicationContext.resources.getString(R.string.server_under_maintenance))
                    alertDialog.setButton(applicationContext.resources.getString(R.string.ok)) { dialog, which ->
                        dialog.dismiss()
                        //pd.dismiss()
                        hideProgressDialog()
                        finish()
                    }
                    alertDialog.show()
                }
            }

            override fun onFailure(call: Call<new_main_model?>, t: Throwable) {
                Log.e("TAG", t.toString())
                error_dialogs(t)
            }
        })

    }

    fun error_dialogs(t: Throwable) {
        if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
            val alertDialog = AlertDialog.Builder(this@bulk_order_activity).create()
            alertDialog.setTitle(applicationContext.resources.getString(R.string.time_out))
            alertDialog.setCancelable(false)
            alertDialog.setMessage(applicationContext.resources.getString(R.string.connect_time_out))
            alertDialog.setButton(applicationContext.resources.getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
                //pd.dismiss()
                hideProgressDialog()
                getMainData()
            }
            alertDialog.show()
        } else if (t.toString().contains("Handshake failed") || t.toString().contains("Failed to connect to printphoto")) {
            val alertDialog = AlertDialog.Builder(this@bulk_order_activity).create()
            alertDialog.setTitle(applicationContext.resources.getString(R.string.server_error))
            alertDialog.setCancelable(false)
            alertDialog.setMessage(applicationContext.resources.getString(R.string.server_under_maintenance))
            alertDialog.setButton(applicationContext.resources.getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
                //pd.dismiss()
                hideProgressDialog()
                finish()
            }
            alertDialog.show()
        } else {

            val alertDialog = AlertDialog.Builder(this@bulk_order_activity)
            alertDialog.setTitle(applicationContext.resources.getString(R.string.internet_connection))
            alertDialog.setCancelable(false)
            alertDialog.setMessage(applicationContext.resources.getString(R.string.slow_connect))
            alertDialog.setPositiveButton(applicationContext.resources.getString(R.string.retry)) { dialog, which ->
                dialog.dismiss()
                //pd.dismiss()
                hideProgressDialog()
                getMainData()
            }
            alertDialog.setNegativeButton(applicationContext.resources.getString(R.string.cancel)) { dialog, which ->
                dialog.dismiss()
                //pd.dismiss()
                hideProgressDialog()
                finish()
            }
            alertDialog.show()
        }
    }


    private fun initlistener() {
        id_reset.setOnClickListener(this)
        id_back.setOnClickListener(this)
        id_ll_submit.setOnClickListener(this)
        for (i in Share.bulk_category_list.indices) {
            Log.e("BULK", "findviews: " + Share.bulk_category_list[i].name)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.id_ll_submit -> if (SharedPrefs.getString(this@bulk_order_activity, SharedPrefs.country_code).equals("IN", ignoreCase = true)) {
                if (id_ed_fname.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                    id_ed_fname.error = getString(R.string.please_enter_name)
                    id_ed_fname.requestFocus()
                } else if (id_ed_email_id.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                    id_ed_email_id.error = getString(R.string.please_enter_email)
                    id_ed_email_id.requestFocus()
                } else if (!id_ed_email_id.text.toString().trim { it <= ' ' }.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex())) {
                    id_ed_email_id.error = getString(R.string.valid_email)
                    id_ed_email_id.requestFocus()
                } else if (id_ed_mobile_no.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                    id_ed_mobile_no.error = getString(R.string.enter_mobile)
                    id_ed_mobile_no.requestFocus()
                } else if (id_ed_mobile_no.text.toString().trim { it <= ' ' }.length < 10) {
                    id_ed_mobile_no.error = getString(R.string.valid_mobile)
                    id_ed_mobile_no.requestFocus()
                } else if (id_ed_business_name.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                    id_ed_business_name.error = getString(R.string.valid_business)
                    id_ed_business_name.requestFocus()
                } else if (ed_description.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true) || ed_description.text.toString().trim { it <= ' ' }.length < 50) {
                    ed_description.error = getString(R.string.min_fifty_character)
                    ed_description.requestFocus()
                } else if (sp_product.selectedItem.toString().trim { it <= ' ' }.equals("Select product", ignoreCase = true)) {
                    Toast.makeText(this, getString(R.string.select_any_prod), Toast.LENGTH_SHORT).show()
                } else if (sp_quantity.selectedItem.toString().trim { it <= ' ' }.equals("Select quantity", ignoreCase = true)) {
                    Toast.makeText(this, getString(R.string.please_select_qty), Toast.LENGTH_SHORT).show()
                } else {
                    if (sp_product.selectedItemPosition >= 0) {
                        bulk_order()
                    }
                }
            } else {
                if (id_ed_fname.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                    id_ed_fname.error = getString(R.string.please_enter_name)
                    id_ed_fname.requestFocus()
                } else if (id_ed_email_id.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                    id_ed_email_id.error = getString(R.string.please_enter_email)
                    id_ed_email_id.requestFocus()
                } else if (!id_ed_email_id.text.toString().trim { it <= ' ' }.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex())) {
                    id_ed_email_id.error = getString(R.string.valid_email)
                    id_ed_email_id.requestFocus()
                } else if (id_ed_mobile_no.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                    id_ed_mobile_no.error = getString(R.string.enter_mobile)
                    id_ed_mobile_no.requestFocus()
                } else if (id_ed_business_name.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true)) {
                    id_ed_business_name.error = getString(R.string.valid_business)
                    id_ed_business_name.requestFocus()
                } else if (ed_description.text.toString().trim { it <= ' ' }.equals("", ignoreCase = true) || ed_description.text.toString().trim { it <= ' ' }.length < 50) {
                    ed_description.error = getString(R.string.min_fifty_character)
                    ed_description.requestFocus()
                } else if (sp_product.selectedItem.toString().trim { it <= ' ' }.equals("Select product", ignoreCase = true)) {
                    Toast.makeText(this, getString(R.string.select_any_prod), Toast.LENGTH_SHORT).show()
                } else if (sp_quantity.selectedItem.toString().trim { it <= ' ' }.equals("Select quantity", ignoreCase = true)) {
                    Toast.makeText(this, getString(R.string.please_select_qty), Toast.LENGTH_SHORT).show()
                } else {
                    if (Locale.getDefault().language.equals("ar", true)) {
                        if (sp_product.selectedItem.toString().trim { it <= ' ' }.equals(applicationContext.getString(R.string.select_product), ignoreCase = true)) {
                            Toast.makeText(this, getString(R.string.select_any_prod), Toast.LENGTH_SHORT).show()
                        } else if (sp_quantity.selectedItem.toString().trim { it <= ' ' }.equals(applicationContext.resources.getString(R.string.select_quantity), ignoreCase = true)) {
                            Toast.makeText(this, getString(R.string.please_select_qty), Toast.LENGTH_SHORT).show()
                        } else {
                            bulk_order()
                        }
                    } else {
                        bulk_order()
                    }
                }
            }
            R.id.id_back -> finish()
            R.id.id_reset -> {
                id_ed_fname.setText("")
                id_ed_email_id.setText("")
                id_ed_mobile_no.setText("")
                id_ed_business_name.setText("")
                val state_adapter = ArrayAdapter(applicationContext, R.layout.simple_spinner_print_item, products)
                sp_product.adapter = state_adapter

                val qty_adapter = ArrayAdapter(applicationContext, R.layout.simple_spinner_print_item, price_range)
                sp_quantity.adapter = qty_adapter
            }
        }
    }

    private fun bulk_order() {

        //pd = ProgressDialog.show(this@bulk_order_activity, "", getString(R.string.loading), true, false)
        showProgressDialog(this@bulk_order_activity)

        val user_id: Int
        if (!SharedPrefs.getBoolean(this@bulk_order_activity, Share.key_reg_suc)) {
            user_id = 0
        } else {
            user_id = Integer.valueOf(SharedPrefs.getString(this@bulk_order_activity, Share.key_ + RegReq.id))
        }

        val apiService = MainApiClient(this@bulk_order_activity).apiInterface
        val call = apiService.bulk_order(user_id, id_ed_fname.text.toString(), id_ed_email_id.text.toString(), "" + country_code + "-" + id_ed_mobile_no.text.toString(),
                Share.bulk_category_list[sp_product.selectedItemPosition - 1].id, sp_quantity.selectedItem.toString(), ed_description.text.toString(), id_ed_business_name.text.toString(),
                Locale.getDefault().language)

        call!!.enqueue(object : Callback<main_response_bulk?> {
            val TAG = "test"

            override fun onResponse(call: Call<main_response_bulk?>, response: Response<main_response_bulk?>) {
                Log.e(TAG, "onResponse: " + response.isSuccessful)
                if (response.isSuccessful) {
                    //pd.dismiss()
                    hideProgressDialog()
                    if (response.body()!!.responseCode.equals("1", ignoreCase = true)) {
                        val alertDialog = AlertDialog.Builder(this@bulk_order_activity).create()
                        alertDialog.setCancelable(false)
                        alertDialog.setTitle(getString(R.string.alert))
                        alertDialog.setMessage(response.body()!!.responseMessage)
                        alertDialog.setButton(getString(R.string.ok)) { dialog, which ->
                            alertDialog.dismiss()
                            finish()
                        }
                        alertDialog.show()
                    } else {
                        Toast.makeText(this@bulk_order_activity, response.body()!!.responseMessage, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    //pd.dismiss()
                    hideProgressDialog()
                    Toast.makeText(this@bulk_order_activity, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<main_response_bulk?>, t: Throwable) {
                //pd.dismiss()
                hideProgressDialog()
                Log.e(TAG, "onFailure: ======>$t")
                Log.e(TAG, "onFailure: ======>" + t.message)
                Log.e(TAG, "onFailure: ======>" + t.localizedMessage)
                if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                    val alertDialog = AlertDialog.Builder(this@bulk_order_activity).create()
                    alertDialog.setTitle(getString(R.string.time_out))
                    alertDialog.setMessage(getString(R.string.connect_time_out))
                    alertDialog.setCancelable(false)
                    alertDialog.setButton(getString(R.string.retry)) { dialog, which ->
                        dialog.dismiss()
                        bulk_order()
                    }
                    alertDialog.show()
                } else {
                    val alertDialog = AlertDialog.Builder(this@bulk_order_activity).create()
                    alertDialog.setTitle(getString(R.string.internet_connection))
                    alertDialog.setMessage(getString(R.string.slow_connect))
                    alertDialog.setCancelable(false)
                    alertDialog.setButton(getString(R.string.retry)) { dialog, which ->
                        dialog.dismiss()
                        bulk_order()
                    }
                    alertDialog.show()
                }
            }
        })


    }

}

