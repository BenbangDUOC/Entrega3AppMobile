package com.example.gestorproductos.ui.detail

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.gestorproductos.R
import com.example.gestorproductos.data.local.model.Producto
import com.example.gestorproductos.ui.list.ListadoActivity
import com.google.gson.Gson

/**
 * Pantalla C: Detalle del Producto
 * Muestra toda la información disponible del producto seleccionado (Requisito Funcional C)
 */
class DetalleActivity : AppCompatActivity() {

    // INSTANCIACIÓN CORREGIDA: Esto utiliza el ViewModel y elimina la advertencia.
    private val viewModel: DetalleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        // Obtener el JSON del Intent que fue enviado desde ListadoActivity
        val productJson = intent.getStringExtra(ListadoActivity.EXTRA_PRODUCT_DETAIL)

        if (productJson != null) {
            try {
                // Deserializar el JSON a un objeto Producto
                val producto = Gson().fromJson(productJson, Producto::class.java)
                displayProductDetails(producto)
            } catch (e: Exception) {
                // Manejo básico de errores si el JSON es inválido
                title = "Error de Carga"
                findViewById<TextView>(R.id.tv_detail_nombre).text = "Error al cargar los detalles del producto."
            }
        } else {
            title = "Producto no encontrado"
            findViewById<TextView>(R.id.tv_detail_nombre).text = "No se recibió información del producto."
        }
    }

    private fun displayProductDetails(producto: Producto) {
        // Establecer el título de la Activity con el nombre del producto
        title = producto.nombre

        // Referenciar y poblar los TextViews
        findViewById<TextView>(R.id.tv_detail_nombre).text = producto.nombre
        findViewById<TextView>(R.id.tv_detail_sku).text = getString(R.string.sku_format, producto.sku)

        // Formatear precio con dos decimales
        val formattedPrice = String.format("$%.2f", producto.precio)
        findViewById<TextView>(R.id.tv_detail_precio).text = formattedPrice

        findViewById<TextView>(R.id.tv_detail_descripcion).text = producto.descripcion
        findViewById<TextView>(R.id.tv_detail_categoria).text = producto.categoria

        // Mostrar stock
        findViewById<TextView>(R.id.tv_detail_stock).text = "${producto.stock} unidades disponibles"
    }
}