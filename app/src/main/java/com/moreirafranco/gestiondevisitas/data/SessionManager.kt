package com.moreirafranco.gestiondevisitas.data

import android.content.Context
import android.content.SharedPreferences

object SessionManager {

    private const val PREFS_NAME = "GestionDeVisitasPrefs"

    // --- Claves para la Sesión ---
    private const val KEY_AUTH_TOKEN = "auth_token"

    // --- NUEVA: Clave para los Favoritos ---
    private const val KEY_FAVORITE_IDS = "favorite_ids"


    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // --- Funciones de Sesión (sin cambios) ---

    fun saveAuthToken(context: Context, token: String) {
        getPreferences(context).edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    fun getAuthToken(context: Context): String? {
        return getPreferences(context).getString(KEY_AUTH_TOKEN, null)
    }

    fun clearAuthToken(context: Context) {
        getPreferences(context).edit().remove(KEY_AUTH_TOKEN).apply()
    }


    fun getFavorites(context: Context): Set<String> {
        // SharedPreferences puede guardar un conjunto de Strings. Si no encuentra nada, devuelve un conjunto vacío.
        return getPreferences(context).getStringSet(KEY_FAVORITE_IDS, emptySet()) ?: emptySet()
    }



    fun isFavorite(context: Context, visitaId: Int): Boolean {
        val favorites = getFavorites(context)
        return favorites.contains(visitaId.toString())
    }

    fun addFavorite(context: Context, visitaId: Int) {
        val favorites = getFavorites(context).toMutableSet() // Hacemos una copia mutable
        favorites.add(visitaId.toString())
        getPreferences(context).edit().putStringSet(KEY_FAVORITE_IDS, favorites).apply()
    }


    fun removeFavorite(context: Context, visitaId: Int) {
        val favorites = getFavorites(context).toMutableSet() // Hacemos una copia mutable
        favorites.remove(visitaId.toString())
        getPreferences(context).edit().putStringSet(KEY_FAVORITE_IDS, favorites).apply()
    }
}



