package com.example.westcuestionario

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

import java.nio.file.Path
import java.util.*


class MainActivity : AppCompatActivity() {

   /*layouts del view*/
    private lateinit var rentrenamiento:LinearLayout
    private lateinit var desarrolloHumano:LinearLayout
    private lateinit var defensaPersonal:LinearLayout
    private lateinit var seguridadCiudadana:LinearLayout
    private lateinit var legal:LinearLayout
    private lateinit var riesgos:LinearLayout
    private lateinit var primerosAuxilios:LinearLayout
    /*intent*/


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rentrenamiento=mainRentrenamiento
        defensaPersonal=mainDefensaPersonal
        desarrolloHumano=mainDesarrolloHumano
        seguridadCiudadana=mainSeguridadCiudadana
        legal=mainLegal
        riesgos=mainRiesgos
        primerosAuxilios=mainPrimeroAuxilios


    }

    override fun onStart() {
        var intent:Intent
        super.onStart()
        rentrenamiento.setOnClickListener(){
            intent=Intent(this.applicationContext,CuestionarioActivity::class.java)
            var key=Asignaturas.REENTRENAMIENTO.name
            intent.putExtra(key,Asignaturas.REENTRENAMIENTO.nombreAsignatura())
            startActivity(intent)
        }
        defensaPersonal.setOnClickListener(){
            intent=Intent(this.applicationContext,CuestionarioActivity::class.java)
            var key=Asignaturas.DEFENSAPERSONAL.name
            intent.putExtra(key,Asignaturas.REENTRENAMIENTO.nombreAsignatura())
            startActivity(intent)
        }
        desarrolloHumano.setOnClickListener(){
            intent=Intent(this.applicationContext,CuestionarioActivity::class.java)
            var key=Asignaturas.DESARROLLOHUMANO.name
            intent.putExtra(key,Asignaturas.DESARROLLOHUMANO.nombreAsignatura())
            startActivity(intent)
        }
        seguridadCiudadana.setOnClickListener(){
            intent=Intent(this.applicationContext,CuestionarioActivity::class.java)
            var key=Asignaturas.SEGURIDADCIUDADANA.name
            intent.putExtra(key,Asignaturas.SEGURIDADCIUDADANA.nombreAsignatura())
            startActivity(intent)
        }
        legal.setOnClickListener(){
            intent=Intent(this.applicationContext,CuestionarioActivity::class.java)
            var key=Asignaturas.LEGAL.name
            intent.putExtra(key,Asignaturas.LEGAL.nombreAsignatura())
            startActivity(intent)
        }
        riesgos.setOnClickListener(){
            intent=Intent(this.applicationContext,CuestionarioActivity::class.java)
            var key=Asignaturas.RIESGOS.name
            intent.putExtra(key,Asignaturas.RIESGOS.nombreAsignatura())
            startActivity(intent)
        }
        primerosAuxilios.setOnClickListener(){
            intent=Intent(this.applicationContext,CuestionarioActivity::class.java)
            var key=Asignaturas.PRIMEROSAUXILIOS.name
            intent.putExtra(key,Asignaturas.PRIMEROSAUXILIOS.nombreAsignatura())
            startActivity(intent)
        }


    }



}