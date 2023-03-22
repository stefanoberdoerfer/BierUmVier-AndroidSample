package de.stefanoberdoerfer.bierumvier.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stefanoberdoerfer.bierumvier.data.network.BeerRepository
import de.stefanoberdoerfer.bierumvier.data.network.model.NetReqState
import de.stefanoberdoerfer.bierumvier.util.Constants
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class BeerListViewModel(private val beerRepo: BeerRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            // load initial page of beers if table does not contain it already
            val localBeersCount = beerRepo.countBeersInDb()
            if (localBeersCount < Constants.PageSize) {
                fetchMoreBeers()
            }
        }
    }

    val beers = beerRepo.getAllBeers().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        listOf()
    )

    // prevent multiple fetch jobs at the same time
    private val currentlyFetching = AtomicBoolean(false)

    private val _netRequestState = MutableSharedFlow<NetReqState>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val netRequestState = _netRequestState.asSharedFlow()

    fun fetchMoreBeers() {
        if (!currentlyFetching.getAndSet(true)) {
            viewModelScope.launch {
                beerRepo.fetchBeers()
                    .collectLatest {
                        _netRequestState.emit(it)
                    }
                currentlyFetching.set(false)
            }
        }
    }
}