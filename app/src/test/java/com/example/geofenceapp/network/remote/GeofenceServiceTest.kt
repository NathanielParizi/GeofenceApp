package com.example.geofenceapp.network.remote

import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest

class GeofenceServiceTest : AutoCloseKoinTest() {

    private lateinit var subject: GeofenceService

    @Before
    fun setUp() {
        startKoin { modules(testAppModule) }
        subject = GeofenceService()
    }

//    @Test
//    suspend fun `updateUserData returns a UserData repsonse`() {
//        val actual = subject.getUserUpdate("user-data")
//        val expected = "user-data"
//        Assert.assertEquals(expected,actual)
//    }
}