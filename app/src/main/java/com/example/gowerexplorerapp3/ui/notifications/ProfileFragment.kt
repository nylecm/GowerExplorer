package com.example.gowerexplorerapp3.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var mAuth = FirebaseAuth.getInstance()

    private var currentUser = mAuth.currentUser

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var msgView: TextView

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    fun update() {
        currentUser = mAuth.currentUser
        val currentEmail = currentUser?.email
        if (currentEmail == null) {
            msgView.text = "Not logged in!"
        } else {
            msgView.text = "logged in as: $currentEmail"
        }

    }

    override fun onStart() {
        super.onStart()
        emailInput = view?.findViewById(R.id.inputEmail)!!
        passwordInput = view?.findViewById(R.id.inputPassword)!!
        loginButton = view?.findViewById(R.id.btnLogIn)!!
        registerButton = view?.findViewById(R.id.btnRegister)!!
        msgView = view?.findViewById(R.id.txtStatus)!!

        loginButton.setOnClickListener {
            loginClick()
        }

        registerButton.setOnClickListener {
            registerClick()
        }

        update()
    }

    private fun loginClick () {
        mAuth.signInWithEmailAndPassword(
            emailInput.text.toString(),
            passwordInput.text.toString()
        ).addOnCompleteListener(this.requireActivity()) { task ->
            if (task.isSuccessful) {
                update()
                //closeKeyBoard()
            } else {
                msgView.text = "very epic fail"
            }
        }
    }

    private fun registerClick() {
        mAuth.createUserWithEmailAndPassword(
            emailInput.text.toString(),
            passwordInput.text.toString()
        ).addOnCompleteListener(this.requireActivity()) { task ->
            if (task.isSuccessful) {
                // TODO...
                val firebaseUser: FirebaseUser = task.result.user!!

                val user = UserModel(mAuth)

            } else {
                // TODO...
            }
        }
    }

    //private fun closeKeyBoard() {
    //    TODO("Not yet implemented")
    //}
}