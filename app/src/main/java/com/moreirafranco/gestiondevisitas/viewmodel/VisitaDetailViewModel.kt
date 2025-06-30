package com.moreirafranco.gestiondevisitas.viewmodel

import androidx.lifecycle.*
import com.moreirafranco.gestiondevisitas.data.ApiRepository
import kotlinx.coroutines.launch

class VisitaDetailViewModel(private val repository: ApiRepository) : ViewModel() {
    private val _operationComplete = MutableLiveData<Boolean>()
    val operationComplete: LiveData<Boolean> get() = _operationComplete
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun deleteVisita(id: Int) {
        viewModelScope.launch {
            val response = repository.deleteVisita(id)
            if (response != null && response.isSuccessful) {
                _operationComplete.value = true
            } else {
                _error.value = "Error al eliminar la visita."
            }
        }
    }
}