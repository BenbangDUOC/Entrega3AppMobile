package com.example.gestorproductos.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestorproductos.data.remote.repository.ProductoRepository
import com.example.gestorproductos.ui.list.ListadoViewModel // Importa esto

class HomeViewModelFactory(private val repository: ProductoRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Opción 1: HomeViewModel
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        // Opción 2: ListadoViewModel (AGREGA ESTO)
        if (modelClass.isAssignableFrom(ListadoViewModel::class.java)) {
            return ListadoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}