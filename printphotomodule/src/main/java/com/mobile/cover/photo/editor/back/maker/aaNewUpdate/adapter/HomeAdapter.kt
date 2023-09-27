package com.mobile.cover.photo.editor.back.maker.aaNewUpdate.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.mobile.cover.photo.editor.back.maker.Commen.SharedPrefs
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.THUMB_TYPE
import com.mobile.cover.photo.editor.back.maker.aaNewUpdate.utilities.loadImage
import com.mobile.cover.photo.editor.back.maker.interfacce.OnItemClickListener
import com.mobile.cover.photo.editor.back.maker.model.AllChild

class HomeAdapter(var context: Context, var data: List<AllChild>,val onItemClickListener: OnItemClickListener?) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_home, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



        var imgPath = data[position].internation_app_image
        if (SharedPrefs.getString(context, SharedPrefs.country_code).equals("IN", ignoreCase = true)) {
            imgPath = data[position].appImage
        }
            Log.e("DATA","PATH"+imgPath)
        Log.i("TAGG",imgPath)
        context.loadImage(imgPath!!, holder.ivThumb, holder.progressBar,THUMB_TYPE.LANDSCAPE)
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