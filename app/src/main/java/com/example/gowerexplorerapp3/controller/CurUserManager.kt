package com.example.gowerexplorerapp3.controller

import com.example.gowerexplorerapp3.model.UserModel
import com.google.firebase.auth.FirebaseAuth

object CurUserManager {
    var mAuth = FirebaseAuth.getInstance()
    var curUser: UserModel? = null

    init {

    }
}