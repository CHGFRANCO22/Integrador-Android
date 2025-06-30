package com.moreirafranco.gestiondevisitas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moreirafranco.gestiondevisitas.data.ApiRepository

class ViewModelFactory : ViewModelProvider.Factory {
    private val repository by lazy { ApiRepository() }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(VisitaListViewModel::class.java) -> VisitaListViewModel(repository) as T
            modelClass.isAssignableFrom(VisitaDetailViewModel::class.java) -> VisitaDetailViewModel(repository) as T
            modelClass.isAssignableFrom(VisitaFormViewModel::class.java) -> VisitaFormViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}