package com.example.gowerexplorerapp3.controller

import android.util.Log
import com.example.gowerexplorerapp3.model.PoiModel
import com.example.gowerexplorerapp3.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object CurUserManager {
    private const val TAG: String = "CurUserManager"
    var mAuth = FirebaseAuth.getInstance()
    var curUser: UserModel? = null

    init {
        logInUser()
    }

    fun logInUser() {
        val db = Firebase.firestore

        val docRef = db.collection("users").document(mAuth.uid.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    curUser = UserModel(document["isAdmin"] as Boolean, ((document.data?.get("numberOfPoints")) as Long).toInt())
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

    }

    fun signOut() {
        mAuth.signOut()
        curUser = null
    }
}