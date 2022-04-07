package com.example.gowerexplorerapp3.controller

import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.model.PoiModel
import com.example.gowerexplorerapp3.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object MyUserManager {
    private const val TAG: String = "MyUserManager"
    var mAuth = FirebaseAuth.getInstance()
    var curUser: UserModel? = null

    init {
        if (mAuth.uid != null) {
            logInUser()
        }
    }

    fun logInUser() {
        val db = Firebase.firestore

        val docRef = db.collection("users").document(mAuth.uid.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    curUser = UserModel(
                        document["userName"] as String,
                        document["admin"] as Boolean,
                        ((document.data?.get("numberOfPoints")) as Long).toInt(),
                        document["poisExplored"] as ArrayList<String>
                    )
                    Log.d(TAG, "poisExplored data: ${curUser!!.poisExplored}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

    }

    fun updateUser() {
        assert(curUser != null)
        val db = Firebase.firestore

        db.collection("users")
            .document(mAuth.currentUser!!.uid)
            .set(curUser!!)
    }

    fun registerNewUser(userName: String) {
        val db = Firebase.firestore

        curUser = UserModel(userName, false, 0, ArrayList())

        db.collection("users")
            .document(mAuth.currentUser!!.uid)
            .set(curUser!!)
    }

    fun signOut() {
        mAuth.signOut()
        curUser = null
    }

    fun creditUserPointsAndDiscoverPoi() {
        creditUserPoints(PoiManager.curPoi!!.poiPoints)
        discoverPoi()
    }

    fun creditUserPoints(numberOfPointsToAdd: Int) {
        val db = Firebase.firestore
        curUser!!.numberOfPoints += numberOfPointsToAdd
        db.collection("users").document(mAuth.currentUser!!.uid).update("numberOfPoints", curUser!!.numberOfPoints);
    }

    private fun discoverPoi() {
        val db = Firebase.firestore
        curUser!!.poisExplored.add(PoiManager.curPoi!!.poiId)
        db.collection("users").document(mAuth.currentUser!!.uid).update("poisExplored", curUser!!.poisExplored);
    }
}