package com.example.gowerexplorerapp3.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class UserModel {
    //lateinit var email: String
    var points: Int = 0
    var poisExplored: Vector<String> = Vector()
    //private val db = Firebase.firestore

    /**
     * User model constructor that can construct an arbitrary user based of set params.
     */
    constructor(email: String, numberOfPoints: Int, poisExplored: Vector<String>) {

    }

    /**
     * User model constructor that can constrct a
     */
    constructor(mAuth: FirebaseAuth) {
        //email = mAuth.currentUser?.email.toString()
        val uid = mAuth.currentUser?.uid

        var db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(uid!!)
            .set(this)
    }
}

