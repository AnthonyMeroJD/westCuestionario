package com.example.westcuestionario

data class Pregunta(
    val pregunta:String,
    val opciones:ArrayList<HashMap<String, String>>,
    val respuesta:String
)