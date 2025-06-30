package com.moreirafranco.gestiondevisitas.viewmodel

import androidx.lifecycle.*
import com.moreirafranco.gestiondevisitas.data.ApiRepository
import com.moreirafranco.gestiondevisitas.data.Visita
import kotlinx.coroutines.launch

class VisitaListViewModel(private val repository: ApiRepository) : ViewModel() {
    private val _visitas = MutableLiveData<List<Visita>>()
    val visitas: LiveData<List<Visita>> get() = _visitas
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun cargarVisitas() {
        viewModelScope.launch {
            val response = repository.getVisitas()
            if (response != null && response.isSuccessful) {
                _visitas.value = response.body()
            } else {
                _error.value = "Error al cargar las visitas."
            }
        }
    }
}


