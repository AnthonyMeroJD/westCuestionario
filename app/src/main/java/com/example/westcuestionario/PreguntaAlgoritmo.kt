package com.example.westcuestionario

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

data class PreguntaAlgoritmo(
    val pregunta:String="def",
    val opciones:HashMap<String, String>?=null,
    val respuesta:String="def"
){
    /*
     onFirebase(R.raw.preguntasriesgos,R.raw.opcionesriesgos,R.raw.respuestasriesgos,"Riesgos")
        onFirebase(R.raw.preguntasreentrenamiento,R.raw.opcionesreentrenamiento,R.raw.respuestasreentrenamiento,"Rentrenamiento")
        onFirebase(R.raw.preguntasprimerosauxilios,R.raw.opcionesprimerosauxilios,R.raw.respuestasprimerosauxilios,"PrimerosAuxilios")
        onFirebase(R.raw.preguntasdesarrollohumano,R.raw.opcionesdesarrollohumano,R.raw.respuestasdesarrollohumano,"DesarrolloHumano")
        onFirebase(R.raw.preguntasseguridadciudadana,R.raw.opcionesseguridadciudadana,R.raw.respuestasseguridadciudadana,"SeguridadCiudadana")
        onFirebase(R.raw.preguntasdefensapersonal,R.raw.opcionesdefensapersonal,R.raw.respuestasdefensapersonal,"DefensaPersonal")
        onFirebase(R.raw.preguntaslegal,R.raw.opcioneslegal,R.raw.respuestaslegal,"Legal")


    private fun crearBanco(listaP:List<String>,listaO:List<String>,listaR: List<String>,nombreBanco:String): ArrayList<Banco> {
        var bancoPreg=ArrayList<Banco>()
        var listaP=listaP
        var listaO=listaO
        var listaR=listaR
        for(p in listaP) {
            var pre:Banco= Banco(null,null,null)
            //opciones
            for (l in listaO) {
                var a = HashMap<String, String>()
                var nn: List<String>
                nn = l.split("/")
                var i=0
                for (n in nn) {
                    var k="key_".plus(i.toString())
                    a.put(k,n )
                    i++
                }
                for(r in listaR){
                    var listaRt=listaR.toMutableList()
                    pre = Banco(a,p,r)
                    listaRt.remove(r)
                    listaR=listaRt
                    break
                }
                var listaa= listaO.toMutableList()
                listaa.remove(l)
                listaO=listaa.toList()
                break
            }

            bancoPreg.add(pre)
        }



        return bancoPreg
    }
    private fun onLine(rsc:Int):String{
        var str:String
        var iS: InputStream =this.resources.openRawResource(rsc)
        var out= BufferedReader(InputStreamReader(iS))
        str=out.readLine()
        return str
    }
    private fun onList(strOnline:String,delimitador:String):List<String>{
        var listaOnList:List<String>

        listaOnList= strOnline.split(delimitador)

        return listaOnList
    }
    private fun onFirebase(srcP:Int,srcO:Int,srcR:Int,nombreBanco:String){
        var preguntasOnline=onLine(srcP)
        var opcionesOnline=onLine(srcO)
        var respuestaOnline=onLine(srcR)
        var listaPreguntasOnline=onList(preguntasOnline,"|")
        var listaOpcionesOnline=onList(opcionesOnline,"|")
        var listaRespuestas=onList(respuestaOnline,"|")
        var Banco=crearBanco(listaPreguntasOnline,listaOpcionesOnline,listaRespuestas,nombreBanco)
        var i=0
        var asignaturaL=HashMap<String,ArrayList<Literal>>()
        var literal=ArrayList<Literal>()
        var lH=HashMap<String,Banco>()
        var ref=FirebaseDatabase.getInstance().reference.child("preguntas").child(nombreBanco)
        for (p in Banco){
            var k="key_".plus(i.toString())
            ref.child(k).setValue(p)
            i++
        }



    }*/
}