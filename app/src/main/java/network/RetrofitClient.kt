package com.moreirafranco.gestiondevisitas.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Reemplaza con los datos de tu proyecto de Supabase
    private const val BASE_URL = "https://kplbbigulvyjkqodkujo.supabase.co/rest/v1/"
    private const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImtwbGJiaWd1bHZ5amtxb2RrdWpvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTEyNTE3MjQsImV4cCI6MjA2NjgyNzcyNH0.1MNUuFOTjNK3ZJktcgq9lm08oGE5TZJ0w-G3Y4vfNus"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // --- ESTA ES LA PARTE CRÍTICA ---
    // Este "client" intercepta cada llamada antes de que salga a internet
    // y le añade los dos encabezados que Supabase necesita para autorizarte.
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("apikey", API_KEY)
                .addHeader("Authorization", "Bearer $API_KEY")
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // ¡Muy importante! Usamos el cliente con el interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}