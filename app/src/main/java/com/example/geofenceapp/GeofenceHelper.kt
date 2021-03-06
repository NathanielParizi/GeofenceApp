package com.example.geofenceapp

import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.maps.model.LatLng
import org.koin.core.KoinComponent

class GeofenceHelper(base: Context?) : ContextWrapper(base), KoinComponent {

    val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofenceBroadCastReceiver::class.java)
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        PendingIntent.getBroadcast(this, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun getGeoFencingRequest(geofence: Geofence): GeofencingRequest {
        // If a geofence is created and user is inside, then the geofence will be triggered
        return GeofencingRequest.Builder().addGeofence(geofence)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .build()
    }

    fun getGeofence(id: String, latLng: LatLng, radius: Float, transitionType: Int): Geofence {

        return Geofence.Builder()
            .setCircularRegion(latLng.latitude, latLng.longitude, radius)
            .setRequestId(id)
            .setTransitionTypes(transitionType).setLoiteringDelay(5000)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
    }

    fun getErrorString(e: Exception): String {
        if (e is ApiException) {
            val apiException = e as ApiException
            when (apiException.statusCode) {
                GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE -> "GEOFENCE NOT AVAIALBLE"
                GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES -> "TOO MANY GEOFENCES AVAIALBLE"
                GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS -> "GEOFENCE_TOO_MANY_PENDING_INTENTS"
                else -> "Unknown"
            }
        }
        return e.localizedMessage.toString()
    }

}