package de.stefanoberdoerfer.bierumvier.di

import de.stefanoberdoerfer.bierumvier.ui.detail.BeerDetailViewModel
import de.stefanoberdoerfer.bierumvier.ui.list.BeerListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel {
        BeerListViewModel(get())
    }
    viewModel {
        BeerDetailViewModel(get(), get())
    }
}