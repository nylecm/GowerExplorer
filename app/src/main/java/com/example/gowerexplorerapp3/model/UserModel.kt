package com.example.gowerexplorerapp3.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class UserModel {
    //lateinit var email: String
    var isAdmin: Boolean = false
    var numberOfPoints: Int = 0
    var poisExplored: Vector<String> = Vector()

    /**
     * User model constructor that can construct an arbitrary user based of set params.
     */
    constructor(isAdmin: Boolean, numberOfPoints: Int, poisExplored: Vector<String>) {
        this.isAdmin = isAdmin
        this.numberOfPoints = numberOfPoints
        this.poisExplored - poisExplored
    }

    /**
     * User model constructor that can construct an arbitrary user based of set params. todo remove
     */
    constructor(isAdmin: Boolean, numberOfPoints: Int) {
        this.isAdmin = isAdmin
        this.numberOfPoints = numberOfPoints
    }

    private fun addNewUserToDB(uid: String) {
//        db.collection("users")
//            .document(uid)
//            .set(this)
    }
}

