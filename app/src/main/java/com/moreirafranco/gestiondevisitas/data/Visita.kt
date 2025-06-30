package com.moreirafranco.gestiondevisitas.data

import com.google.gson.annotations.SerializedName

data class Visita(
    @SerializedName("id")
    val id: Int,

    // Como confirmaste que en Supabase se llama "nombreCliente", lo dejamos así.
    @SerializedName("nombreCliente")
    val nombreCliente: String,

    @SerializedName("direccion")
    val direccion: String,

    @SerializedName("detalles")
    val detalles: String,

    @SerializedName("created_at")
    val createdAt: String
)