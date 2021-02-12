package com.thejan.flickrimagesearch.model

import com.google.gson.annotations.SerializedName


class PhotosResponse {
    @SerializedName("photos") var photos: Photos? = null
    @SerializedName("stat") var stat: String? = null
}

class Photos {
    @SerializedName("page")
    var page: Int = 0
    @SerializedName("pages")
    var pages: Int = 0
    @SerializedName("perpage")
    var perpage: String? = null
    @SerializedName("total")
    var total: String? = null
    @SerializedName("photo")
    var photo: List<Photo>? = null
}


class Photo {
    @SerializedName("id")
    var id: String? = null
    @SerializedName("owner")
    var owner: String? = null
    @SerializedName("secret")
    var secret: String? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("server")
    var server: String? = null
}

