package com.thejan.flickrimagesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import com.thejan.flickrimagesearch.base.BaseViewModel
import com.thejan.flickrimagesearch.base.ViewModelState
import com.thejan.flickrimagesearch.helper.FORMAT
import com.thejan.flickrimagesearch.helper.METHOD_RECENT
import com.thejan.flickrimagesearch.helper.TOKEN
import com.thejan.flickrimagesearch.model.Photo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel : BaseViewModel() {

    private var maxPageCount = 1
    private var currentPage = 1
    private var currentText = ""

    val responseResult: MutableLiveData<List<Photo>> by lazy {
        MutableLiveData<List<Photo>>()
    }

    init {
        state = MutableLiveData()
    }

    fun getPhotos(page: Int, text: String) {
        currentText = text
        addDisposable(
            apiCall.getPhotos(
                TOKEN,
                METHOD_RECENT,
                FORMAT,
                1,
                page,
                text
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state!!.postValue(ViewModelState.loading()) }
                .subscribe(
                    { response ->
                        currentPage = response.photos!!.page
                        responseResult.postValue(response.photos!!.photo)
                        maxPageCount = response!!.photos!!.pages
                        currentPage = response!!.photos!!.page
                        state!!.postValue(ViewModelState.success())
                    },

                    {
                        state!!.postValue(ViewModelState.error())
                    })

        )

    }

    fun callToTheNextPage() {
        if (currentPage < maxPageCount) {
            getPhotos(++currentPage, currentText)
        }
    }
}