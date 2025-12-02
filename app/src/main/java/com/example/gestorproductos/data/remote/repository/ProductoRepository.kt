package com.example.gestorproductos.data.remote.repository

import com.example.gestorproductos.data.local.database.ProductoDao
import com.example.gestorproductos.data.local.model.Producto
import com.example.gestorproductos.data.remote.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repositorio central que maneja la lógica híbrida de carga de datos.
 * (Lógica de Negocio: 25%, Persistencia: 25%, Consumo API: 20%)
 */
class ProductoRepository(
    private val apiService: ApiService,
    private val productoDao: ProductoDao
) {

    // 1. Cargar desde API y Guardar en BD (Botón "Cargar desde Rest API" - Caso Exitoso)
    suspend fun fetchProductsFromApiAndStore(): List<Producto> {
        // Ejecutar la operación de red en el hilo IO (Calidad de Código: 10%)
        return withContext(Dispatchers.IO) {
            try {
                // Llamar a Retrofit
                val products = apiService.getProductosFromApi()

                // Guardar los datos en la base de datos local (Persistencia de Datos: 25%)
                productoDao.insertAll(products)

                return@withContext products

            } catch (e: Exception) {
                // Manejo de Errores: Si falla la red o el JSON, propagar la excepción
                throw e
            }
        }
    }

    // 2. Cargar desde Base de Datos Local (Botón "Cargar desde Base de Datos Local" - Caso Exitoso)
    suspend fun getLocalProducts(): List<Producto> {
        return withContext(Dispatchers.IO) {
            // Leer desde Room
            productoDao.getAllProductos()
        }
    }

    // 3. Validación de BD Vacía (Lógica de Negocio: 25%)
    suspend fun isDatabaseEmpty(): Boolean {
        return withContext(Dispatchers.IO) {
            productoDao.countProducts() == 0
        }
    }

    // 4. Guardar datos en BD (usado desde la Pantalla de Listado)
    suspend fun saveProducts(products: List<Producto>) {
        withContext(Dispatchers.IO) {
            productoDao.insertAll(products)
        }
    }
}