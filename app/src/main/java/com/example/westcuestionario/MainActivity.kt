package com.example.westcuestionario

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {


    private lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database=FirebaseDatabase.getInstance().reference
        val ref=database.child("hola").child("nueva").setValue("dsadas")


    }

    override fun onStart() {
        super.onStart()

    }
}
