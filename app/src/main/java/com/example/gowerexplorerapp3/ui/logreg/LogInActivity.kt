package com.example.gowerexplorerapp3.ui.logreg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.controller.CurUserManager

class LogInActivity : AppCompatActivity() {
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

    override fun onStart() {
        super.onStart()
        emailInput = findViewById(R.id.txt_email)
        passwordInput = findViewById(R.id.txt_password)
        loginButton = findViewById(R.id.btnLogIn)
        registerButton = findViewById(R.id.btnRegister)
        msgView = findViewById(R.id.txt_status)

        loginButton.setOnClickListener {
            loginClick()
        }
        registerButton.setOnClickListener {
            registerClick()
        }
        update()
    }

    private fun update() {
        val currentUser = CurUserManager.mAuth.currentUser
        val currentEmail = currentUser?.email
        if (currentEmail == null) {
            msgView.text = "Not logged in!"
        } else {
            msgView.text = "logged in as: $currentEmail"
        }
    }

    private fun loginClick () {
        CurUserManager.mAuth.signInWithEmailAndPassword(
            emailInput.text.toString(),
            passwordInput.text.toString()
        ).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                CurUserManager.logInUser()
                finish() // goes back
            } else {
                msgView.text = getString(R.string.incorrect_login_details)
            }
        }
    }

    private fun registerClick() {
        val intent = Intent(this, RegisterActivity::class.java)
        this.startActivity(intent)
//        CurUserManager.mAuth.createUserWithEmailAndPassword(
//            emailInput.text.toString(),
//            passwordInput.text.toString()
//        ).addOnCompleteListener(this) { task ->
//            if (task.isSuccessful) {
//
//                // TODO...
//                val firebaseUser: FirebaseUser = task.result.user!!
//                update()
//                val user = UserModel(CurUserManager.mAuth)
//
//            } else {
//                // TODO...
//            }
//        }
    }

    //private fun closeKeyBoard() {
    //    TODO("Not yet implemented")
    //}
}