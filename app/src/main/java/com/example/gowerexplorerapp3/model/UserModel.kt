package com.example.gowerexplorerapp3.model

import java.util.*
import kotlin.collections.ArrayList

class UserModel {
    var userName: String = ""
    var isAdmin: Boolean = false
    var numberOfPoints: Int = 0
    var poisExplored: ArrayList<String> = ArrayList()

    /**
     * User model constructor that can construct an arbitrary user based of set params.
     */
    constructor(userName: String, isAdmin: Boolean, numberOfPoints: Int, poisExplored: ArrayList<String>) {
        this.userName = userName
        this.isAdmin = isAdmin
        this.numberOfPoints = numberOfPoints
        this.poisExplored = poisExplored
    }

    private fun addToDB(uid: String) {

    }
}

