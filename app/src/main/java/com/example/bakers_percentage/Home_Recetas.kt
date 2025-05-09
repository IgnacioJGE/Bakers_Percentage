package com.example.bakers_percentage

import android.content.Intent
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.app.AppCompatActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Home_Recetas : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home__recetas, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val botonaddreceta = view.findViewById<AppCompatButton>(R.id.add_receta)
        botonaddreceta.setOnClickListener {
            addreceta()
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home_Recetas().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onResume() {//pinta los botones con los nombres de los archivos 
        super.onResume()
        val LinLayRecetas= view?.findViewById<LinearLayout>(R.id.contenedor_recetas)
        LinLayRecetas?.removeAllViews()
        val directory = requireContext().filesDir
        val archivos = directory.listFiles { file -> file.extension == "json" }
        if (archivos!=null) {
            val inflater = LayoutInflater.from(requireContext())
            for (i in 0 until archivos.size) {//bucle con los archivos internos y le añade la logica al boton receta

                val nuevareceta = inflater.inflate(R.layout.receta_basica, LinLayRecetas, false)
                val nombrereceta = archivos[i].name.substringBeforeLast(".")
                val botnonombrereceta = nuevareceta.findViewById<Button>(R.id.nombre_receta)
                val botoneditar= nuevareceta.findViewById<Button>(R.id.edit_button)
                botoneditar.setOnClickListener(){ editarreceta(nombrereceta) }
                botnonombrereceta.setOnClickListener(){ irareceta(nombrereceta) }
                botnonombrereceta.text = nombrereceta
                LinLayRecetas?.addView(nuevareceta)

            }
        }
    }
    private fun irareceta(nombrereceta:String){
        val intent = Intent(requireContext(), Receta::class.java)
        intent.putExtra("id_receta",nombrereceta)
        startActivity(intent)
    }
    private fun editarreceta(nombrereceta:String){
        val intent = Intent(requireContext(), Editar_receta::class.java)
        intent.putExtra("id_receta",nombrereceta)
        startActivity(intent)
    }

    private fun addreceta(){//redirige a la pagina de guardar receta y compueba el numero de recetas
        val directory = requireContext().filesDir
        val archivos = directory.listFiles { file -> file.extension == "json" }

        if (archivos != null && archivos.size >=10) {
            Toast.makeText(requireContext(), "Sorry you have reached the limit of 10 recipes", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(requireContext(), Guardar_receta::class.java)
        startActivity(intent)
    }

}