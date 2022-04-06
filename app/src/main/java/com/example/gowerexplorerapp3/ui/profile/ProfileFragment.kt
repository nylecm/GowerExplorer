package com.example.gowerexplorerapp3.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.controller.CurUserManager
import com.example.gowerexplorerapp3.ui.logreg.LogInActivity

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onStart() {
        super.onStart()

        val btnLogInOut: Button = view?.findViewById(R.id.btn_log_in_out)!!

        // No user is logged in:
        if (CurUserManager.curUser == null) {
            btnLogInOut.setOnClickListener {
                val intent = Intent(requireContext(), LogInActivity::class.java)
                requireContext().startActivity(intent)
            }
        } else { // A user is already logged in:
            btnLogInOut.text = getString(R.string.log_out)
            btnLogInOut.setOnClickListener {
                // TODO sign out code here
            }
        }



    }
}