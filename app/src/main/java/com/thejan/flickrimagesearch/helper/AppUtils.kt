package com.thejan.flickrimagesearch.helper

import android.widget.ImageView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.thejan.flickrimagesearch.R

fun loadImagePicaso(url: String, iv: ImageView) {
    Picasso.get().load(url).placeholder(R.drawable.ic_placeholder)
        .into(iv)
}