package com.example.westcuestionario

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {
    private lateinit var preguntasReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onStart() {
        super.onStart()
        preguntasReference=FirebaseDatabase.getInstance().reference.child("preguntas")
        var listener= object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.getValue(Banco::class.java)

                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        preguntasReference.addValueEventListener(listener)

    }



}