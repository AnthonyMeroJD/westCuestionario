package com.example.westcuestionario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.contains
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fondo_preguntas.*
import kotlinx.android.synthetic.main.tarjeta_preguntas.view.*


class CuestionarioActivity() : AppCompatActivity() {
    /*rv*/
    private lateinit var rv: RecyclerView

    /*dbReference*/
    private lateinit var preguntasRef: DatabaseReference

    //adaptadores para recyclerview
    lateinit var opcion: FirebaseRecyclerOptions<Pregunta>
    lateinit var adapter: FirebaseRecyclerAdapter<Pregunta, PreguntasViewHolder>
    lateinit var preg: HashMap<Int, ArrayList<String>>
    lateinit var opcionesPreguntas: ArrayList<Pregunta>


    /*boolean checked*/
    private lateinit var mCheckedA: BooleanArray
    private lateinit var mCheckedB: BooleanArray
    private lateinit var mCheckedC: BooleanArray
    private lateinit var mCheckedD: BooleanArray
    private lateinit var mCheckedE: BooleanArray
    private lateinit var mCheckedF: BooleanArray

    override fun onCreate(savedInstanceState: Bundle?) {
        opcionesPreguntas = ArrayList()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fondo_preguntas)
        var i = intent
        var b: Bundle? = i.extras

        b?.let {
            referencia(it)?.let {
                preguntasRef = it
            }
        }
        rv = rvPreguntas
        /*manager rv*/
        val qry = preguntasRef.limitToFirst(20)
        opcionesPreguntas.size
        mCheckedA = BooleanArray(20)
        mCheckedB = BooleanArray(20)
        mCheckedC = BooleanArray(20)
        mCheckedD = BooleanArray(20)
        mCheckedE = BooleanArray(20)
        mCheckedF = BooleanArray(20)
        opcion = FirebaseRecyclerOptions.Builder<Pregunta>().setQuery(
            qry, Pregunta::class.java
        ).build()


    }

    /*
    * @param Bundle
    * @return DatabaseReference
    * retorna la referencia dependiendo del key del bundle
    * */
    fun referencia(b: Bundle): DatabaseReference? {
        var asignatura: String
        var referencia: DatabaseReference? = null
        if (b.getString(Asignaturas.REENTRENAMIENTO.name) != null) {
            asignatura = Asignaturas.REENTRENAMIENTO.nombreAsignatura()
            referencia =
                FirebaseDatabase.getInstance().reference.child("preguntas").child(asignatura)
        }
        if (b.getString(Asignaturas.PRIMEROSAUXILIOS.name) != null) {
            asignatura = Asignaturas.PRIMEROSAUXILIOS.nombreAsignatura()
            referencia =
                FirebaseDatabase.getInstance().reference.child("preguntas").child(asignatura)
        }
        if (b.getString(Asignaturas.RIESGOS.name) != null) {
            asignatura = Asignaturas.RIESGOS.nombreAsignatura()
            referencia =
                FirebaseDatabase.getInstance().reference.child("preguntas").child(asignatura)
        }
        if (b.getString(Asignaturas.LEGAL.name) != null) {
            asignatura = Asignaturas.LEGAL.nombreAsignatura()
            referencia =
                FirebaseDatabase.getInstance().reference.child("preguntas").child(asignatura)
        }
        if (b.getString(Asignaturas.SEGURIDADCIUDADANA.name) != null) {
            asignatura = Asignaturas.SEGURIDADCIUDADANA.nombreAsignatura()
            referencia =
                FirebaseDatabase.getInstance().reference.child("preguntas").child(asignatura)
        }
        if (b.getString(Asignaturas.DEFENSAPERSONAL.name) != null) {
            asignatura = Asignaturas.DEFENSAPERSONAL.nombreAsignatura()
            referencia =
                FirebaseDatabase.getInstance().reference.child("preguntas").child(asignatura)
        }
        if (b.getString(Asignaturas.DESARROLLOHUMANO.name) != null) {
            asignatura = Asignaturas.DESARROLLOHUMANO.nombreAsignatura()
            referencia =
                FirebaseDatabase.getInstance().reference.child("preguntas").child(asignatura)
        }

        return referencia
    }

    /*
    * @param View
    * @return ViewHolder
    * view holder para mi recycler
    * */
    inner class PreguntasViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tituloPregunta = view.titulo_preguntas
        val radGroup: RadioGroup = view.grupo_radio_button
        val opcion1: RadioButton = view.rBopcion1
        val opcion2: RadioButton = view.rBopcion2
        val opcion3: RadioButton = view.rBopcion3
        val opcion4: RadioButton = view.rBopcion4
        val opcion5: RadioButton = view.rBopcion5
        val opcion6: RadioButton = view.rBopcion6
        val layout = view.parentRg

    }

    override fun onStart() {
        super.onStart()


        adapter = object : FirebaseRecyclerAdapter<Pregunta, PreguntasViewHolder>(opcion) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntasViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.tarjeta_preguntas, parent, false)
                var vH = PreguntasViewHolder(view)

                return vH
            }


            override fun onBindViewHolder(
                holder: PreguntasViewHolder,
                position: Int,
                model: Pregunta
            ) {

                holder.tituloPregunta.text = model.pregunta


                var pregunta = model.pregunta
                var respuesta = model.respuesta
                var rg = holder.radGroup
                var opciones = model.opciones!!.values
                var opcionesCompletas = ArrayList<String>()
                holder.tituloPregunta.text = pregunta
                var op1 = holder.opcion1
                var op2 = holder.opcion2
                var op3 = holder.opcion3
                var op4 = holder.opcion4
                var op5 = holder.opcion5
                var op6 = holder.opcion6
                rg.clearCheck()
                var posit = position
                for (opcion in opciones) {
                    opcionesCompletas.add(opcion)
                }
                opcionesCompletas.add(respuesta)
                var shuffleOpciones = opcionesCompletas.toMutableList()
                shuffleOpciones.shuffle()
                var opcionesSize = opcionesCompletas.size
                var i = 0
                for (opcion in shuffleOpciones) {
                    if (opcionesSize == 2) {
                        when (i) {
                            0 -> op1.text = opcion
                            1 -> op2.text = opcion

                        }
                        rg.removeView(op4)
                        rg.removeView(op5)
                        rg.removeView(op6)
                    }
                    if (opcionesSize == 3) {
                        when (i) {
                            0 -> op1.text = opcion
                            1 -> op2.text = opcion
                            2 -> {
                                if (!rg.contains(op3)) {
                                    rg.addView(op3)
                                    op3.text = opcion
                                } else {
                                    op3.text = opcion
                                }
                            }
                        }
                        rg.removeView(op4)
                        rg.removeView(op5)
                        rg.removeView(op6)
                    }
                    if (opcionesSize == 4) {
                        when (i) {
                            0 -> op1.text = opcion
                            1 -> op2.text = opcion
                            2 -> {
                                if (!rg.contains(op3)) {
                                    rg.addView(op3)
                                    op3.text = opcion
                                } else {
                                    op3.text = opcion
                                }
                            }
                            3 -> {
                                if (!rg.contains(op4)) {
                                    rg.addView(op4)
                                    op4.text = opcion
                                } else {
                                    op4.text = opcion
                                }
                            }
                        }

                        rg.removeView(op5)
                        rg.removeView(op6)
                    }
                    if (opcionesSize == 5) {
                        when (i) {
                            0 -> op1.text = opcion
                            1 -> op2.text = opcion
                            2 -> {
                                if (!rg.contains(op3)) {
                                    rg.addView(op3)
                                    op3.text = opcion
                                } else {
                                    op3.text = opcion
                                }
                            }
                            3 -> {
                                if (!rg.contains(op4)) {
                                    rg.addView(op4)
                                    op4.text = opcion
                                } else {
                                    op4.text = opcion
                                }
                            }
                            4 -> {
                                if (!rg.contains(op5)) {
                                    rg.addView(op5)
                                    op5.text = opcion
                                } else {
                                    op5.text = opcion
                                }
                            }
                        }
                        rg.removeView(op6)
                    }
                    if (opcionesSize == 6) {
                        when (i) {
                            0 -> op1.text = opcion
                            1 -> op2.text = opcion
                            2 -> {
                                if (!rg.contains(op3)) {
                                    rg.addView(op3)
                                    op3.text = opcion
                                } else {
                                    op3.text = opcion
                                }
                            }
                            3 -> {
                                if (!rg.contains(op4)) {
                                    rg.addView(op4)
                                    op4.text = opcion
                                } else {
                                    op4.text = opcion
                                }
                            }
                            4 -> {
                                if (!rg.contains(op5)) {
                                    rg.addView(op5)
                                    op5.text = opcion
                                } else {
                                    op5.text = opcion
                                }
                            }
                            5 -> {
                                if (!rg.contains(op6)) {
                                    rg.addView(op6)
                                    op6.text = opcion
                                } else {
                                    op6.text = opcion
                                }
                            }
                        }
                    }
                    i++
                }
                /*opcion 1*/
                if (mCheckedA[position]) {
                    op1.isChecked = true
                    op2.isChecked = false
                    if (rg.contains(op3)) {
                        op3.isChecked = false
                    } else if (rg.contains(op4)) {
                        op4.isChecked = false
                    } else if (rg.contains(op5)) {
                        op5.isChecked = false
                    } else if (rg.contains(op6)) {
                        op6.isChecked = false
                    }
                } else {
                    op1.isChecked = false
                }
                op1.setOnClickListener {
                    var b = it as RadioButton
                    if (b.isChecked) {
                        mCheckedA[position] = true
                        mCheckedB[position] = false
                        if (rg.childCount > 2) {
                            mCheckedC[position] = false
                            mCheckedD[position] = false
                            mCheckedE[position] = false
                            mCheckedF[position] = false
                        }
                    } else {
                        mCheckedA[position] = false
                    }
                }
                /*opcion 2*/
                if (mCheckedB[position]) {
                    op1.isChecked = false
                    op2.isChecked = true
                    if (rg.contains(op3)) {
                        op3.isChecked = false
                    } else if (rg.contains(op4)) {
                        op4.isChecked = false
                    } else if (rg.contains(op5)) {
                        op5.isChecked = false
                    } else if (rg.contains(op6)) {
                        op6.isChecked = false
                    }
                } else {
                    op2.isChecked = false
                }
                op2.setOnClickListener {
                    var b = it as RadioButton
                    if (b.isChecked == true) {
                        mCheckedA[position] = false
                        mCheckedB[position] = true
                        if (rg.childCount > 2) {
                            mCheckedC[position] = false
                            mCheckedD[position] = false
                            mCheckedE[position] = false
                            mCheckedF[position] = false
                        }
                    } else {
                        mCheckedB[position] = false
                    }
                }
                /*OPCION 3*/
                if (rg.contains(op3)) {
                    if (mCheckedC[position]) {
                        op1.isChecked = false
                        op2.isChecked = false
                        op3.isChecked = true
                        if (rg.contains(op4)) {
                            op4.isChecked = false
                        } else if (rg.contains(op5)) {
                            op5.isChecked = false
                        } else if (rg.contains(op6)) {
                            op6.isChecked = false
                        }
                    } else {
                        op3.isChecked = false
                    }

                    op3.setOnClickListener {
                        var b = it as RadioButton
                        if (b.isChecked) {
                            mCheckedA[position] = false
                            mCheckedB[position] = false
                            mCheckedC[position] = true
                            if (rg.childCount > 3) {
                                mCheckedD[position] = false
                                mCheckedE[position] = false
                                mCheckedF[position] = false
                            }
                        } else {
                            mCheckedC[position] = false
                        }
                    }
                }
                /*OPCION 4*/
                if (rg.contains(op4)) {
                    if (mCheckedD[position]) {
                        op1.isChecked = false
                        op2.isChecked = false
                        op3.isChecked = false
                        op4.isChecked = true
                        if (rg.contains(op5)) {
                            op5.isChecked = false
                        } else if (rg.contains(op6)) {
                            op6.isChecked = false
                        }
                    } else {
                        op4.isChecked = false
                    }

                    op4.setOnClickListener {
                        var b = it as RadioButton
                        if (b.isChecked) {
                            mCheckedA[position] = false
                            mCheckedB[position] = false
                            mCheckedC[position] = false
                            mCheckedD[position] = true
                            if (rg.childCount > 4) {
                                mCheckedE[position] = false
                                mCheckedF[position] = false
                            }
                        } else {
                            mCheckedD[position] = false
                        }
                    }
                }
                /*OPCION 5*/
                if (rg.contains(op5)) {
                    if (mCheckedE[position]) {
                        op1.isChecked = false
                        op2.isChecked = false
                        op3.isChecked = false
                        op4.isChecked = false
                        op5.isChecked = true
                        if (rg.contains(op6)) {
                            op6.isChecked = false
                        }
                    } else {
                        op5.isChecked = false
                    }

                    op5.setOnClickListener {
                        var b = it as RadioButton
                        if (b.isChecked) {
                            mCheckedA[position] = false
                            mCheckedB[position] = false
                            mCheckedC[position] = false
                            mCheckedD[position] = false
                            mCheckedE[position] = true
                            if (rg.childCount > 5) {
                                mCheckedF[position] = false
                            }
                        } else {
                            mCheckedE[position] = false
                        }
                    }
                }
                /*OPCION 6*/
                if (rg.contains(op6)) {
                    if (mCheckedF[position]) {
                        op1.isChecked = false
                        op2.isChecked = false
                        op3.isChecked = false
                        op4.isChecked = false
                        op5.isChecked = false
                        op6.isChecked = true
                    } else {
                        op6.isChecked = false
                    }

                    op6.setOnClickListener {
                        var b = it as RadioButton
                        if (b.isChecked) {
                            mCheckedA[position] = false
                            mCheckedB[position] = false
                            mCheckedC[position] = false
                            mCheckedD[position] = false
                            mCheckedE[position] = false
                            mCheckedF[position] = true

                        } else {
                            mCheckedF[position] = false
                        }
                    }
                }

            }


        }
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)

        adapter.startListening()


    }


   

}

