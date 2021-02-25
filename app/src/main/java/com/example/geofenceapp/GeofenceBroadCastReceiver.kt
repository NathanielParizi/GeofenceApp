package com.example.geofenceapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.geofenceapp.model.UserData
import com.example.geofenceapp.viewModels.GeofenceViewModel
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import org.koin.core.KoinComponent
import org.koin.core.inject

private const val TAG = "GeofenceBroadCastReceiv"

open class GeofenceBroadCastReceiver : BroadcastReceiver(), KoinComponent {

    private val geoFenceViewModel: GeofenceViewModel by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        //receives a signal when ever Geofence is triggered
        Toast.makeText(context, "Geofence Triggered", Toast.LENGTH_LONG)

        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            Log.d(TAG, "onReceive: Error receiving Geofenc")
            return
        }


        val geofenceList = geofencingEvent.triggeringGeofences
        for (geofence in geofenceList) {
            Log.d(TAG, "onReceive: " + geofence.requestId)
        }
        val transitionType = geofencingEvent.geofenceTransition

        when (transitionType) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                Toast.makeText(context, "Entered Geofence", Toast.LENGTH_LONG).show()
                geoFenceViewModel.userMutableLiveData.setValue(UserData("Entering"))
                geoFenceViewModel.basicCoroutineFetch()
            }
            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                Toast.makeText(context, "Exited Geofence", Toast.LENGTH_LONG).show()
                geoFenceViewModel.userMutableLiveData.setValue(UserData("Exiting"))
                geoFenceViewModel.basicCoroutineFetch()
            }
            Geofence.GEOFENCE_TRANSITION_DWELL -> {
                Toast.makeText(context, "Roaming inside Geofence", Toast.LENGTH_LONG).show()
                geoFenceViewModel.userMutableLiveData.setValue(UserData("Dwelling"))
                geoFenceViewModel.basicCoroutineFetch()
            }
        }
    }
}