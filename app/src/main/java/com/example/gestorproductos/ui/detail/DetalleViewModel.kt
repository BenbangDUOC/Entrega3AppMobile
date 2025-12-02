package com.example.gestorproductos.ui.detail

import androidx.lifecycle.ViewModel

/**
 * ViewModel para la Pantalla de Detalle (C).
 *
 * Esta clase se mantiene por consistencia arquitectónica (MVVM), pero no
 * implementa lógica compleja ya que los datos son pasados directamente
 * desde ListadoActivity.
 */
class DetalleViewModel : ViewModel() {
    // No se necesita lógica aquí porque la DetalleActivity simplemente muestra
    // el objeto Producto que recibió a través del Intent.

    // Si en el futuro necesitaras operaciones asíncronas o manejo de estado
    // específico para esta pantalla (ej. guardar como favorito), la lógica
    // y los coroutines se añadirían aquí.
}