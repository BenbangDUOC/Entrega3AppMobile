package com.example.gestorproductos.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.gestorproductos.utils.Constants

object RetrofitClient {

    // Creación de la instancia Retrofit
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            // Uso de Gson para convertir el JSON en objetos Producto (Consumo API: 20%)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Acceso a la interfaz de servicio
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}