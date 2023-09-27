package com.mobile.cover.photo.editor.back.maker.activity.Un_Usefull_Old

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.mobile.cover.photo.editor.back.maker.Commen.Share
import com.mobile.cover.photo.editor.back.maker.Commen.Share.drawables_sticker
import com.mobile.cover.photo.editor.back.maker.Commen.Share.upload
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.FontActivity
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.LogInActivity
import com.mobile.cover.photo.editor.back.maker.activity.Usefull.StickerActivity
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.apiclient.MainApiClient
import com.mobile.cover.photo.editor.back.maker.constraint.RegReq
import com.mobile.cover.photo.editor.back.maker.customView.MaskableFrameLayout
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.DrawableSticker
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.Sticker
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.StickerView
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.StickerView.mStickers
import com.mobile.cover.photo.editor.back.maker.customView.StickerView.back_StickerView
import com.mobile.cover.photo.editor.back.maker.extra.ProgressRequestBody
import com.mobile.cover.photo.editor.back.maker.model.Cart
import kotlinx.android.synthetic.main.testing_activity_case_edit.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*

class CaseEditActivity2 : AppCompatActivity(), ProgressRequestBody.UploadCallbacks {
    internal var msk: Bitmap? = null
    internal var bground: Bitmap? = null
    internal var up_image: Bitmap? = null
    internal var width: Int = 0
    internal var height: Int = 0
    internal var bitmap: Bitmap? = null
    internal var selectedColor = Color.parseColor("#ffffff")
    internal var progress: ProgressDialog? = null
    internal var maskableFrameLayout: MaskableFrameLayout? = null
    private val PICK_IMAGE_REQUEST = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.testing_activity_case_edit)

        back_StickerView.mStickers.clear()
        mStickers.clear()


        iv_back.setOnClickListener { back_print_method() }
        iv_logo.setOnClickListener { front() }


    }

    fun back_print_method() {
        Log.e("int", "==>$selectedColor")
        Share.back_print = 1

        //        if (Share.bitmapHashMap != null) {
        msk = (resources.getDrawable(R.drawable.tshirt_front_mask) as BitmapDrawable).bitmap
        bground = (resources.getDrawable(R.drawable.tshirt_front_mask) as BitmapDrawable).bitmap
        up_image = (resources.getDrawable(R.drawable.tshirt_front_model) as BitmapDrawable).bitmap
        //        }

        /*  msk = BitmapFactory.decodeResource(getResources(), R.drawable.backgound);
        bground = BitmapFactory.decodeResource(getResources(), R.drawable.backgound);
        up_image = BitmapFactory.decodeResource(getResources(), R.drawable.up);*/


        setHeader()
        getDisplaySize()
        findViews()
        setDiemns()
        intView()
    }

    private fun front() {
        Log.e("int", "==>$selectedColor")

        Share.back_print = 0
        //        if (Share.bitmapHashMap != null) {
        msk = (resources.getDrawable(R.drawable.tshirt_front_mask) as BitmapDrawable).bitmap
        bground = (resources.getDrawable(R.drawable.tshirt_front_mask) as BitmapDrawable).bitmap
        up_image = (resources.getDrawable(R.drawable.tshirt_front_model) as BitmapDrawable).bitmap
        //        }

        /*  msk = BitmapFactory.decodeResource(getResources(), R.drawable.backgound);
        bground = BitmapFactory.decodeResource(getResources(), R.drawable.backgound);
        up_image = BitmapFactory.decodeResource(getResources(), R.drawable.up);*/


        setHeader()
        getDisplaySize()
        findViews()
        setDiemns()
        intView()
    }

    fun addtocart(v: View) {


        bground = changeBitmapColor(bground, selectedColor)
        background.setImageBitmap(bground)
        if (Share.back_print == 1) {
            back_stickerView.setBackgroundColor(selectedColor)
        } else {
            stickerView.setBackgroundColor(selectedColor)
        }
        if (!SharedPrefs.getBoolean(this, Share.key_reg_suc)) {
            val alertDialog = AlertDialog.Builder(this@CaseEditActivity2)
            alertDialog.setTitle(getString(R.string.log_in))
            alertDialog.setCancelable(false)
            alertDialog.setMessage(getString(R.string.need_login))
            alertDialog.setPositiveButton(getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
                val intent = Intent(this@CaseEditActivity2, LogInActivity::class.java)
                startActivity(intent)
            }
            alertDialog.setNegativeButton(getString(R.string.cancel)) { dialog, which -> dialog.dismiss() }

            alertDialog.create().show()

            return
        }
        val alertDialog = AlertDialog.Builder(this@CaseEditActivity2)
        alertDialog.setCancelable(false)
        alertDialog.setMessage("Are you sure ?")
        alertDialog.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            dialog.dismiss()
            sendData()
        }
        alertDialog.setNegativeButton(getString(R.string.no)) { dialog, which -> dialog.dismiss() }

        alertDialog.create().show()


        //save_bitmap=loadBitmapFromView(savelayout);
        /*  Intent intent = new Intent(CaseEditActivity.this, Main2Activity.class);
        startActivity(intent);*/
    }

    private fun sendData() {
        id_add_photo.visibility = View.GONE
        if (Share.back_print == 1) {
            back_stickerView.setControlItemsHidden()
        } else {
            stickerView.setControlItemsHidden()
        }

        savelayout.isDrawingCacheEnabled = true
        savelayout.buildDrawingCache()

        /*    Bitmap bitmap = stickerView.createBitmap();
        final BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inTargetDensity = 1;
        bitmap.setDensity(Bitmap.DENSITY_NONE);

        //int fromHere = (int) (save_bitmap.getHeight() * 0.2);

        ImageView imageView = new ImageView(this);
        Bitmap croppedBitmap = Bitmap.createBitmap(bitmap, 0, 13, bitmap.getWidth(), bitmap.getHeight() - 26);
        Bitmap bitmap1 = Bitmap.createBitmap(croppedBitmap.getWidth(), croppedBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap1);

        canvas.scale(-1, 1, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        canvas.drawBitmap(croppedBitmap, 0, 0, new Paint());
*/


        try {
            /*Intent intent=new Intent(CaseEditActivity.this,Main2Activity.class);
            startActivity(intent);*/
            crateReq().execute()

        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("EXP", "sendData: " + e.message)
        }

    }

    fun getFile(filename: String, yourbitmap: Bitmap?, formate: String): File {
        val f = File(cacheDir, filename + formate)
        try {
            f.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        if (formate.contains("jpg")) {
            yourbitmap!!.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos)

        } else {
            yourbitmap!!.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos)

        }
        val bitmapdata = bos.toByteArray()

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(f)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return f
    }

    fun RotateBitmap(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    internal fun CropBitmapTransparency(sourceBitmap: Bitmap): Bitmap? {
        var minX = sourceBitmap.width
        var minY = sourceBitmap.height
        var maxX = -1
        var maxY = -1
        for (y in 0 until sourceBitmap.height) {
            for (x in 0 until sourceBitmap.width) {
                val alpha = sourceBitmap.getPixel(x, y) shr 24 and 255
                if (alpha > 0)
                // pixel is not 100% transparent
                {
                    if (x < minX)
                        minX = x
                    if (x > maxX)
                        maxX = x
                    if (y < minY)
                        minY = y
                    if (y > maxY)
                        maxY = y
                }
            }
        }
        return if (maxX < minX || maxY < minY) null else Bitmap.createBitmap(sourceBitmap, minX, minY, maxX - minX + 1, maxY - minY + 1) // Bitmap is entirely transparent

        // crop bitmap to non-transparent area and return:
    }

    @Throws(IOException::class)
    private fun savebitmap(bm: Bitmap, name: String) {
        val file_path = Environment.getExternalStorageDirectory().absolutePath + "/Phonecase"
        val dir = File(file_path)
        if (!dir.exists())
            dir.mkdirs()
        val file = File(dir, "Phonecase$name.png")
        val fOut = FileOutputStream(file)

        bm.compress(Bitmap.CompressFormat.PNG, 100, fOut)
        fOut.flush()
        fOut.close()
    }

    fun getBitmapPrint(bm: Bitmap): Bitmap {
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inTargetDensity = 1
        bm.density = Bitmap.DENSITY_NONE

        val fromHere = (bm.height * 0.2).toInt()

        val imageView = ImageView(this)
        //  Bitmap croppedBitmap = Bitmap.createBitmap(bm, 0, 13, bm.getWidth(), bm.getHeight() - 26);
        val croppedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height)
        val bitmap = Bitmap.createBitmap(croppedBitmap.width, croppedBitmap.height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmap)

        canvas.scale(-1f, 1f, (bm.width / 2).toFloat(), (bm.height / 2).toFloat())
        canvas.drawBitmap(croppedBitmap, 0f, 0f, Paint(Paint.FILTER_BITMAP_FLAG))
        return bitmap
    }

    fun getBitmapPrint(bm: Bitmap, width: String, height: String): Bitmap {
        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inTargetDensity = 1
        bm.density = Bitmap.DENSITY_NONE

        val fromHere = (bm.height * 0.2).toInt()

        val imageView = ImageView(this)
        val croppedBitmap = Bitmap.createBitmap(bm, 0, 13, bm.width, bm.height - 26)
        var bitmap: Bitmap? = null
        if (intent.hasExtra("width")) {
            bitmap = Bitmap.createBitmap(Integer.parseInt(width), Integer.parseInt(height), Bitmap.Config.ARGB_8888)

        } else {
            bitmap = Bitmap.createBitmap(croppedBitmap.width, croppedBitmap.height, Bitmap.Config.ARGB_8888)

        }

        val scaleX = Integer.parseInt(width) / bitmap!!.width.toFloat()
        val scaleY = Integer.parseInt(height) / bitmap.height.toFloat()
        val pivotX = 0f
        val pivotY = 0f

        val scaleMatrix = Matrix()
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY)

        //   Canvas canvas = new Canvas(resizedBitmap);

        val canvas = Canvas(bitmap)
        canvas.matrix.set(scaleMatrix)
        canvas.scale(-1f, 1f, (bm.width / 2).toFloat(), (bm.height / 2).toFloat())
        // canvas.drawBitmap(croppedBitmap, 0, 0, new Paint());
        canvas.drawBitmap(croppedBitmap, 0f, 0f, Paint(Paint.FILTER_BITMAP_FLAG))
        //   canvas.drawBitmap(croppedBitmap, null, new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), null);
        return bitmap
    }

    fun getResizedBitmapCover(bm: Bitmap, newHeight: Int, newWidth: Int): Bitmap {
        val width = bm.width
        val height = bm.height
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // create a matrix for the manipulation
        val matrix = Matrix()
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight)
        // recreate the new Bitmap
        val resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
        return Bitmap.createScaledBitmap(bm, newWidth, newHeight, true)
    }

    fun combineImages(c: Bitmap, s: Bitmap): Bitmap { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom
        var cs: Bitmap? = null

        val width = c.width
        val height = s.height

        //        if(c.getWidth() > s.getWidth()) {
        //            width = c.getWidth() + s.getWidth();
        //            height = c.getHeight();
        //        } else {
        //            width = s.getWidth() + s.getWidth();
        //            height = c.getHeight();
        //        }

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val comboImage = Canvas(cs!!)

        comboImage.drawBitmap(c, 0f, 0f, null)
        comboImage.drawBitmap(s, 0f, 0f, null)

        // this is an extra bit I added, just incase you want to save the new image somewhere and then return the location
        /*String tmpImg = String.valueOf(System.currentTimeMillis()) + ".png";

    OutputStream os = null;
    try {
      os = new FileOutputStream(loc + tmpImg);
      cs.compress(CompressFormat.PNG, 100, os);
    } catch(IOException e) {
      Log.e("combineImages", "problem combining images", e);
    }*/

        return cs
    }

    private fun setDiemns() {

        //        width = (int) (Share.screenWidth * 0.65);
        //        height = (int) (Share.screenHeight * 0.6);
        if (Share.back_print == 1) {
            width = Share.screenWidth
            height = Share.screenHeight

            maskableFrameLayout?.layoutParams?.width = width
            maskableFrameLayout?.layoutParams?.height = height

            back_stickerView.layoutParams.width = width
            back_stickerView.layoutParams.height = height

            background.layoutParams.width = width
            background.layoutParams.height = height

            up.layoutParams.width = width
            up.layoutParams.height = height
            maskableFrameLayout?.setMask(BitmapDrawable(msk))
            background.setImageBitmap(bground)


            up.setImageBitmap(up_image)
        } else {
            width = Share.screenWidth
            height = Share.screenHeight

            maskableFrameLayout?.layoutParams?.width = width
            maskableFrameLayout?.layoutParams?.height = height

            stickerView.layoutParams.width = width
            stickerView.layoutParams.height = height

            background.layoutParams.width = width
            background.layoutParams.height = height

            up.layoutParams.width = width
            up.layoutParams.height = height
            maskableFrameLayout?.setMask(BitmapDrawable(msk))
            background.setImageBitmap(bground)


            up.setImageBitmap(up_image)
        }

        /* DrawableSticker drawableSticker = new DrawableSticker(getDrawable(R.drawable.ic_phone_cases));
        drawableSticker.setTag("main");
        stickerView.addSticker(drawableSticker);*/
    }

    private fun intView() {
        upload = false
        if (Share.back_print == 1) {
            back_stickerView.setOnStickerOperationListener(object : back_StickerView.OnStickerOperationListener {
                override fun onStickerClicked(sticker: Sticker) {}

                override fun onStickerDeleted(sticker: Sticker) {
                    if (mStickers.size == 0) {
                        id_add_photo.visibility = View.VISIBLE
                    }
                }

                override fun onStickerDragFinished(sticker: Sticker) {}

                override fun onStickerZoomFinished(sticker: Sticker) {}

                override fun onStickerAdd(sticker: Sticker) {
                    id_add_photo.visibility = View.GONE
                }

                override fun onStickerNull() {

                }

                override fun onStickerFlipped(sticker: Sticker) {}
            })

        } else {
            stickerView.setOnStickerOperationListener(object : StickerView.OnStickerOperationListener {
                override fun onStickerClicked(sticker: Sticker) {}

                override fun onStickerDeleted(sticker: Sticker) {
                    if (mStickers.size == 0) {
                        id_add_photo.visibility = View.VISIBLE
                    }
                }

                override fun onStickerDragFinished(sticker: Sticker) {}

                override fun onStickerZoomFinished(sticker: Sticker) {}

                override fun onStickerAdd(sticker: Sticker) {
                    id_add_photo.visibility = View.GONE
                }

                override fun onStickerNull() {

                }

                override fun onStickerFlipped(sticker: Sticker) {}
            })
        }
        id_add_photo.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }
    }

    fun openGallery(view: View) {
        val intent = Intent()
        // Show only images, no videos or anything else
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    fun pickColor(view: View) {
        ColorPickerDialogBuilder
                .with(this@CaseEditActivity2)
                .setTitle(getString(R.string.choose_color))
                .initialColor(selectedColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener { }
                .setPositiveButton(getString(R.string.ok)) { dialog, selectedColor, allColors ->
                    this@CaseEditActivity2.selectedColor = selectedColor
                    bground = changeBitmapColor(bground, selectedColor)
                    background.setImageBitmap(bground)
                    if (Share.back_print == 1) {
                        back_stickerView.setBackgroundColor(selectedColor)
                    } else {
                        stickerView.setBackgroundColor(selectedColor)
                    }
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog, which -> }
                .build()
                .show()
    }

    fun openFont(view: View) {
        val intent = Intent(this@CaseEditActivity2, FontActivity::class.java)
        startActivity(intent)
    }

    fun openSticker(view: View) {
        val intent = Intent(this@CaseEditActivity2, StickerActivity::class.java)
        startActivity(intent)
    }

    private fun changeBitmapColor(sourceBitmap: Bitmap?, color: Int): Bitmap {

        val resultBitmap = Bitmap.createBitmap(sourceBitmap!!, 0, 0,
                sourceBitmap.width - 1, sourceBitmap.height - 1)
        val p = Paint()
        val filter = LightingColorFilter(color, 1)
        p.colorFilter = filter


        val canvas = Canvas(resultBitmap)
        canvas.drawBitmap(resultBitmap, 0f, 0f, p)
        return resultBitmap
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            val uri = data.data

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                // Log.d(TAG, String.valueOf(bitmap));

                val drawableSticker = DrawableSticker(BitmapDrawable(bitmap))
                drawableSticker.tag = "maindraw"
                if (Share.back_print == 1) {
                    back_stickerView.addStickerMain(drawableSticker)
                } else {
                    stickerView.addStickerMain(drawableSticker)
                }


            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun findViews() {

        maskableFrameLayout = findViewById(R.id.maskable)
        if (Share.back_print == 1) {
            stickerView = findViewById(R.id.id_stickerview)
            back_stickerView = findViewById(R.id.id_back_stickerview)
            back_stickerView.visibility = View.VISIBLE
            stickerView.visibility = View.GONE
        } else {
            stickerView = findViewById(R.id.id_stickerview)
            back_stickerView = findViewById(R.id.id_back_stickerview)
            back_stickerView.visibility = View.GONE
            stickerView.visibility = View.VISIBLE
        }


    }

    override fun onResume() {
        super.onResume()
        if (Share.FONT_FLAG) {
            Share.FONT_FLAG = false


            val drawableSticker = Share.TEXT_DRAWABLE
            drawableSticker.tag = "text"
            if (Share.back_print == 1) {
                back_stickerView.addSticker(drawableSticker)
            } else {
                stickerView.addSticker(drawableSticker)
            }

            drawables_sticker.add(drawableSticker)

        }


        if (Share.imageuri.equals("", ignoreCase = true)) {

        } else {
            Glide.with(this@CaseEditActivity2).asBitmap().load(Share.imageuri).into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    val drawableSticker = DrawableSticker(BitmapDrawable(resource))
                    drawableSticker.tag = "cartoon"
                    if (Share.back_print == 1) {
                        back_stickerView.addSticker(drawableSticker)
                    } else {
                        stickerView.addSticker(drawableSticker)
                    }
                    Share.imageuri = ""
                }
            })
        }
    }

    private fun setHeader() {
        val imageView = findViewById<ImageView>(R.id.id_back)
        imageView.setOnClickListener { onBackPressed() }
    }

    private fun getDisplaySize() {
        val display = window.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        Share.screenWidth = size.x
        Share.screenHeight = size.y
    }


    override fun onProgressUpdate(percentage: Int) {
        progress!!.progress = percentage
    }

    override fun onError() {

    }

    override fun onFinish() {
        progress!!.progress = 100

    }

    private inner class crateReq : AsyncTask<Void, Void, Void>() {

        internal var builder: MultipartBody.Builder? = null

        override fun onPreExecute() {
            super.onPreExecute()
            progress = ProgressDialog(this@CaseEditActivity2)
            progress!!.setMessage("Uploading Data...")
            progress!!.show()

            builder = MultipartBody.Builder()
        }

        override fun onPostExecute(aVoid: Void) {
            super.onPostExecute(aVoid)
            if (builder != null) {
                val multipartBody = builder!!.build()
                val apiService = MainApiClient(this@CaseEditActivity2).apiInterface
                val cartCall = apiService.sendCart(multipartBody)
                cartCall!!.enqueue(object : Callback<Cart?> {
                    override fun onResponse(call: Call<Cart?>, response: Response<Cart?>) {

                        if (progress != null && progress!!.isShowing)
                            progress!!.dismiss()

                        if (response != null) {
                            if (response.body()!!.responseCode.equals("1", ignoreCase = true)) {
                                upload = true
                                mStickers.clear()
                                finish()
                                overridePendingTransition(R.anim.app_right_in, R.anim.app_left_out)
                            } else {
                                val alertDialog = AlertDialog.Builder(this@CaseEditActivity2)
                                alertDialog.setTitle("Upload Faild")
                                alertDialog.setCancelable(false)
                                alertDialog.setMessage("Something went to wrong. Please try again Later")
                                alertDialog.setPositiveButton(getString(R.string.ok)) { dialog, which -> dialog.dismiss() }

                                alertDialog.create().show()
                            }
                        }
                        Log.d("response", "==>$response")
                    }

                    override fun onFailure(call: Call<Cart?>, t: Throwable) {
                        Log.d("response", "Faied==>$t")
                        if (progress != null && progress!!.isShowing)
                            progress!!.dismiss()

                        if (t.toString().contains("connect timed out") || t.toString().contains("timeout")) {
                            val alertDialog = AlertDialog.Builder(this@CaseEditActivity2).create()
                            alertDialog.setTitle(getString(R.string.time_out))
                            alertDialog.setMessage(getString(R.string.connect_time_out))
                            alertDialog.setButton(getString(R.string.retry)) { dialog, which ->
                                dialog.dismiss()
                                sendData()
                            }
                            alertDialog.show()
                        } else {
                            val alertDialog = AlertDialog.Builder(this@CaseEditActivity2).create()
                            alertDialog.setTitle(getString(R.string.internet_connection))
                            alertDialog.setMessage(getString(R.string.slow_connect))
                            alertDialog.setButton(getString(R.string.retry)) { dialog, which ->
                                dialog.dismiss()
                                sendData()
                            }
                            alertDialog.show()
                        }
                    }
                })
            }

        }

        override fun doInBackground(vararg voids: Void): Void? {
            // Log.e("width","==>if"+getIntent().getIntExtra("width",0));
            if (intent.hasExtra("width")) {
                print_bitmap = getBitmapPrint(stickerView.createBitmap(intent.getStringExtra("width"), intent.getStringExtra("height")))
                Log.e("width", "==>if")


            } else {
                print_bitmap = getBitmapPrint(stickerView.createBitmap())
            }
            Log.e("width", "==>" + print_bitmap!!.width)
            Log.e("height", "==>" + print_bitmap!!.height)
            cover_bitmap = RotateBitmap(getResizedBitmapCover(CropBitmapTransparency(savelayout.drawingCache)!!, height, width), 270f)
            //  cover_bitmap = savelayout.getDrawingCache();
            savelayout.isAlwaysDrawnWithCacheEnabled = false
            val file_printphoto = getFile("printphoto_" + System.currentTimeMillis(), print_bitmap, ".jpg")
            val file_cover_bitmap = getFile("cover_bitmap_" + System.currentTimeMillis(), cover_bitmap, ".png")
            Log.d("file_printphoto", "Size==>" + file_printphoto.length() / 1024)
            Log.d("file_cover_bitmap", "Size==>" + file_cover_bitmap.length() / 1024)
            //            ProgressRequestBody reqFile_printphoto = new ProgressRequestBody(file_printphoto, this);
            //            ProgressRequestBody reqFile_cover_bitmap = new ProgressRequestBody(file_cover_bitmap, this);

            // MultipartBody.Part filePart_file_printphoto = MultipartBody.Part.createFormData("file[]", file_printphoto.getName(), reqFile_printphoto);
            //    MultipartBody.Part filePart_file_cover_bitmap = MultipartBody.Part.createFormData("file[]", file_cover_bitmap.getName(), reqFile_cover_bitmap);


            builder!!.setType(MultipartBody.FORM)
            builder!!.addFormDataPart("model_name", intent.getStringExtra("model_name")!!)
            builder!!.addFormDataPart("user_id", SharedPrefs.getString(this@CaseEditActivity2, Share.key_ + RegReq.id))
            builder!!.addFormDataPart("quantity", "1")
            builder!!.addFormDataPart("model_id", intent.getStringExtra("model_id")!!)
            builder!!.addFormDataPart("total_amount", intent.getStringExtra("total_amount")!!)
            builder!!.addFormDataPart("discount", "0")
            builder!!.addFormDataPart("paid_amount", intent.getStringExtra("paid_amount")!!)
            val requestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file_printphoto)
            val requestBody1 = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file_cover_bitmap)
            builder!!.addFormDataPart("print_image", file_printphoto.name, requestBody)
            builder!!.addFormDataPart("case_image", file_cover_bitmap.name, requestBody1)
            builder!!.addFormDataPart("ln", Locale.getDefault().language)


            //  Call<Cart> cartCall = apiService.sendCarttoadd(filePart_file_printphoto, filePart_file_cover_bitmap, getIntent().getStringExtra("model_name"), getIntent().getStringExtra("user_id"), getIntent().getStringExtra("quantity"), getIntent().getStringExtra("total_amount"), getIntent().getStringExtra("discount"), getIntent().getStringExtra("paid_amount"), getIntent().getStringExtra("shipping"));


            return null
        }
    }

    companion object {

        lateinit var stickerView: StickerView
        lateinit var back_stickerView: back_StickerView
        var print_bitmap: Bitmap? = null
        var cover_bitmap: Bitmap? = null

        fun loadBitmapFromView(v: RelativeLayout): Bitmap {
            val b = Bitmap.createBitmap(v.layoutParams.width, v.layoutParams.height, Bitmap.Config.ARGB_8888)
            val c = Canvas(b)
            v.layout(v.left, v.top, v.right, v.bottom)
            v.draw(c)
            return b
        }
    }
}
