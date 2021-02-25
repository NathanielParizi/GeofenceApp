package com.example.geofenceapp.network.remote

import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class GeofenceRepositoryTest : AutoCloseKoinTest() {

    private val service: GeofenceService by inject()
    private lateinit var subject: GeofenceRepository

    @Before
    fun setUp() {
        startKoin { modules(testAppModule) }
        subject = GeofenceRepository()

    }
}