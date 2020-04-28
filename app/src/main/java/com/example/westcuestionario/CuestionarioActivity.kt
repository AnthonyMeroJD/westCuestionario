package com.example.westcuestionario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.cuestionario.*

import kotlinx.android.synthetic.main.tarjeta_preguntas.view.*


class CuestionarioActivity : AppCompatActivity() {
    /*rv*/
    private lateinit var rv:RecyclerView
    /*dbReference*/
    private lateinit var preguntasRef:DatabaseReference
    //adaptadores para recyclerview
    lateinit var opcion: FirebaseRecyclerOptions<Pregunta>
    lateinit var adapter: FirebaseRecyclerAdapter<Pregunta, PreguntasViewHolder>

    inner class PreguntasViewHolder(view:View):RecyclerView.ViewHolder(view){
        val pregunta =view.titulo_preguntas


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fondo_preguntas)
        var i=intent
        var b:Bundle?=i.extras
        var asignatura:String
        b?.let {
              if (b.getString(Asignaturas.REENTRENAMIENTO.name)!=null){
                  asignatura=Asignaturas.REENTRENAMIENTO.nombreAsignatura()
                  preguntasRef=FirebaseDatabase.getInstance().reference.child("preguntas").child(asignatura)
              }
        }
        rv=rvPreguntas





    }

    override fun onStart() {
        super.onStart()
        /*manager rv*/
        rv.layoutManager=LinearLayoutManager(applicationContext)
        /*qey*/
        val qry=preguntasRef.limitToFirst(20)
        adapter=revLlenar(qry)

        rv.adapter=adapter

        adapter.startListening()

    }

    fun revLlenar(qry:Query):FirebaseRecyclerAdapter<Pregunta,PreguntasViewHolder>{
        opcion=FirebaseRecyclerOptions.Builder<Pregunta>().setQuery(
            qry,Pregunta::class.java).build()
        adapter=object:FirebaseRecyclerAdapter<Pregunta,PreguntasViewHolder>(opcion){
            private var chklst:Cheklist=Cheklist(null,false)

            private var lastPos: Int? = null
            private var checkBox: RadioButton? = null

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntasViewHolder {
                //inflate el layout
                val view:View= LayoutInflater.from(parent.context).inflate(R.layout.tarjeta_preguntas,parent,false)


                var vH=PreguntasViewHolder(view)

                return vH
            }

            override fun onBindViewHolder(
                holder: PreguntasViewHolder,
                position: Int,
                model: Pregunta
            ) {
                holder.pregunta.text=model.pregunta

                }
                /*

                var y =position
                var id=(y+1)*100
                var n=getItemId(position)

                holder.rgroup.removeAllViews()
                if (chklst.buttons!=null){
                    for (b in chklst.buttons!!){
                        if(b.id==100){
                            b.isChecked=true
                        }
                    }
                }
                if (holder.rgroup.isEmpty()){
                    var buttns=ArrayList<RadioButton>()
                    for (opciones in model.opciones!!.values){
                        var rb:RadioButton= RadioButton(this@CuestionarioActivity)
                        rb.text=opciones
                        rb.id=id++
                        buttns.add(rb)
                    }
                       chklst=cheklist(buttns,false)
                    for (b in chklst.buttons!!){
                        holder.rgroup.addView(b)
                        b.setOnClickListener{
                            chklst.isSelected=true
                            Toast.makeText(this@CuestionarioActivity,n.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }

                }*/



            }

        return adapter

        }

    }

