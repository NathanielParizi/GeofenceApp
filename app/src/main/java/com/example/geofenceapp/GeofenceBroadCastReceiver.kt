package com.example.geofenceapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
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

    }
}