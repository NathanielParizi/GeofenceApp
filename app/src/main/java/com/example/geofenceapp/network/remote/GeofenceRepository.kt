package com.example.geofenceapp.network.remote

import com.example.geofenceapp.model.UserData
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Response

class GeofenceRepository : KoinComponent {

    private val geoFenceService: GeofenceService by inject()

    // Make a network call to API with users data

    suspend fun getUserUpdate(
        userData: String,
    ): Response<UserData> {
//        return geoFenceService.getUserUpdate(userData)
        return Response.success(UserData(""))
    }

}