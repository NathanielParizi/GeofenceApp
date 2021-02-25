package com.example.geofenceapp.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geofenceapp.model.UserData
import com.example.geofenceapp.network.remote.GeofenceRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

private const val TAG = "GeofenceViewModel"
class GeofenceViewModel : ViewModel(), KoinComponent {

    private val service: GeofenceRepository by inject()

    var responseError = ""
    val userMutableLiveData = MutableLiveData<UserData>()
    val userLiveData: LiveData<UserData>
        get() = userMutableLiveData
    private var job: Job? = null

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        //Cancels job when ViewModel is terminated
    }


    fun basicCoroutineFetch() {

        viewModelScope.launch {
            val response = service.getUserUpdate(userMutableLiveData.value.toString())
            if (response.isSuccessful && response.body() != null) {
                userMutableLiveData.value = response.body()
            } else {
                responseError = response.errorBody().toString()
                onError(responseError)
            }
        }
    }

    private fun onError(message: String) {
        Log.d(TAG, "onError: $message")
    }
}