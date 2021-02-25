package com.example.geofenceapp

import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.maps.model.LatLng

class GeofenceHelper(base: Context?) : ContextWrapper(base) {

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

}