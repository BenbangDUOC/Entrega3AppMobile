package com.example.gestorproductos.ui.list

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestorproductos.R
import com.example.gestorproductos.data.local.database.AppDatabase
import com.example.gestorproductos.data.local.model.Producto
import com.example.gestorproductos.data.remote.api.RetrofitClient
import com.example.gestorproductos.data.remote.repository.ProductoRepository
import com.example.gestorproductos.ui.detail.DetalleActivity
import com.example.gestorproductos.ui.home.HomeViewModelFactory // Necesario para inyectar el Repositorio
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Pantalla B: Listado de Productos (Utiliza RecyclerView)
 */
class ListadoActivity : AppCompatActivity() {

    // Clave estática para recibir los datos del Intent
    companion object {
        const val EXTRA_PRODUCTS_JSON = "extra_products_json"
        const val EXTRA_PRODUCT_DETAIL = "extra_product_detail"
    }

    private lateinit var productsAdapter: ListadoAdapter
    private var currentProducts: List<Producto> = emptyList()

    // Usamos el mismo ViewModelFactory ya que el ListadoViewModel también necesitará el Repositorio
    private val dao by lazy { AppDatabase.getDatabase(applicationContext).productoDao() }
    private val apiService by lazy { RetrofitClient.apiService }
    private val repository by lazy { ProductoRepository(apiService, dao) }
    private val viewModel: ListadoViewModel by viewModels {
        HomeViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)

        // 1. Recibir los datos del Intent de HomeActivity
        val productsJson = intent.getStringExtra(EXTRA_PRODUCTS_JSON)
        if (productsJson != null) {
            val listType = object : TypeToken<List<Producto>>() {}.type
            currentProducts = Gson().fromJson(productsJson, listType)
        }

        setupRecyclerView()
        setupSaveButton()
        setupObservers()

        // Iniciar la carga de la lista
        productsAdapter.updateProducts(currentProducts)
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_productos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Callback para el click en el ítem
        productsAdapter = ListadoAdapter(currentProducts) { producto ->
            // Navegación a la Pantalla de Detalle (Requisito Funcional: Navegación)
            navigateToDetail(producto)
        }
        recyclerView.adapter = productsAdapter
    }

    private fun setupSaveButton() {
        val fabSave: FloatingActionButton = findViewById(R.id.fab_save_local)

        // Botón "Almacenar en Base de Datos Local" (Requisito Funcional: Guardado)
        fabSave.setOnClickListener {
            if (currentProducts.isNotEmpty()) {
                // Llama al ViewModel para ejecutar la lógica de guardado en Room
                viewModel.saveProductsToLocalDb(currentProducts)
            } else {
                Toast.makeText(this, "No hay productos para guardar.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupObservers() {
        // Observa el mensaje de éxito o error del ViewModel (al guardar en BD)
        viewModel.message.observe(this) { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                viewModel.messageShown() // Limpia el mensaje
            }
        }
    }

    private fun navigateToDetail(producto: Producto) {
        val intent = Intent(this, DetalleActivity::class.java).apply {
            // Pasamos el producto completo como JSON (para DetalleActivity)
            val productJson = Gson().toJson(producto)
            putExtra(EXTRA_PRODUCT_DETAIL, productJson)
        }
        startActivity(intent)
    }
}