package com.example.geofenceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var goeFencingClient: GeofencingClient


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
    }
}