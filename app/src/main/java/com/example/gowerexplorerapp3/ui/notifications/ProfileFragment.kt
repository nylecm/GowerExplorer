package com.example.gowerexplorerapp3.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.gowerexplorerapp3.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {

    private var mAuth = FirebaseAuth.getInstance()
    private var currentUser = mAuth.currentUser

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

    override fun onDestroyView() {
        super.onDestroyView()
    }
}