package com.moreirafranco.gestiondevisitas.viewmodel

import androidx.lifecycle.*
import com.moreirafranco.gestiondevisitas.data.ApiRepository
import com.moreirafranco.gestiondevisitas.network.VisitaPayload
import kotlinx.coroutines.launch

class VisitaFormViewModel(private val repository: ApiRepository) : ViewModel() {
    private val _operationComplete = MutableLiveData<Boolean>()
    val operationComplete: LiveData<Boolean> get() = _operationComplete
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun addVisita(payload: VisitaPayload) {
        viewModelScope.launch {
            val response = repository.addVisita(payload)
            if (response != null && response.isSuccessful) {
                _operationComplete.value = true
            } else {
                _error.value = "Error al crear la visita."
            }
        }
    }

    fun updateVisita(id: Int, payload: VisitaPayload) {
        viewModelScope.launch {
            val response = repository.updateVisita(id, payload)
            if (response != null && response.isSuccessful) {
                _operationComplete.value = true
            } else {
                _error.value = "Error al actualizar la visita."
            }
        }
    }
}

