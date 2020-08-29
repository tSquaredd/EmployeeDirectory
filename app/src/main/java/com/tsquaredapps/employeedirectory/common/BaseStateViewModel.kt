package com.tsquaredapps.employeedirectory.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Base class for ViewModels that use a sealed class
 * state to communicate with corresponding view
 */
open class BaseStateViewModel<T> : ViewModel() {
    protected val state = MutableLiveData<T>()
    val stateLiveData: LiveData<T>
        get() = state
}