package com.thejan.flickrimagesearch.base

/**
 * Created by Thejan_Thrimanna on 2020-02-06.
 */

class ViewModelState constructor(
    var status: Status,
    var error: Throwable? = null) {
    companion object {
        fun loading(): ViewModelState {
            return ViewModelState(Status.LOADING)
        }
        fun success(): ViewModelState {
            return ViewModelState(Status.SUCCESS)
        }
        fun error(): ViewModelState {
            return ViewModelState(Status.ERROR)
        }
        fun list_empty(): ViewModelState {
            return ViewModelState(Status.LIST_EMPTY)
        }
        fun correct_version(): ViewModelState {
            return ViewModelState(Status.CORRECT_VERSION)
        }

        fun logout(): ViewModelState {
            return ViewModelState(Status.LOGOUT)
        }
        fun wrong_version(): ViewModelState {
            return ViewModelState(Status.WRONG_VERSION)
        }
    }
}
