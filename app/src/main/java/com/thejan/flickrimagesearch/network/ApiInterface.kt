package com.thejan.flickrimagesearch.network

import com.thejan.flickrimagesearch.model.Photos
import com.thejan.flickrimagesearch.model.PhotosResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {
    @GET("/services/rest")
    fun getPhotos(
        @Query("api_key") token: String,
        @Query("method") method: String,
        @Query("format") format: String,
        @Query("nojsoncallback") noJsonCallBack: Int,
        @Query("page") page: Int,
        @Query("text") text: String
    ): Observable<PhotosResponse>
}