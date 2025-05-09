package com.example.bakers_percentage

import android.content.Context
import com.google.android.material.snackbar.Snackbar
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import kotlinx.coroutines.withContext

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
        val guardar= findViewById<Button>(R.id.guardar_receta)
        val nombrereceta= findViewById<EditText>(R.id.nombre_receta)
        val contenedoringredientes= findViewById<LinearLayout>(R.id.contenedor_ingradientes)
        atras.setOnClickListener{finish()}
        addboton.setOnClickListener{
            addingradiente(contenedoringredientes)
        }
        guardar.setOnClickListener{guardarreceta(contenedoringredientes,nombrereceta.text.toString())}
    }

    private fun addingradiente(contendor:LinearLayout){
        val elementos_internos =contendor.childCount
        if(elementos_internos<10) {
            val inflater = LayoutInflater.from(this)
            val nuevoingrediente = inflater.inflate(R.layout.elemento_ingrediente, contendor, false)
            contendor.addView(nuevoingrediente, elementos_internos - 1)
        }else{
            val rootView: View = findViewById(android.R.id.content)
            Toast.makeText(this,"Too many ingredients",Toast.LENGTH_SHORT).show()
        }
        }

    private fun guardarreceta(contendor:LinearLayout,nombrereceta:String){//GUARDAR ELEMENTOS EN MEMORIA
        val ingredeintes= mutableMapOf<String,Int>()
        if(nombrereceta.isEmpty()){
            Toast.makeText(this, "Please add a name to the recipe", Toast.LENGTH_SHORT).show()
            return
        }

        for (i in 1 until contendor.childCount-1){
            val ingrediente= contendor.getChildAt(i)

            if(ingrediente is ConstraintLayout){
                val nombreingredienteEdTex= ingrediente.findViewById<EditText>(R.id.nombre_ingrediente)
                val porcentajeEdTex= ingrediente.findViewById<EditText>(R.id.porcentaje)
                val nombreingredeinte= nombreingredienteEdTex.text.trim().toString()
                val porcentaje= porcentajeEdTex.text.trim().toString().toIntOrNull()
                Log.e("porcente",nombreingredeinte.toString())
                if(nombreingredeinte.isNotEmpty() && porcentaje!=null){
                    ingredeintes[nombreingredeinte]=porcentaje

                }
                else{
                    Toast.makeText(this, "Please fill all the ingredients before saving the recipe", Toast.LENGTH_SHORT).show()
                    return
                }
            }
        }

        val receta= recetajson(nombre = nombrereceta, ingredientes = ingredeintes)
        val gson = Gson()
        val jsonreceta = gson.toJson(receta)
        try {
            val nombrearchivo = "$nombrereceta.json"
            openFileOutput(nombrearchivo, MODE_PRIVATE).use { output ->
                output.write(jsonreceta.toByteArray())
            }
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, "Error saving the recipe", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }

    }


}