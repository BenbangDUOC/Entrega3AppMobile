package com.example.gestorproductos.data.remote.api

import com.example.gestorproductos.data.local.model.Producto
import retrofit2.http.GET
import com.example.gestorproductos.utils.Constants

interface ApiService {

    // Obtiene una lista de Productos. El nombre del endpoint está en Constants.
    @GET(Constants.API_ENDPOINT)
    suspend fun getProductosFromApi(): List<Producto>
}