package com.example.bakers_percentage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.bakers_percentage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cambiarfragmento(Home_Recetas())

        binding.navBar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.recetas -> cambiarfragmento(Home_Recetas())
                R.id.social -> cambiarfragmento(Social())
                R.id.ayuda -> cambiarfragmento(Settings())
                else -> {
                }
            }
            true
        }
    }

    private fun cambiarfragmento(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.marco_principal,fragment)
        fragmentTransaction.commit()

    }



}