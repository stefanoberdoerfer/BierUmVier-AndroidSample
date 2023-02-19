package de.stefanoberdoerfer.bierumvier.data.network

import de.stefanoberdoerfer.bierumvier.data.network.model.Beer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PunkApi {
    @GET("beers")
    suspend fun getBeers(@Query("page") page: Long): Response<List<Beer>>

    @GET("beers/random")
    suspend fun getRandomBeer(): Response<Beer>
}