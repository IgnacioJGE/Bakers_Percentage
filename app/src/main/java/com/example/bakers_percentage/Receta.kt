package com.example.bakers_percentage

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import java.io.File
import kotlin.time.times


class Receta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_receta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nombrereceta= intent.getStringExtra("id_receta")
        val nombreenpantalla= findViewById<TextView>(R.id.nombre_receta)
        val botonatras = findViewById<Button>(R.id.atras)
        val botonedditar = findViewById<Button>(R.id.editar_receta)
        val contenedoringredientes= findViewById<LinearLayout>(R.id.contenedor_ingredientes)
        val pesototal= findViewById<EditText>(R.id.peso_total)

        if (nombrereceta!=null) {
            val objetoreceta:recetajson = generaringredientes(nombrereceta)
            var sumatorioporcentajes = 0
            for (i in 0 until objetoreceta.ingredientes.size){
                sumatorioporcentajes += objetoreceta.ingredientes.values.toList().get(i)
            }
            pesototal.setOnEditorActionListener{ _, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                    cambiarpesos(contenedoringredientes,objetoreceta,pesototal.text.toString().toInt(),sumatorioporcentajes)
                    true
                }else  false
            }
            rellenarscrollview(contenedoringredientes,objetoreceta,pesototal.text.toString().toInt(),sumatorioporcentajes)//para rellenarel scrollview de ingredientes
            botonedditar.setOnClickListener { editarreceta(nombrereceta) }// para el botn de editar
            botonatras.setOnClickListener { finish() }//para el boton de salir
            nombreenpantalla.text = nombrereceta
        }
    }
    private fun editarreceta(nombre:String){
        val intent = Intent(this, Editar_receta::class.java)
        intent.putExtra("id_receta",nombre)
        startActivity(intent)
    }
    private fun generaringredientes (nombre: String):recetajson{
        val gson = Gson()
        val jsonreceta = File(this.filesDir, nombre+".json")
        val objetoreceta= gson.fromJson(jsonreceta.readText(),recetajson::class.java)
        return objetoreceta
    }
    private fun rellenarscrollview(vista:LinearLayout,objetoreceta:recetajson,pesototal:Int,sumatorioporcentajes:Int){
        val inflater = LayoutInflater.from(this)

        for (i in 0 until objetoreceta.ingredientes.size){
            val ingredeintereceta = inflater.inflate(R.layout.ingrediente, vista, false)
            val nombreingredieente=ingredeintereceta.findViewById<TextView>(R.id.nombre_ingrediente)
            val pesoingrediente=ingredeintereceta.findViewById<TextView>(R.id.peso_ingrediente)
            nombreingredieente.text= objetoreceta.ingredientes.keys.toList().get(i)
            val pesoraw=((pesototal/sumatorioporcentajes)*(objetoreceta.ingredientes.values.toList().get(i).toString().toFloat())).toString()
            if (pesoraw.endsWith(".0")){
                pesoingrediente.text= pesoraw.toFloat().toInt().toString()
            }else pesoingrediente.text= pesoraw

            vista.addView(ingredeintereceta)
        }
    }
    private fun cambiarpesos(vista:LinearLayout,objetoreceta:recetajson,pesototal:Int,sumatorioporcentajes: Int){
        for (i in 0 until vista.childCount){
            val viewingrediente = vista.getChildAt(i)
            val textviewpeso= viewingrediente.findViewById<TextView>(R.id.peso_ingrediente)
            val pesoraw=((pesototal.toFloat()/sumatorioporcentajes.toFloat())*(objetoreceta.ingredientes.values.toList().get(i).toString().toFloat()))
            val pesostring = String.format("%.2f",pesoraw.toFloat())
            if (pesostring.endsWith(".00")){
                textviewpeso.text= pesostring.toFloat().toInt().toString()
            }else textviewpeso.text= pesostring



        }

    }

}