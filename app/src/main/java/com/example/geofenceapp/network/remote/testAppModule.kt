package com.example.geofenceapp.network.remote

import com.example.geofenceapp.GeofenceBroadCastReceiver
import com.example.geofenceapp.GeofenceHelper
import com.example.geofenceapp.MapsActivity
import com.example.geofenceapp.viewModels.GeofenceViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.util.concurrent.TimeUnit

val testAppModule = module(override = true) {

    viewModel { GeofenceViewModel() }
    single { MapsActivity() }
    single { GeofenceBroadCastReceiver() }
    single { GeofenceHelper(androidContext()) }
    single { GeofenceRepository() }
    single { GeofenceService() }


    single {
        val gson: Gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setDateFormat(DateFormat.LONG)
            .create()
        GsonConverterFactory.create(gson)
    } bind GsonConverterFactory::class

    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        val client = OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .apply { HttpLoggingInterceptor.Level.BODY }
            .cache(null)
            .addInterceptor(loggingInterceptor)
        client.build()

    }

    single {
        val builder =
            Retrofit.Builder()
                .addConverterFactory(get<GsonConverterFactory>())
                .client(get())

        builder
    }

}
