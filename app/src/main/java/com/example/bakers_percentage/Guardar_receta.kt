package com.example.bakers_percentage

import com.google.android.material.snackbar.Snackbar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Guardar_receta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_guardar_receta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val atras= findViewById<Button>(R.id.atras)
        val addboton= findViewById<Button>(R.id.add_button)
        val contenedoringredientes= findViewById<LinearLayout>(R.id.contenedor_ingradientes)
        atras.setOnClickListener{finish()}
        addboton.setOnClickListener{
            addingradiente(contenedoringredientes)
        }
    }

    private fun addingradiente(contendor:LinearLayout){
        val elementos_internos =contendor.childCount
        if(elementos_internos<10) {
            val inflater = LayoutInflater.from(this)
            val nuevoingrediente = inflater.inflate(R.layout.elemento_ingrediente, contendor, false)
            contendor.addView(nuevoingrediente, elementos_internos - 1)
        }else{
            val rootView: View = findViewById(android.R.id.content)
            Snackbar.make(rootView, "Too many ingredients", Snackbar.LENGTH_SHORT)
                .show()
        }
        }

}