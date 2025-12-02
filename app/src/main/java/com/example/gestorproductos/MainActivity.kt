package com.example.gestorproductos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gestorproductos.ui.home.HomeActivity

/**
 * Activity Principal de la aplicación.
 * * NOTA: Actúa como un simple redirect a HomeActivity, que es el verdadero
 * punto de inicio del proyecto híbrido.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Redirigir inmediatamente a HomeActivity
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)

        // Finalizar esta actividad para que el usuario no pueda volver a ella con el botón "Atrás"
        finish()
    }
}