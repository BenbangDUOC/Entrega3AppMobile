package com.example.gestorproductos.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.gestorproductos.R
import com.example.gestorproductos.data.local.database.AppDatabase
import com.example.gestorproductos.data.remote.api.RetrofitClient
import com.example.gestorproductos.data.remote.repository.ProductoRepository
import com.example.gestorproductos.ui.list.ListadoActivity
import com.example.gestorproductos.utils.NetworkUtils
import com.google.gson.Gson

/**
 * Pantalla de Inicio (Home) - Punto A de los Requerimientos Funcionales
 */
class HomeActivity : AppCompatActivity() {

    // Inicialización de componentes de la capa de datos
    private val dao by lazy { AppDatabase.getDatabase(applicationContext).productoDao() }
    private val apiService by lazy { RetrofitClient.apiService }
    private val repository by lazy { ProductoRepository(apiService, dao) }

    // Inicialización del ViewModel usando el Factory
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Reemplaza con el nombre de tu layout XML
        setContentView(R.layout.activity_home)

        setupViews()
        setupObservers()
    }

    private fun setupViews() {
        val btnLoadApi: Button = findViewById(R.id.btn_load_api)
        val btnLoadLocal: Button = findViewById(R.id.btn_load_local)

        // Lógica del Botón 1: Cargar desde Rest API
        btnLoadApi.setOnClickListener {
            // Validación: Verificar conexión a Internet (Lógica de Negocio: 25%)
            if (NetworkUtils.isInternetAvailable(this)) {
                // Caso Exitoso: Llama al ViewModel para cargar y guardar
                viewModel.loadFromApi()
            } else {
                // Caso Fallido: Mostrar mensaje (Toast/Snackbar)
                Toast.makeText(this, "Sin conexión a Internet", Toast.LENGTH_SHORT).show()
            }
        }

        // Lógica del Botón 2: Cargar desde Base de Datos Local
        btnLoadLocal.setOnClickListener {
            // La validación de BD Vacía se hace dentro del ViewModel (Lógica de Negocio: 25%)
            viewModel.loadFromLocalDb()
        }
    }

    private fun setupObservers() {
        // Observa la lista de productos para navegar
        viewModel.productList.observe(this, Observer { products ->
            // Solo navegar si el ViewModel lo indica (después de una carga exitosa)
            if (viewModel.shouldNavigateToList.value == true) {
                // Navegación a la Pantalla de Listado
                navigateToProductList(products)
                viewModel.navigationDone() // Resetea el flag
            }
        })

        // Observa los mensajes de error/validación (Casos Fallidos)
        viewModel.message.observe(this, Observer { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
        })
    }

    // Función para navegar a la ListadoActivity
    private fun navigateToProductList(products: List<com.example.gestorproductos.data.local.model.Producto>) {
        val intent = Intent(this, ListadoActivity::class.java).apply {
            // Pasamos los datos como JSON string para evitar límites de Intent (serialización/parcelable)
            val productsJson = Gson().toJson(products)
            putExtra(ListadoActivity.EXTRA_PRODUCTS_JSON, productsJson)
        }
        startActivity(intent)
    }
}