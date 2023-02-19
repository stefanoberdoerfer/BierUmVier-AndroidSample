package de.stefanoberdoerfer.bierumvier.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stefanoberdoerfer.bierumvier.data.network.BeerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BeerDetailViewModel(private val beerId: Long) : ViewModel() {

    val beer = BeerRepository.getBeerById(beerId).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        null
    )

    fun updateEvaluation(value: Float) {
        viewModelScope.launch {
            BeerRepository.updateEvaluationFor(beerId, value)
        }
    }
}