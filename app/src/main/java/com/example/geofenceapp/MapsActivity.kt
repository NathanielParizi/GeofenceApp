package com.example.geofenceapp

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject

private const val TAG = "MapsActivity"

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var goeFencingClient: GeofencingClient
    private val geoFenceHelper: GeofenceHelper by inject()

    private val FINE_REQUEST_REQUEST_CODE = 10111
    private val GEOFENCE_RADIUS: Double = 50.0
    private val GEOFENCE_ID = "GEOFENCE_ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        goeFencingClient = LocationServices.getGeofencingClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val chicago = LatLng(41.881, -87.623177)
        mMap.addMarker(MarkerOptions().position(chicago).title("Marker in Chicago"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(chicago, 18.0f))
        enableUserLocation()
    }

    private fun enableUserLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true

        } else {
            // Ask for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // Show user dialog for permission requirement
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), FINE_REQUEST_REQUEST_CODE
                )

            } else {
                // Ask for the permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), FINE_REQUEST_REQUEST_CODE
                )

            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty() && PackageManager.PERMISSION_GRANTED == grantResults[0] && requestCode == FINE_REQUEST_REQUEST_CODE) {
            // We have permission
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            mMap.isMyLocationEnabled = true

        }

    }

    private fun addMaker(latLng: LatLng) {
        val markerOption = MarkerOptions().position(latLng)
        mMap.addMarker(markerOption)

    }

    private fun addGeoFencedArea(latLng: LatLng, radius: Double) {
        val circleOptions = CircleOptions()
        circleOptions.center(latLng)
        circleOptions.radius(radius)
        circleOptions.strokeColor(Color.argb(255, 0, 255, 0))
        circleOptions.fillColor(Color.argb(25, 0, 255, 30))
        circleOptions.strokeWidth(4f)
        mMap.addCircle(circleOptions)
    }

    override fun onMapLongClick(latLng: LatLng?) {
        mMap.clear()
        addMaker(latLng!!)
        addGeoFencedArea(latLng, GEOFENCE_RADIUS)
        addGeoFence(latLng, GEOFENCE_RADIUS.toFloat())
    }

    private fun addGeoFence(latLng: LatLng, radius: Float) {
        val transitionType = Geofence.GEOFENCE_TRANSITION_ENTER or
                Geofence.GEOFENCE_TRANSITION_EXIT or
                Geofence.GEOFENCE_TRANSITION_DWELL
        val geofence = geoFenceHelper.getGeofence(
            GEOFENCE_ID,
            latLng,
            radius,
            transitionType
        )
        val geoFencingRequest = geoFenceHelper.getGeoFencingRequest(geofence)
        val pendingIntent = geoFenceHelper.geofencePendingIntent

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        // task
        goeFencingClient.addGeofences(geoFencingRequest, pendingIntent)
            .addOnSuccessListener {
                Log.d(TAG, "addGeoFence: Geofence added")
            }
            .addOnFailureListener {
            }

    }

}