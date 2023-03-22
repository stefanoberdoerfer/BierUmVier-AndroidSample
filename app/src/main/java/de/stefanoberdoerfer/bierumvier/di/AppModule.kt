package de.stefanoberdoerfer.bierumvier.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import de.stefanoberdoerfer.bierumvier.BuildConfig
import de.stefanoberdoerfer.bierumvier.data.db.AppDatabase
import de.stefanoberdoerfer.bierumvier.data.network.BeerRepository
import de.stefanoberdoerfer.bierumvier.data.network.PunkApi
import de.stefanoberdoerfer.bierumvier.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single { provideOkHttpClient() }
    single { provideMoshi() }
    single { provideRetrofit(get(), get()) }
    single { providePunkBeersApi(get()) }

    single { provideDatabase(get()) }

    single { BeerRepository(get(), get()) }
}

fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
    // debugging interceptor
    if (BuildConfig.DEBUG) {
        addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
    }
}.build()

fun provideMoshi(): Moshi = Moshi.Builder()
    .build()

fun provideRetrofit(okClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
    .baseUrl(Constants.BaseUrl)
    .client(okClient)
    .addConverterFactory(
        MoshiConverterFactory
            .create(moshi)
            .asLenient()
    )
    .build()

fun providePunkBeersApi(retrofit: Retrofit): PunkApi = retrofit.create(PunkApi::class.java)

fun provideDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, "beer_db")
        .build()