package com.example.gowerexplorerapp3.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.controller.MyUserManager
import com.example.gowerexplorerapp3.ui.logreg.LogInActivity

class ProfileFragment : Fragment() {

    lateinit var txtHelloUsername: TextView
    lateinit var btnLogInOut: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onStart() {
        txtHelloUsername = view?.findViewById(R.id.txt_hello_username)!!
        btnLogInOut = view?.findViewById(R.id.btn_log_in_out)!!
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

        // No user is logged in:
        if (MyUserManager.curUser == null) {
            displayLoggedOutUI()
        } else { // A user is already logged in:
            displayLoggedInUI()
        }
    }

    fun displayLoggedOutUI() {
        txtHelloUsername.text = getString(R.string.hello_guest_user)
        btnLogInOut.text = getString(R.string.log_in)
        btnLogInOut.setOnClickListener {
            val intent = Intent(requireContext(), LogInActivity::class.java)
            requireContext().startActivity(intent)
        }
    }

    fun displayLoggedInUI() {
        txtHelloUsername.text = "Hello ${MyUserManager.curUser?.userName}"
        btnLogInOut.text = getString(R.string.log_out)

        btnLogInOut.setOnClickListener {
            MyUserManager.signOut()
            displayLoggedOutUI()
            // TODO sign out code here
        }
    }
}