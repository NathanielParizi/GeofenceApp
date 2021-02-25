package com.example.geofenceapp.network.remote

import com.example.geofenceapp.model.UserData
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import retrofit2.Callback
import retrofit2.Response

class GeofenceRepositoryTest : AutoCloseKoinTest() {

    private val service: GeofenceService by inject()
    private lateinit var subject: GeofenceRepository

    @Before
    fun setUp() {
        startKoin { modules(testAppModule) }
        subject = GeofenceRepository()
    }

//    @Test
//    fun `getUserUpdate for onSuccess returns UserData object`() {
//        val mockCall = mockk<Response<UserData>>
//        every { service.getUserUpdate() } returns mockCall
//        val slot = slot<Callback<Response<UserData>>>()
//    }
}