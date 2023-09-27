package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.mobile.cover.photo.editor.back.maker.R

enum class THUMB_TYPE { PORTRAIT, SQUARE, LANDSCAPE }

fun Context.loadImage(imgUrl: String, imageView: ImageView, progressBar: View?, thumbType: THUMB_TYPE) {

    var thumbnail = R.drawable.ic_thumb_logo
    if (thumbType == THUMB_TYPE.SQUARE) {
        thumbnail = R.drawable.ic_thumb_logo_square
    } else if (thumbType == THUMB_TYPE.PORTRAIT) {
        thumbnail = R.drawable.ic_thumb_logo_potrait
    }

    val thumbnailRequest = Glide.with(this).load(thumbnail)
    Glide.with(this)
            .load(imgUrl)
            .placeholder(thumbnail)
            .error(thumbnail)
            // .thumbnail(0.15f)
            .thumbnail(thumbnailRequest)
            .fitCenter()
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                    progressBar?.visibility = View.GONE
                    imageView.visibility = View.VISIBLE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    progressBar?.visibility = View.GONE
                    imageView.visibility = View.VISIBLE
                    return false
                }
            }).into(imageView)
}

fun Context.loadImageCenterCrop(imgUrl: String, imageView: ImageView, progressBar: View?, thumbType: THUMB_TYPE) {

    var thumbnail = R.drawable.ic_thumb_logo
    if (thumbType == THUMB_TYPE.SQUARE) {
        thumbnail = R.drawable.ic_thumb_logo_square
    } else if (thumbType == THUMB_TYPE.PORTRAIT) {
        thumbnail = R.drawable.ic_thumb_logo_potrait
    }

    val thumbnailRequest = Glide.with(this).load(thumbnail)
    Glide.with(this)
            .load(imgUrl)
            .placeholder(thumbnail)
            .error(thumbnail)
            // .thumbnail(0.15f)
            .thumbnail(thumbnailRequest)
            .centerCrop()
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                    progressBar?.visibility = View.GONE
                    imageView.visibility = View.VISIBLE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    progressBar?.visibility = View.GONE
                    imageView.visibility = View.VISIBLE
                    return false
                }
            }).into(imageView)
}


fun Context.loadImageCenterCrop(imgUrl: String, imageView: ImageView, progressBar: View?, thumbType: THUMB_TYPE, width: Int, height: Int) {

    var thumbnail = R.drawable.ic_thumb_logo
    if (thumbType == THUMB_TYPE.SQUARE) {
        thumbnail = R.drawable.ic_thumb_logo_square
    } else if (thumbType == THUMB_TYPE.PORTRAIT) {
        thumbnail = R.drawable.ic_thumb_logo_potrait
    }

    val thumbnailRequest = Glide.with(this).load(thumbnail)
    Glide.with(this)
            .load(imgUrl)
            .placeholder(thumbnail)
            .error(thumbnail)
            .apply(RequestOptions().override(width, height))
            .thumbnail(thumbnailRequest)
            .centerCrop()
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                    progressBar?.visibility = View.GONE
                    imageView.visibility = View.VISIBLE
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    progressBar?.visibility = View.GONE
                    imageView.visibility = View.VISIBLE
                    return false
                }
            }).into(imageView)
}

