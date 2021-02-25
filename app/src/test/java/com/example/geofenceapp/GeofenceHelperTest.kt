package com.example.geofenceapp

import com.example.geofenceapp.network.remote.testAppModule
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest

class GeofenceHelperTest : AutoCloseKoinTest() {

    private lateinit var subject: GeofenceHelper

    @Before
    fun setUp() {
        startKoin { modules(testAppModule) }
        subject = GeofenceHelper(subject.applicationContext)

    }
}