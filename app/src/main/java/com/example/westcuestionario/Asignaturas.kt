package com.example.westcuestionario

enum class Asignaturas {
    DEFENSAPERSONAL{
        override fun nombreAsignatura(): String="DefensaPersonal"
    },
    DESARROLLOHUMANO{
        override fun nombreAsignatura(): String="DesarrolloHumano"
    },
    LEGAL{
        override fun nombreAsignatura(): String="Legal"
    },
    PRIMEROSAUXILIOS{
        override fun nombreAsignatura(): String="PrimerosAuxilios"
    },
    REENTRENAMIENTO{
        override fun nombreAsignatura(): String="Rentrenamiento"
    },
    RIESGOS{
        override fun nombreAsignatura(): String="Riesgos"
    },
    SEGURIDADCIUDADANA{
        override fun nombreAsignatura(): String="SeguridadCiudadana"
    };




    abstract fun nombreAsignatura():String
}