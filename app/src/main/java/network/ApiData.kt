package com.moreirafranco.gestiondevisitas.network

import com.google.gson.annotations.SerializedName

// Molde para enviar usuario y contraseña en el login
data class LoginRequest(
    val email: String,
    val pass: String // Usamos 'pass' porque 'password' a veces es una palabra reservada
)

// Molde para recibir el token del servidor
data class LoginResponse(
    // @SerializedName nos permite usar un nombre de variable diferente al que viene en el JSON.
    // Por si en el JSON se llama "auth_token", por ejemplo.
    @SerializedName("token")
    val token: String
)
