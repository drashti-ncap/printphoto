package com.mobile.cover.photo.editor.back.maker.activity.Usefull

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputFilter
import android.view.Display
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.OnColorSelectedListener
import com.flask.colorpicker.builder.ColorPickerClickListener
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.google.firebase.analytics.FirebaseAnalytics
import com.mobile.cover.photo.editor.back.maker.Commen.Share
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter.FontAdapter
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.DrawableSticker
import com.mobile.cover.photo.editor.back.maker.model.FontModel
import kotlinx.android.synthetic.main.activity_font.*

import java.util.ArrayList

class FontActivity : AppCompatActivity(), View.OnClickListener {
    internal var flag = false
    internal var face: Typeface? = null
    internal var colorFlag = false
    internal var sColor = Color.WHITE
    internal var iv_more_app: ImageView? = null
    internal var iv_blast: ImageView? = null
    internal var is_closed: Boolean? = true

    //  private Toolbar toolbar_top;
    private var fontAdapter: FontAdapter? = null
    private val list = ArrayList<FontModel>()
    private val font_array = arrayOf("1", "6", "ardina_script", "beyondwonderland", "C", "coventry_garden_nf", "font3", "font6", "font10", "font16", "font20", "g", "h", "h2", "h3", "h6", "h7", "h8", "h15", "h18", "h20", "m", "o", "saman", "variane")

    // AdView adView;
    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    private fun getDisplaySize() {
        val display = window.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        Share.screenWidth = size.x
        Share.screenHeight = size.y
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_font)
        getDisplaySize()
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this@FontActivity)
        et_text!!.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(20))


        id_back.setOnClickListener { finish() }


        val gridLayoutManager = GridLayoutManager(this@FontActivity, 3)
        rv_font!!.layoutManager = gridLayoutManager

        rv_font!!.layoutParams.height = Share.screenHeight - toolbar!!.height - ll_font_color!!.height

        et_text!!.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
            }
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER /*|| (actionId == EditorInfo.IME_ACTION_DONE)*/) {
                // Log.i(TAG,"Enter pressed");
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(et_text!!.windowToken, 0)


            }
            false
        }

        iv_color!!.setOnClickListener(this)

        iv_done!!.setOnClickListener(this)

        initView()

    }

    override fun onResume() {
        super.onResume()
        getDisplaySize()
    }


    override fun onClick(v: View) {

        if (v === iv_color) {


            ColorPickerDialogBuilder
                    .with(this@FontActivity)
                    .setTitle(getString(R.string.choose_color))
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .alphaSliderOnly()
                    .setOnColorSelectedListener { }
                    .setPositiveButton(getString(R.string.ok)) { dialog, selectedColor, allColors ->
                        colorFlag = true
                        et_text!!.setTextColor(selectedColor)
                        et_text!!.setHintTextColor(selectedColor)

                        sColor = selectedColor
                    }
                    .setNegativeButton(getString(R.string.cancel)) { dialog, which -> colorFlag = false }
                    .build()
                    .show()
            hideKeyBoard(v, this)
        } else if (v === iv_done) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(et_text!!.windowToken, 0)


            val str = et_text!!.text.toString()


            if (!str.trim { it <= ' ' }.equals("", ignoreCase = true)) {
                Share.FONT_FLAG = true
                Share.FONT_TEXT = et_text!!.text.toString()

                val b2 = createBitmapFromLayoutWithText(applicationContext, et_text!!.text.toString(), et_text!!.currentTextColor, 0, et_text!!.typeface)
                val d = BitmapDrawable(resources, b2)
                val sticker = DrawableSticker(d)
                // squreActivity.drawables_sticker.add(sticker);

                val face = Typeface.createFromAsset(assets, Share.FONT_EFFECT + ".ttf")


                Share.TEXT_DRAWABLE = sticker
                val isInForeGround = true
                finish()
                overridePendingTransition(R.anim.app_left_in, R.anim.app_right_out)


            } else {
                Toast.makeText(applicationContext, getString(R.string.text_null), Toast.LENGTH_LONG).show()
            }


        }

    }

    private fun initView() {

        for (i in font_array.indices) {
            val spinnerModel = FontModel()
            spinnerModel.font_name = font_array[i]
            list.add(spinnerModel)
        }


        fontAdapter = FontAdapter(this@FontActivity, list)
        rv_font!!.adapter = fontAdapter

        fontAdapter!!.eventListener = object : FontAdapter.EventListener {
            override fun onItemViewClicked(position: Int) {

                Share.FONT_EFFECT = font_array[position].toLowerCase()

                val face = Typeface.createFromAsset(this@FontActivity.assets, font_array[position].toLowerCase() + ".ttf")
                et_text!!.typeface = face


            }

            override fun onDeleteMember(position: Int) {

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.app_left_in, R.anim.app_right_out)
    }

    companion object {

        fun createBitmapFromLayoutWithText(context: Context, s: String, color: Int, i: Int, face: Typeface): Bitmap {
            val mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = mInflater.inflate(R.layout.row_bitmap, null)

            val tv = view.findViewById<TextView>(R.id.tv_custom_text1)
            tv.textSize = 50F
            var j = 0
            while (j < s.length) {
                if (s.length >= 40) {
                    if (j <= s.length - 40) {
                        if (j == 0) {
                            val m = s.substring(0, 40)
                            tv.text = m
                        } else {
                            tv.append("\n")
                            val l = s.substring(j, j + 40)
                            tv.append(l)
                        }
                    } else {
                        tv.append("\n")
                        val l = s.substring(j)
                        tv.append(l)
                    }
                } else {
                    tv.text = s
                }
                j += 40
            }
            tv.setTextColor(color)
            tv.typeface = face
            view.layoutParams = LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)

            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))

            view.layout(0, 0, view.measuredWidth, view.measuredHeight)

            val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
            val c = Canvas(bitmap)
            view.draw(c)
            return bitmap
        }

        fun hideKeyBoard(view: View, mActivity: Activity) {
            val imm = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
