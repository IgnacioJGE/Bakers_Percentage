package com.example.bakers_percentage

data class recetajson(
    val nombre: String,
    val ingredientes: MutableMap<String, Int>
)