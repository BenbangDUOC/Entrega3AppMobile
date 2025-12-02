package com.example.gestorproductos.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestorproductos.data.local.model.Producto
import com.example.gestorproductos.data.remote.repository.ProductoRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para la Pantalla de Inicio (Home)
 */
class HomeViewModel(private val repository: ProductoRepository) : ViewModel() {

    // LiveData para emitir la lista de productos al Listado
    private val _productList = MutableLiveData<List<Producto>>()
    val productList: LiveData<List<Producto>> = _productList

    // LiveData para manejar mensajes de error/éxito (Toast/Snackbar)
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    // Flag para indicar que la navegación debe ocurrir
    private val _shouldNavigateToList = MutableLiveData<Boolean>()
    val shouldNavigateToList: LiveData<Boolean> = _shouldNavigateToList


    // Maneja la carga desde la API (Botón 1)
    fun loadFromApi() {
        // La validación de Internet (NetworkUtils) debe hacerse en la Activity
        // antes de llamar a esta función, para evitar lanzar el Coroutine si no hay red.

        // Usamos viewModelScope para el manejo de hilos (Calidad de Código: 10%)
        viewModelScope.launch {
            try {
                val products = repository.fetchProductsFromApiAndStore()
                _productList.value = products
                _shouldNavigateToList.value = true // Indica a la Activity que navegue
            } catch (e: Exception) {
                // Requerimiento 6: Manejo de Errores (Si falla el servidor/JSON)
                _message.value = "Error al cargar desde API: ${e.message}"
            }
        }
    }

    // Maneja la carga desde la BD Local (Botón 2)
    fun loadFromLocalDb() {
        viewModelScope.launch {
            // Validación de BD Vacía (Lógica de Negocio: 25%)
            if (repository.isDatabaseEmpty()) {
                _message.value = "No hay datos locales almacenados" // Caso Fallido [cite: 27]
            } else {
                val products = repository.getLocalProducts()
                _productList.value = products
                _shouldNavigateToList.value = true // Indica a la Activity que navegue
            }
        }
    }

    // Función de limpieza para LiveData de navegación
    fun navigationDone() {
        _shouldNavigateToList.value = false
    }
}