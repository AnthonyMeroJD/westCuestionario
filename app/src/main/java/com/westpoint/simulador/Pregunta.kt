package com.westpoint.simulador

data class Pregunta(
    var opciones: MutableMap<String, String>? = HashMap(),
    var pregunta: String = "def",
    var respuesta: String = "def"

)