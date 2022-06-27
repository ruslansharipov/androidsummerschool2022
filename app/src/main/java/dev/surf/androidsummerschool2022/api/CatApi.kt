package dev.surf.androidsummerschool2022.api

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query


interface CatApi {

    @GET("cat?json=true")
    suspend fun getRandomCat(@Query("tag") tag: String?): CatResponse

    companion object {
        private const val BASE_URL = "https://cataas.com/"

        fun create() : CatApi {
            val contentType = "application/json".toMediaType()
            val converterFactory = Json.asConverterFactory(contentType)
            val loggingInterceptor = HttpLoggingInterceptor { message -> Log.d("Okhttp", message) }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okhttp = OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(converterFactory)
                .client(okhttp)
                .build()
                .create(CatApi::class.java)
        }
    }
}