package com.example.westcuestionario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import kotlinx.android.synthetic.main.cuestionario_fragment.view.*
import kotlinx.android.synthetic.main.custompregn.view.*
import kotlinx.android.synthetic.main.pregunta.*


class cuestionario : Fragment() {

    /*recyclerView*/
    private lateinit var rv: RecyclerView
    /*referencia a la base*/
    private lateinit var database: DatabaseReference
    /*referencia a la ruta*/
    private lateinit var preguntasReference: DatabaseReference
    /*adaptadores para recycler view*/
    lateinit var opcion: FirebaseRecyclerOptions<Pregunta>
    lateinit var adapter: FirebaseRecyclerAdapter<Pregunta, PreguntaViewHolder>
    /*ViewHolder*/
    inner class PreguntaViewHolder(view:View): RecyclerView.ViewHolder(view){
        val pregunta=view.txtPregunta

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root=inflater.inflate(R.layout.cuestionario_fragment, container, false)
        /*Inicializacion de vista*/
        rv=root.findViewById(R.id.rvPregunta)
        /*inicializamos las rutas*/
        preguntasReference=FirebaseDatabase.getInstance().reference.child("preguntas")
        return root
    }

    override fun onStart() {
        super.onStart()
        /*managers del recycler*/
        rv.layoutManager= LinearLayoutManager(view?.context)
        /*qry PARA LA BUSQUEDA*/
        val qry=preguntasReference.equalTo("legal")
        /*creando el adaptador firebase*/
        val adapterRv=llenarPreguntas(qry)
        /*ligo los adapters a cada recycler*/
        rv.adapter=adapterRv
        /*pongo en marcha los escuchas*/
        adapterRv.startListening()
    }
    private fun llenarPreguntas(qry:Query):FirebaseRecyclerAdapter<Pregunta,PreguntaViewHolder>{

        opcion=FirebaseRecyclerOptions.Builder<Pregunta>().setQuery(
            qry,Pregunta::class.java).build()
        adapter=object:FirebaseRecyclerAdapter<Pregunta,PreguntaViewHolder>(opcion){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntaViewHolder {
                val view:View= LayoutInflater.from(parent.context).inflate(R.layout.custompregn,parent,false)
                return PreguntaViewHolder(view)
            }

            override fun onBindViewHolder(
                holder: PreguntaViewHolder,
                position: Int,
                model: Pregunta
            ) {
                holder.pregunta.text="asdas"
            }

        }
        return adapter
    }




}
