package de.stefanoberdoerfer.bierumvier.data.network

import de.stefanoberdoerfer.bierumvier.BuildConfig
import de.stefanoberdoerfer.bierumvier.data.db.AppDatabase
import de.stefanoberdoerfer.bierumvier.data.db.model.BeerEntity
import de.stefanoberdoerfer.bierumvier.data.network.model.NetReqState
import de.stefanoberdoerfer.bierumvier.data.network.model.NetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class BeerRepository(private val netApi: PunkApi, private val appDb: AppDatabase) {

    fun fetchBeers() = flow {
        emit(NetReqState.Loading)

        // Only for presentation: small delay so loading indicator is visible
        if (BuildConfig.DEBUG) {
            delay(2000)
        }

        val beerDao = appDb.beerDao()

        // Get page by checking beer with the highest id in local db
        // Note:
        // This is only working in this sample app, in a production app this could
        // be solved better by saving which page number has already been loaded
        val highestId = beerDao.getHighestBeerId()
        val page: Long = if (highestId != null) {
            // integer division is always rounded down; +1 gives us the next page
            (highestId / 25) + 1
        } else {
            1
        }

        val response = netApi.getBeers(page)

        if (response.isSuccessful) {
            // persist new beers in db
            val beerEntities = response.body()?.map { BeerEntity.from(it) } ?: listOf()
            appDb.beerDao().insert(beerEntities)
            emit(NetReqState.Success)
        } else {
            emit(NetReqState.Error.fromErrorResponse(response))
        }
    }.catch {
        it.printStackTrace()
        emit(NetReqState.Error(NetworkError.Unknown))
    }.flowOn(Dispatchers.IO)

    fun getBeerById(id: Long) = appDb.beerDao().getById(id)

    fun getAllBeers() = appDb.beerDao().getAllAsFlow()

    suspend fun updateEvaluationFor(id: Long, value: Float) =
        appDb.beerDao().updateEvaluation(id, value)

    suspend fun countBeersInDb() = appDb.beerDao().countBeersInDb()
}