package com.example.gestorproductos.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Clase de utilidad para verificar el estado de la conexión a Internet.
 * (Requerimiento de Validación: Lógica de Negocio 25%)
 */
object NetworkUtils {

    fun isInternetAvailable(context: Context): Boolean {
        // Obtener el servicio de conectividad
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // Obtener la red activa
        val network = connectivityManager.activeNetwork ?: return false

        // Obtener las capacidades de la red
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        // Verificar si tiene capacidad de Internet
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}