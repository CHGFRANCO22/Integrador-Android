package com.moreirafranco.gestiondevisitas.network

import com.google.gson.annotations.SerializedName
import com.moreirafranco.gestiondevisitas.data.Visita
import retrofit2.Response
import retrofit2.http.*

data class VisitaPayload(
    @SerializedName("nombreCliente")
    val nombreCliente: String,
    @SerializedName("direccion")
    val direccion: String,
    @SerializedName("detalles")
    val detalles: String
)

interface ApiService {
    // --- CAMBIO CLAVE ---
    // Volvemos a usar el comodín "*" para que Supabase nos dé todas las columnas
    // y evitamos el error "column does not exist".
    @GET("visitas?select=*")
    suspend fun getVisitas(): Response<List<Visita>>

    @POST("visitas")
    suspend fun addVisita(@Body nuevaVisita: VisitaPayload): Response<Unit>

    @PATCH("visitas")
    suspend fun updateVisita(@Query("id") id: String, @Body visitaActualizada: VisitaPayload): Response<Unit>

    @DELETE("visitas")
    suspend fun deleteVisita(@Query("id") id: String): Response<Unit>
}