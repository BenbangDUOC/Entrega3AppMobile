package com.example.gestorproductos.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestorproductos.data.local.model.Producto
import com.example.gestorproductos.data.remote.repository.ProductoRepository
import kotlinx.coroutines.launch

class ListadoViewModel(private val repository: ProductoRepository) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    /**
     * Guarda la lista de productos actual en la Base de Datos Local (Room).
     * (Persistencia de Datos: 25%)
     */
    fun saveProductsToLocalDb(products: List<Producto>) {
        viewModelScope.launch {
            try {
                repository.saveProducts(products) // Llama a la función del Repositorio
                _message.value = "✅ ¡Productos guardados localmente con éxito!"
            } catch (e: Exception) {
                _message.value = "❌ Error al guardar en BD: ${e.message}"
            }
        }
    }

    fun messageShown() {
        _message.value = "" // Limpia el mensaje después de mostrarlo
    }
}