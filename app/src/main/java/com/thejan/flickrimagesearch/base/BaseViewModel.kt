package com.thejan.flickrimagesearch.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thejan.flickrimagesearch.network.ApiInterface
import com.thejan.flickrimagesearch.network.ServiceGenerator
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel:ViewModel(){
    var retrofit = ServiceGenerator.getClient()
    var apiCall = retrofit.create(ApiInterface::class.java)!!
    var state: MutableLiveData<ViewModelState>? = null
    private lateinit var disposables: CompositeDisposable

    val message: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        initRx()
    }

    fun updateClient() {
        retrofit = ServiceGenerator.getClient()
        apiCall = retrofit.create(ApiInterface::class.java)!!
    }

    private fun initRx() {
        disposables = CompositeDisposable()
    }

    @Synchronized
    fun addDisposable(disposable: Disposable?) {
        if (disposable == null) return
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposables.isDisposed) disposables.dispose()
    }

}