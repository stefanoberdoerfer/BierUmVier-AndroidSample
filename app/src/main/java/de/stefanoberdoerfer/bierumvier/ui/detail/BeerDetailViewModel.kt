package de.stefanoberdoerfer.bierumvier.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stefanoberdoerfer.bierumvier.data.network.BeerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BeerDetailViewModel(private val beerRepo: BeerRepository, handle: SavedStateHandle) :
    ViewModel() {

    private val args = BeerDetailFragmentArgs.fromSavedStateHandle(handle)

    val beer = beerRepo.getBeerById(args.beerId).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )

    fun updateEvaluation(value: Float) {
        viewModelScope.launch {
            beerRepo.updateEvaluationFor(args.beerId, value)
        }
    }
}