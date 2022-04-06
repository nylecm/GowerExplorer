package com.example.gowerexplorerapp3.ui.logreg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.controller.CurUserManager
import com.example.gowerexplorerapp3.model.UserModel
import com.google.firebase.auth.FirebaseUser

class LogInActivity : AppCompatActivity() {
    private var currentUser = CurUserManager.mAuth.currentUser

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var msgView: TextView

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
    }

    fun update() {
        currentUser = CurUserManager.mAuth.currentUser
        val currentEmail = currentUser?.email
        if (currentEmail == null) {
            msgView.text = "Not logged in!"
        } else {
            msgView.text = "logged in as: $currentEmail"
        }

    }

    override fun onStart() {
        super.onStart()



        emailInput = findViewById(R.id.inputEmail)!!
        passwordInput = findViewById(R.id.inputPassword)!!
        loginButton = findViewById(R.id.btnLogIn)!!
        registerButton = findViewById(R.id.btnRegister)!!
        msgView = findViewById(R.id.txtStatus)!!

        loginButton.setOnClickListener {
            loginClick()
        }
        registerButton.setOnClickListener {
            registerClick()
        }
        update()
    }

    private fun loginClick () {
        CurUserManager.mAuth.signInWithEmailAndPassword(
            emailInput.text.toString(),
            passwordInput.text.toString()
        ).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                update()
                //closeKeyBoard()
            } else {
                msgView.text = "very epic fail"
            }
        }
    }

    private fun registerClick() {
        CurUserManager.mAuth.createUserWithEmailAndPassword(
            emailInput.text.toString(),
            passwordInput.text.toString()
        ).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // TODO...
                val firebaseUser: FirebaseUser = task.result.user!!
                update()
                val user = UserModel(CurUserManager.mAuth)

            } else {
                // TODO...
            }
        }
    }

    //private fun closeKeyBoard() {
    //    TODO("Not yet implemented")
    //}
}