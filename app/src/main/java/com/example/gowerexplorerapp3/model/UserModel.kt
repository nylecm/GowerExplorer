package com.example.gowerexplorerapp3.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class UserModel {
    //lateinit var email: String
    var userName: String = ""
    var isAdmin: Boolean = false
    var numberOfPoints: Int = 0
    var poisExplored: Vector<String> = Vector()

    /**
     * User model constructor that can construct an arbitrary user based of set params.
     */
    constructor(userName: String, isAdmin: Boolean, numberOfPoints: Int, poisExplored: Vector<String>) {
        this.userName = userName
        this.isAdmin = isAdmin
        this.numberOfPoints = numberOfPoints
        this.poisExplored = poisExplored
    }

    /**
     * User model constructor that can construct an arbitrary user based of set params. todo remove
     */
    constructor(userName: String, isAdmin: Boolean, numberOfPoints: Int) {
        this.userName = userName
        this.isAdmin = isAdmin
        this.numberOfPoints = numberOfPoints
    }

    private fun addToDB(uid: String) {

    }
}

