package com.example.geofenceapp

import android.content.Context
import com.example.geofenceapp.network.remote.testAppModule
import com.google.android.gms.location.Geofence
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest

class GeofenceHelperTest : AutoCloseKoinTest() {

    private lateinit var subject: GeofenceHelper

    @Before
    fun setUp() {
        // Ideally we don't want to mock Context because it manages a broad scope to handle resources
        // I couldn't get ApplicationProvider.getApplicationContext to work properly so used this mock as an alternative
        val mockContext = mockk<Context>(relaxed = true)
        startKoin { modules(testAppModule) }
        subject = GeofenceHelper(mockContext)
    }

    @Test
    fun `onError returns an error string associated with Exception`() {
        val actual = subject.getErrorString(
            Exception(
                "OutOfMemoryException"
            )
        )
        val expected = "OutOfMemoryException"
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `getGeofence returns a complex object from it's associated Builder class`(){
        val geofence = Geofence.Builder()
            .setCircularRegion(10.0, 12.0, 100.0F)
            .setRequestId("GEOFENCE_ID")
            .setTransitionTypes(1).setLoiteringDelay(5000)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
    }
}