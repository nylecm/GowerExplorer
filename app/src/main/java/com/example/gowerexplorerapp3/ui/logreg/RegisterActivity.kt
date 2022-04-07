package com.example.gowerexplorerapp3.ui.logreg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.controller.MyUserManager

class RegisterActivity : AppCompatActivity() {
    private lateinit var emailInput: EditText
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var passwordConfirmInput: EditText
    private lateinit var registerButton: Button
    private lateinit var msgView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    override fun onStart() {
        super.onStart()
        emailInput = findViewById(R.id.txt_email)
        usernameInput = findViewById(R.id.txt_user_name)
        passwordInput = findViewById(R.id.txt_password)
        passwordConfirmInput = findViewById(R.id.txt_password_conf)
        registerButton = findViewById(R.id.btnRegister)
        msgView = findViewById(R.id.txt_status)

        registerButton.setOnClickListener {
            registerClick()
        }
        update()
    }

    private fun update() {
        val currentUser = MyUserManager.mAuth.currentUser
        val currentEmail = currentUser?.email
        if (currentEmail == null) {
            msgView.text = "Not logged in!"
        } else {
            msgView.text = "logged in as: $currentEmail"
        }
    }

    private fun registerClick() {
        MyUserManager.mAuth.createUserWithEmailAndPassword(
            emailInput.text.toString(),
            passwordInput.text.toString()
        ).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                MyUserManager.registerNewUser(usernameInput.text.toString())
            } else {
                // TODO...
            }
        }
    }

    //private fun closeKeyBoard() {
    //    TODO("Not yet implemented")
    //}
}