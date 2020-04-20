package com.example.westcuestionario

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

data class Pregunta(
    val pregunta:String="def",
    val opciones:HashMap<String, String>?=null,
    val respuesta:String="def"
){
    /*
    private fun crearBanco(listaP:List<String>,listaO:List<String>,listaR: List<String>,nombreBanco:String): ArrayList<Pregunta> {
        var bancoPreg=ArrayList<Pregunta>()
        var listaP=listaP
        var listaO=listaO
        var listaR=listaR
        for(p in listaP) {
            var pre:Pregunta=Pregunta()
            //opciones
            for (l in listaO) {
                var a = HashMap<String, String>()
                var nn: List<String>
                nn = l.split("/")
                var i=0
                for (n in nn) {
                    a.put(i.toString(),n )
                    i++
                }
                for(r in listaR){
                    var listaRt=listaR.toMutableList()
                    pre = Pregunta(p,a,r)
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
        var b=HashMap<String,ArrayList<Pregunta>>()
        b.put(nombreBanco,bancoPreg)
        var banco=BancoPreguntas(b)
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
        for (pregunta in Banco){


            i++
        }




    }*/
}