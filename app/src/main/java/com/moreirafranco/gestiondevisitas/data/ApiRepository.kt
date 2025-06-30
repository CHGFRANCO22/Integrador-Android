package com.moreirafranco.gestiondevisitas.data

import android.util.Log
import com.moreirafranco.gestiondevisitas.network.RetrofitClient
import com.moreirafranco.gestiondevisitas.network.VisitaPayload
import retrofit2.Response

class ApiRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getVisitas(): Response<List<Visita>>? {
        return try {
            apiService.getVisitas()
        } catch (e: Exception) {
            Log.e("ApiRepository", "Error al obtener visitas: ${e.message}")
            null
        }
    }

    suspend fun addVisita(payload: VisitaPayload): Response<Unit>? {
        return try {
            apiService.addVisita(payload)
        } catch (e: Exception) {
            Log.e("ApiRepository", "Error al agregar visita: ${e.message}")
            null
        }
    }

    suspend fun updateVisita(id: Int, payload: VisitaPayload): Response<Unit>? {
        return try {
            // "eq." significa "equals". Es la forma de filtrar en Supabase.
            apiService.updateVisita("eq.$id", payload)
        } catch (e: Exception) {
            Log.e("ApiRepository", "Error al actualizar visita: ${e.message}")
            null
        }
    }

    suspend fun deleteVisita(id: Int): Response<Unit>? {
        return try {
            apiService.deleteVisita("eq.$id")
        } catch (e: Exception) {
            Log.e("ApiRepository", "Error al eliminar visita: ${e.message}")
            null
        }
    }
}
