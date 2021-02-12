package com.thejan.flickrimagesearch.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thejan.flickrimagesearch.R
import com.thejan.flickrimagesearch.helper.IMAGE_BASE_URL
import com.thejan.flickrimagesearch.helper.IMAGE_EXTENSION
import com.thejan.flickrimagesearch.helper.loadImagePicaso
import com.thejan.flickrimagesearch.model.Photo
import kotlinx.android.synthetic.main.layout_photo_item.view.*

class PhotosAdapter(private var list: ArrayList<Photo>) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    var listener: ClickItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_photo_item,
                parent,
                false
            ), list, listener!!
        )
    }

    fun setClick(listener: ClickItem) {
        this.listener = listener
    }

    fun addItems(list: List<Photo>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData(){
        this.list.clear()
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): Photo {
        return list.get(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        loadImagePicaso(
            IMAGE_BASE_URL + getItem(position).server + "/" + getItem(position).id + "_" + getItem(
                position
            ).secret + IMAGE_EXTENSION, holder.image
        )
    }

    class ViewHolder(
        itemView: View,
        list: List<Photo>,
        listener: ClickItem
    ) : RecyclerView.ViewHolder(itemView) {

        val image = itemView.ivPhoto

        init {
            itemView.setOnClickListener {
                listener.clickItem(adapterPosition)
            }
        }

    }

    interface ClickItem {
        fun clickItem(position: Int)
    }
}