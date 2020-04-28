package com.example.westcuestionario

data class preguntas(
    var opciones:MutableMap<String,String>?= HashMap(),
    var pregunta:String="def",
    var respuesta:String="def"

)