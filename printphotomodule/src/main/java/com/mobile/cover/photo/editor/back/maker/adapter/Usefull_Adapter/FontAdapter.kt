package com.mobile.cover.photo.editor.back.maker.adapter.Usefull_Adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.mobile.cover.photo.editor.back.maker.Commen.Share
import com.mobile.cover.photo.editor.back.maker.R
import com.mobile.cover.photo.editor.back.maker.model.FontModel

import java.util.ArrayList

class FontAdapter(private val context: Context, list_model: ArrayList<FontModel>) : RecyclerView.Adapter<FontAdapter.MyViewHolder>() {

    private var list = ArrayList<FontModel>()
    var eventListener: EventListener? = null

    init {
        this.list = list_model
    }

    private fun onItemViewClicked(position: Int) {
        if (eventListener != null) {
            eventListener!!.onItemViewClicked(position)
        }
    }

    fun getItem(position: Int): FontModel {
        return list[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.font_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.font_name.setOnClickListener { onItemViewClicked(position) }
        Share.FONT_STYLE = list[position].font_name
        val height = (context.resources.displayMetrics.heightPixels * 0.09).toInt()
        holder.itemView.layoutParams.height = height
        val typeface = Typeface.createFromAsset(context.assets, Share.FONT_STYLE.toLowerCase() + ".ttf")
        holder.font_name.text = "Hello"
        holder.font_name.typeface = typeface
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface EventListener {
        fun onItemViewClicked(position: Int)

        fun onDeleteMember(position: Int)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var font_name: TextView

        init {

            font_name = itemView.findViewById(R.id.tv_font_name)

        }
    }


}
