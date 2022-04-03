package com.example.gowerexplorerapp3.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class UserModel {
    lateinit var email: String
    var numberOfPoints: Int = 0
    var poisExplored: Vector<String> = Vector()
    private val db = Firebase.firestore

    /**
     * User model constructor that can construct an arbitrary user based of set params.
     */
    constructor(email: String, numberOfPoints: Int, poisExplored: Vector<String>) {

    }

    /**
     * User model constructor that can constrct a
     */
    constructor(mAuth: FirebaseAuth) {
        email = mAuth.currentUser?.email.toString()
        val uid = mAuth.uid

        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

        db.collection("users").add(user)
    }
}