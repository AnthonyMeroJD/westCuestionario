package com.westpoint.simulador

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.contains
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.fondo_preguntas.*
import kotlinx.android.synthetic.main.tarjeta_preguntas.view.*
import kotlinx.android.synthetic.main.tarjeta_preguntas.view.cVPreguntas


class CuestionarioActivity() : AppCompatActivity() {
    /*rv*/
    private lateinit var rv: RecyclerView

    /*dbReference*/
    private lateinit var preguntasRef: DatabaseReference
    private  lateinit var qry:Query
    //adaptadores para recyclerview
    lateinit var opcion: FirebaseRecyclerOptions<Pregunta>
    lateinit var adapter: FirebaseRecyclerAdapter<Pregunta, PreguntasViewHolder>
    lateinit var preg: HashMap<Int, ArrayList<String>>
    lateinit var opcionesPreguntas: ArrayList<Pregunta>

    /*RespuestasChoice*/
    private lateinit var respuestasChoice: HashMap<Int, String>

    /*boolean checked*/
    private lateinit var mCheckedA: BooleanArray
    private lateinit var mCheckedB: BooleanArray
    private lateinit var mCheckedC: BooleanArray
    private lateinit var mCheckedD: BooleanArray
    private lateinit var mCheckedE: BooleanArray
    private lateinit var mCheckedF: BooleanArray

    /**/
    private var respuestasShuffleP = HashMap<Int, ArrayList<String>>()
    private var c = 0
    private var viewHolderCorrectas =ArrayList<Int>()
    private var viewHolder= HashMap<Int, PreguntasViewHolder>()
    private var viewHolderIncorrectas =ArrayList<Int>()

    /**/
    override fun onCreate(savedInstanceState: Bundle?) {
        opcionesPreguntas = ArrayList()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fondo_preguntas)
        supportActionBar?.hide()
        var i = intent
        var b: Bundle? = i.extras

        b?.let {
            referencia(it)?.let {
                preguntasRef = it
            }
        }
        rv = rvPreguntas
        /*manager rv*/


        opcionesPreguntas.size
        mCheckedA = BooleanArray(20)
        mCheckedB = BooleanArray(20)
        mCheckedC = BooleanArray(20)
        mCheckedD = BooleanArray(20)
        mCheckedE = BooleanArray(20)
        mCheckedF = BooleanArray(20)
        respuestasChoice = HashMap()
        qry=preguntasRef.limitToFirst(20)
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
            referencia = FirebaseDatabase.getInstance().reference.child("preguntas").child(asignatura)

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
    *@param opcion:String
    * @param respuesta:String
    * @param position:Int
    * @param literal:String
     */
    fun literalRespuesta(opcion: String, respuesta: String, posicion: Int, literal: String) {
        if (opcion.equals(respuesta)) {
            respuestasChoice.put(posicion, literal)
        }
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
        val layout = view.cVPreguntas

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

                var pregunta = model.pregunta
                var respuesta = model.respuesta
                var rg = holder.radGroup
                var opciones = model.opciones!!.values
                var opcionesCompletas = ArrayList<String>()
                var op1 = holder.opcion1
                var op2 = holder.opcion2
                var op3 = holder.opcion3
                var op4 = holder.opcion4
                var op5 = holder.opcion5
                var op6 = holder.opcion6
                var btns = ArrayList<RadioButton>()
                holder.layout.setCardBackgroundColor(resources.getColor(R.color.blanco))
                viewHolderCorrectas?.let {
                    if(it.size>0){
                        for (n in it){
                            if (position.equals(n)){
                                holder.layout.setCardBackgroundColor(resources.getColor(R.color.verdeClaro))
                            }
                        }
                    }
                }
                viewHolderIncorrectas?.let {
                    if(it.size>0){
                        for (n in it){
                            if (position.equals(n)){
                                holder.layout.setCardBackgroundColor(resources.getColor(R.color.rojo))
                            }
                        }
                    }
                }

                btns.add(op1)
                btns.add(op2)
                btns.add(op3)
                btns.add(op4)
                btns.add(op5)
                btns.add(op6)
                rg.clearCheck()
                holder.tituloPregunta.text = "${position + 1})${pregunta}"
                fun chequear(
                    mchecked: BooleanArray,
                    posicion: Int,
                    btns: ArrayList<RadioButton>,
                    rg: RadioGroup,
                    literal: String
                ) {
                    if (mchecked[posicion]) {
                        when (literal) {
                            "A" -> {
                                var i = 0
                                for (btn in btns) {
                                    if (i == 0) {
                                        btn.isChecked = true
                                    } else {
                                        if (rg.contains(btn)) {
                                            btn.isChecked = false
                                        }
                                    }
                                    i++
                                }
                            }
                            "B" -> {
                                var i = 0
                                for (btn in btns) {
                                    if (i == 1) {
                                        btn.isChecked = true
                                    } else {
                                        if (rg.contains(btn)) {
                                            btn.isChecked = false
                                        }
                                    }
                                    i++
                                }

                            }
                            "C" -> {
                                var i = 0
                                for (btn in btns) {
                                    if (i == 2) {
                                        btn.isChecked = true
                                    } else {
                                        if (rg.contains(btn)) {
                                            btn.isChecked = false
                                        }
                                    }
                                    i++
                                }

                            }
                            "D" -> {
                                var i = 0
                                for (btn in btns) {
                                    if (i == 3) {
                                        btn.isChecked = true
                                    } else {
                                        if (rg.contains(btn)) {
                                            btn.isChecked = false
                                        }
                                    }
                                    i++
                                }
                            }
                            "E" -> {
                                var i = 0
                                for (btn in btns) {
                                    if (i == 4) {
                                        btn.isChecked = true
                                    } else {
                                        if (rg.contains(btn)) {
                                            btn.isChecked = false
                                        }
                                    }
                                    i++
                                }
                            }
                            "F" -> {
                                var i = 0
                                for (btn in btns) {
                                    if (i == 5) {
                                        btn.isChecked = true
                                    } else {
                                        if (rg.contains(btn)) {
                                            btn.isChecked = false
                                        }
                                    }
                                    i++
                                }
                            }
                        }
                    }
                }

                for (opcion in opciones) {
                    opcionesCompletas.add(opcion)
                }
                opcionesCompletas.add(respuesta)
                if (c < 20) {
                    var preguntasShuffle = opcionesCompletas.toMutableList()
                    preguntasShuffle.shuffle()
                    var lista = ArrayList(preguntasShuffle)
                    respuestasShuffleP.put(position, lista)
                    viewHolder.put(position, holder)
                    c++
                }

                var opcionesSize = opcionesCompletas.size
                var i = 0
                respuestasShuffleP[position]?.let {
                    for (opcion in it) {
                        if (opcionesSize == 2) {
                            when (i) {
                                0 -> {
                                    op1.text = opcion
                                    literalRespuesta(opcion, respuesta, position, "A")
                                }
                                1 -> {
                                    op2.text = opcion
                                    literalRespuesta(opcion, respuesta, position, "B")
                                }
                            }
                            rg.removeView(op3)
                            rg.removeView(op4)
                            rg.removeView(op5)
                            rg.removeView(op6)
                        }
                        if (opcionesSize == 3) {
                            when (i) {
                                0 -> {
                                    op1.text = opcion
                                    literalRespuesta(opcion, respuesta, position, "A")
                                }
                                1 -> {
                                    op2.text = opcion
                                    literalRespuesta(opcion, respuesta, position, "B")
                                }
                                2 -> {
                                    if (!rg.contains(op3)) {
                                        rg.addView(op3)
                                        op3.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "C")

                                    } else {
                                        op3.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "C")
                                    }
                                }
                            }
                            rg.removeView(op4)
                            rg.removeView(op5)
                            rg.removeView(op6)
                        }
                        if (opcionesSize == 4) {
                            when (i) {
                                0 -> {
                                    op1.text = opcion
                                    literalRespuesta(opcion, respuesta, position, "A")
                                }
                                1 -> {
                                    op2.text = opcion
                                    literalRespuesta(opcion, respuesta, position, "B")
                                }
                                2 -> {
                                    if (!rg.contains(op3)) {
                                        rg.addView(op3)
                                        op3.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "C")

                                    } else {
                                        op3.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "C")
                                    }
                                }
                                3 -> {
                                    if (!rg.contains(op4)) {
                                        rg.addView(op4)
                                        op4.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "D")
                                    } else {
                                        op4.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "D")
                                    }
                                }
                            }

                            rg.removeView(op5)
                            rg.removeView(op6)
                        }
                        if (opcionesSize == 5) {
                            when (i) {
                                0 -> {
                                    op1.text = opcion
                                    literalRespuesta(opcion, respuesta, position, "A")
                                }
                                1 -> {
                                    op2.text = opcion
                                    literalRespuesta(opcion, respuesta, position, "B")
                                }
                                2 -> {
                                    if (!rg.contains(op3)) {
                                        rg.addView(op3)
                                        op3.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "C")
                                    } else {
                                        op3.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "C")
                                    }
                                }
                                3 -> {
                                    if (!rg.contains(op4)) {
                                        rg.addView(op4)
                                        op4.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "D")
                                    } else {
                                        op4.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "D")
                                    }
                                }
                                4 -> {
                                    if (!rg.contains(op5)) {
                                        rg.addView(op5)
                                        op5.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "E")
                                    } else {
                                        op5.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "E")
                                    }
                                }
                            }
                            rg.removeView(op6)
                        }
                        if (opcionesSize == 6) {
                            when (i) {
                                0 -> {
                                    op1.text = opcion
                                    literalRespuesta(opcion, respuesta, position, "A")
                                }
                                1 -> {
                                    op2.text = opcion
                                    literalRespuesta(opcion, respuesta, position, "B")
                                }
                                2 -> {
                                    if (!rg.contains(op3)) {
                                        rg.addView(op3)
                                        op3.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "C")

                                    } else {
                                        op3.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "C")
                                    }
                                }
                                3 -> {
                                    if (!rg.contains(op4)) {
                                        rg.addView(op4)
                                        op4.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "D")
                                    } else {
                                        op4.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "D")
                                    }
                                }
                                4 -> {
                                    if (!rg.contains(op5)) {
                                        rg.addView(op5)
                                        op5.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "E")
                                    } else {
                                        op5.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "E")
                                    }
                                }
                                5 -> {
                                    if (!rg.contains(op6)) {
                                        rg.addView(op6)
                                        op6.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "F")
                                    } else {
                                        op6.text = opcion
                                        literalRespuesta(opcion, respuesta, position, "F")
                                    }
                                }
                            }
                        }
                        i++
                    }
                }

                /*opcion 1*/
                chequear(mCheckedA, position, btns, rg, "A")
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
                chequear(mCheckedB, position, btns, rg, "B")
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
                    chequear(mCheckedC, position, btns, rg, "C")
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
                    chequear(mCheckedD, position, btns, rg, "D")
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
                    chequear(mCheckedE, position, btns, rg, "E")
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
                    chequear(mCheckedF, position, btns, rg, "F")
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
        btnCalificar.setOnClickListener {

            var correctas = ArrayList<Int>()
            var inCorrectas = ArrayList<Int>()
            var elegidas = HashMap<Int, String>()

            var i = 0
            for (i in mCheckedA.indices) {
                if (mCheckedA.get(i)) {
                    elegidas.put(i.toInt(), "A")
                }
                if (mCheckedB.get(i)) {
                    elegidas.put(i.toInt(), "B")
                }
                if (mCheckedC.get(i)) {
                    elegidas.put(i.toInt(), "C")
                }
                if (mCheckedD.get(i)) {
                    elegidas.put(i.toInt(), "D")
                }
                if (mCheckedE.get(i)) {
                    elegidas.put(i.toInt(), "E")
                }
                if (mCheckedF.get(i)) {
                    elegidas.put(i.toInt(), "F")
                }

            }

            for (respuesta in elegidas.keys) {
                if (respuestasChoice[respuesta].equals(elegidas[respuesta])) {
                    correctas.add(respuesta)
                    i++
                }else{
                    inCorrectas.add(respuesta)
                }
            }
            for (correcta in correctas){
                viewHolder[correcta]?.let {
                    it.layout.setCardBackgroundColor(resources.getColor(R.color.verdeClaro))
                }
                viewHolderCorrectas.add(correcta)
            }
            for (incorrecta in inCorrectas){
                viewHolder[incorrecta]?.let {
                    it.layout.setCardBackgroundColor(resources.getColor(R.color.rojo))
                }
                viewHolderIncorrectas.add(incorrecta)
            }
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(this)

            builder.setMessage(
                "Su nota es:${i}/20"
            ).setPositiveButton("Reintentar",
                DialogInterface.OnClickListener { dialog, id ->
                    val intent = intent
                    finish()
                    startActivity(intent)
                })
                .setNegativeButton("Regresar inicio",
                    DialogInterface.OnClickListener { dialog, id ->
                        val intent = Intent(this, MainActivity::class.java)
                        finish()
                        startActivity(intent)
                    })
            btnCalificar.isClickable = false
            // Create the AlertDialog object and return it
            builder.create()
            builder.show()


        }


    }


}


