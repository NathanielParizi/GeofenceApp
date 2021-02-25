package com.example.geofenceapp.network.remote

import com.example.geofenceapp.model.UserData
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Response
import retrofit2.Retrofit

class GeofenceService : KoinComponent {

    private val retrofitBuilder: Retrofit.Builder by inject()
    private var apiService: IApiService

    init {
        // baseUrl for the API would be in a constant in a contract file ideally
        apiService = retrofitBuilder.baseUrl("http://www.google.com")
            .build().create(IApiService::class.java)
    }

    // function will make API call with Retrofit Interface ideally
    suspend fun getUserUpdate(userData: String): UserData {
//        return apiService.getUserUpdate(userData)
        return UserData("user-data")

    }
}