package com.example.demo.base

import android.app.Application
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel

/**
 * Created by Harshal Chaudhari on 18/12/19.
 */


abstract class BaseViewModel(application: Application) : AndroidViewModel(application), Observable {
    var repository: BaseRepository = BaseRepository(application)
    val callbacks = PropertyChangeRegistry()


    fun setErrorCallback(errorCallBack: BaseErrorCallBack) =
        repository.setErrorCallback(errorCallBack)

    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

}