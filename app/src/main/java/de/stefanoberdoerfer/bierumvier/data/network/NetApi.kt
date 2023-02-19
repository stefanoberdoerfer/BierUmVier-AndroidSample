package de.stefanoberdoerfer.bierumvier.data.network

import com.squareup.moshi.Moshi
import de.stefanoberdoerfer.bierumvier.BuildConfig
import de.stefanoberdoerfer.bierumvier.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetApi {

    val client: PunkApi by lazy { retrofit.create(PunkApi::class.java) }

    private val okClient: OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
            // debugging interceptor
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            }
        }.build()
    }

    private val moshi by lazy {
        Moshi.Builder()
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BaseUrl)
            .client(okClient)
            .addConverterFactory(
                MoshiConverterFactory
                    .create(moshi)
                    .asLenient()
            )
            .build()
    }
}