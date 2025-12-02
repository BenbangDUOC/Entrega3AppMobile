package com.example.gestorproductos.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gestorproductos.R
import com.example.gestorproductos.data.local.model.Producto

class ListadoAdapter(
    private var products: List<Producto>,
    private val onProductClick: (Producto) -> Unit // Función de callback para la navegación
) : RecyclerView.Adapter<ListadoAdapter.ProductoViewHolder>() {

    // Referencias a las vistas del layout item_producto.xml
    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val skuTextView: TextView = view.findViewById(R.id.tv_sku)
        val nombreTextView: TextView = view.findViewById(R.id.tv_nombre)
        val precioTextView: TextView = view.findViewById(R.id.tv_precio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = products[position]

        // Mostrando los elementos estrictamente requeridos
        holder.skuTextView.text = holder.itemView.context.getString(R.string.sku_format, producto.sku)
        holder.nombreTextView.text = holder.itemView.context.getString(R.string.nombre_format, producto.nombre)
        holder.precioTextView.text = holder.itemView.context.getString(R.string.precio_format, String.format("%.2f", producto.precio))

        // Manejo del click para navegar a DetalleActivity (Requisito Funcional: Navegación)
        holder.itemView.setOnClickListener {
            onProductClick(producto)
        }
    }

    override fun getItemCount() = products.size

    // Función para actualizar los datos del RecyclerView
    fun updateProducts(newProducts: List<Producto>) {
        products = newProducts
        notifyDataSetChanged()
    }
}