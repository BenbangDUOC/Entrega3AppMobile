package com.example.gestorproductos.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey
    @SerializedName("SKU")
    val sku: String,

    @SerializedName("Nombre")
    val nombre: String,

    @SerializedName("Precio")
    val precio: Double,

    // Propiedades adicionales para la Pantalla de Detalle
    @SerializedName("Descripcion")
    val descripcion: String,

    @SerializedName("Categoria")
    val categoria: String,

    @SerializedName("Stock")
    val stock: Int
)