package com.example.gestorproductos.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gestorproductos.data.local.model.Producto

@Dao
interface ProductoDao {

    // Leer todos los productos (Persistencia de Datos: 25%)
    @Query("SELECT * FROM productos")
    suspend fun getAllProductos(): List<Producto>

    // Guardar una lista de productos (Persistencia de Datos: 25%)
    // Si el SKU ya existe, reemplaza el producto viejo (OnConflictStrategy.REPLACE).
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(productos: List<Producto>)

    // Contar productos para la Validación de Lógica de Negocio (25%)
    @Query("SELECT COUNT(sku) FROM productos")
    suspend fun countProducts(): Int
}