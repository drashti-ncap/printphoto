package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.THUMB_TYPE
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.loadImage
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener
import com.mobile.cover.photo.editor.back.maker.model.mall_AllChild

class HomeMallAdapter(var context: Context, var data: List<mall_AllChild>, val onItemClickListener: OnItemClickListener?) : RecyclerView.Adapter<HomeMallAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_home, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        if (data[position].international_app_mall_image != null) {
            var imgPath = data[position].international_app_mall_image
            if (SharedPrefs.getString(context, SharedPrefs.country_code).equals("IN", ignoreCase = true)) {
                imgPath = data[position].app_mall_image
            }
            Log.i("New_thumb", imgPath)
            context.loadImage(imgPath!!, holder.ivThumb, holder.progressBar, THUMB_TYPE.LANDSCAPE)
        } else {
            holder.progressBar.visibility = View.GONE
            Glide.with(context).load(R.drawable.ic_thumb_logo).centerCrop().into(holder.ivThumb)
        }


        holder.itemView.setOnClickListener { view -> onItemClickListener!!.onItemClickLister(view, position) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivThumb: ImageView = view.findViewById(R.id.iv_thumb)
        var progressBar: ProgressBar = view.findViewById(R.id.progressBar)
    }
}