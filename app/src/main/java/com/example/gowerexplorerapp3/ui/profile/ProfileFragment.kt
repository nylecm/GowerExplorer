package com.example.gowerexplorerapp3.ui.profile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.controller.MyUserManager
import com.example.gowerexplorerapp3.controller.PoiManager
import com.example.gowerexplorerapp3.ui.poiview.PoiEditActivity
import com.example.gowerexplorerapp3.ui.logreg.LogInActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class ProfileFragment : Fragment() {

    lateinit var txtHelloUsername: TextView
    lateinit var btnLogInOut: Button
    lateinit var btnEmailPoIs: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onStart() {
        super.onStart()

        txtHelloUsername = view?.findViewById(R.id.txt_hello_username)!!
        btnLogInOut = view?.findViewById(R.id.btn_log_in_out)!!
        btnEmailPoIs = view?.findViewById(R.id.btn_email_pois)!!
        if (MyUserManager.curUser != null && MyUserManager.curUser!!.isAdmin) {
            view?.findViewById<CardView>(R.id.card_admin)!!.isVisible = true
            adminSetup()
        }

        val txtLeaderboard = view?.findViewById<TextView>(R.id.txt_leaderboard)!!

        val db = Firebase.firestore
        db.collection("users").get().addOnSuccessListener { documents ->
            for (user in documents) {
                txtLeaderboard.text = "${txtLeaderboard.text} ${user["userName"]} , ${user["numberOfPoints"].toString()} points.\n\n"
            }
        }

    }

    private fun adminSetup() {
        view?.findViewById<Button>(R.id.btn_create_poi)!!.setOnClickListener {
            PoiManager.curPoi = null
            val intent = Intent(requireContext(), PoiEditActivity::class.java)
            requireContext().startActivity(intent)
        }
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

        view?.findViewById<CardView>(R.id.card_profile)!!.isVisible = true

        btnEmailPoIs.isVisible = true
        btnEmailPoIs.setOnClickListener {
            var emailBody: String = MyUserManager.curUser!!.prepareEmailOfExploredPois()
            val title = "Explored PoIs"

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(MyUserManager.mAuth.currentUser!!.email))
            intent.putExtra(Intent.EXTRA_SUBJECT, title)
            intent.putExtra(Intent.EXTRA_TEXT, emailBody)
            try {
                requireContext().startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(
                    requireContext(),
                    "Error: There are no email clients installed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val txtNumberOfPoints = view?.findViewById<TextView>(R.id.txt_number_of_points)!!
        txtNumberOfPoints.isVisible = true
        txtNumberOfPoints.text = getString(R.string.you_have) + " " + MyUserManager.curUser!!.numberOfPoints + getString(R.string.points_end_sent)
    }
}