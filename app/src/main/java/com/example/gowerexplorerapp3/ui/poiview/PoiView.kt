package com.example.gowerexplorerapp3.ui.poiview

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.os.UserManager
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.controller.MyUserManager
import com.example.gowerexplorerapp3.controller.PoiManager
import com.example.gowerexplorerapp3.databinding.ActivityPoiViewBinding
import com.example.gowerexplorerapp3.model.ReviewModel
import com.example.gowerexplorerapp3.ui.PoiEditActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.util.*

class PoiView : AppCompatActivity(), TextToSpeech.OnInitListener {

    private val TAG: String = "PoiView"
    private lateinit var binding: ActivityPoiViewBinding
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var btnSpeak: Button
    private lateinit var btnCheckIn: Button
    private lateinit var reviewHolder: LinearLayout
    private lateinit var mainLinearLayout: LinearLayout
    var lastUserLocation: GeoPoint = GeoPoint(0.0, 0.0)
    private var tts: TextToSpeech? = null

    // TODO add location itegration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
        binding = ActivityPoiViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        binding.toolbarLayout.title = PoiManager.curPoi!!.title

        Picasso.get()
            .load(PoiManager.curPoi!!.img)
            .resize(1080, 600)
            .centerCrop()
            .into(findViewById<ImageView>(R.id.mainImage));

        findViewById<TextView>(R.id.textview).text = PoiManager.curPoi!!.description

        btnSpeak = findViewById(R.id.btn_speak)
        btnSpeak.isEnabled = false;
        tts = TextToSpeech(this, this)

        btnSpeak.setOnClickListener { speakOut(PoiManager.curPoi!!.description) }

        val parkingLocationStr =
            PoiManager.curPoi!!.parkingLocation.latitude.toString() + ", " + PoiManager.curPoi!!.parkingLocation.longitude.toString()

        findViewById<Button>(R.id.navigateTo).setOnClickListener {
            val gmmIntentUri =
                Uri.parse("google.navigation:q=$parkingLocationStr")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        findViewById<TextView>(R.id.txtDirections).text = PoiManager.curPoi!!.directions

        btnCheckIn = findViewById(R.id.btn_check_in)

        btnCheckIn.setOnClickListener {
            if (MyUserManager.curUser == null) {
                Snackbar.make(
                    it,
                    "You must be logged in to discover the PoI.",
                    Snackbar.LENGTH_LONG
                ).show()
            } else if (MyUserManager.curUser!!.poisExplored.contains(PoiManager.curPoi!!.poiId)) {
                Snackbar.make(
                    it,
                    "PoI already explored.",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                getLastLocation()
                if (PoiManager.curPoi!!.isCloseEnoughToDiscover(this.lastUserLocation)) {
                    Log.d(TAG, "Close Enough")
                    MyUserManager.creditUserPointsAndDiscoverPoi()
                    Snackbar.make(it, "You have discovered the PoI.", Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    Log.d(TAG, "Not Close Enough")
                    Snackbar.make(
                        it,
                        "You are not close enough to discover the PoI.",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

        reviewHolder = findViewById(R.id.ll_reviews)

        findViewById<Button>(R.id.btn_post_review).setOnClickListener {
            // check if user is logged in:
            postReview()

        }
        populateReviews()

        mainLinearLayout = findViewById<LinearLayout>(R.id.ll_scroll_main)

        val editPoiButton = Button(this)
        editPoiButton.text = getString(R.string.edit_poi)
        editPoiButton.setPadding(24, 8, 24, 8)

        editPoiButton.setOnClickListener {
            val intent = Intent(this, PoiEditActivity::class.java)
            startActivity(intent)
        }

        val deletePoiButton = Button(this)
        deletePoiButton.text = getString(R.string.delete_poi)
        deletePoiButton.setPadding(24, 8, 24, 16)

        deletePoiButton.setOnClickListener {
            PoiManager.deletePoi(PoiManager.curPoi!!)
            Snackbar.make(binding.root, "Poi Deleted, Please Re-open App.", Snackbar.LENGTH_SHORT)
                .show()
        }

        if (MyUserManager.curUser != null && MyUserManager.curUser!!.isAdmin) {
            mainLinearLayout.addView(editPoiButton)
            mainLinearLayout.addView(deletePoiButton)
        }
    }

    private fun postReview() {
        if (MyUserManager.mAuth.currentUser != null) {
            val reviewTitle: String =
                findViewById<EditText>(R.id.txt_new_review_title).text.toString()
            val reviewStars: Int = findViewById<RatingBar>(R.id.rating_review_new).numStars
            val reviewContents: String =
                findViewById<EditText>(R.id.txt_new_review_contents).text.toString()

            val review = ReviewModel(
                reviewTitle,
                reviewStars,
                reviewContents,
                MyUserManager.mAuth.currentUser!!.uid,
                PoiManager.curPoi!!.poiId
            )

            val db = Firebase.firestore

            db.collection("reviews")
                .document(review.hashCode().toString())
                .set(review)
            updateReviews()
            Snackbar.make(binding.root, "Review Posted!", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(
                binding.root,
                "You must be logged in to post a review",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateReviews() {
        reviewHolder.removeAllViews()
        populateReviews()
    }

    private fun populateReviews() {
        val db = Firebase.firestore

        db.collection("reviews")
            .whereEqualTo("poiId", PoiManager.curPoi!!.poiId)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() == 0) {
                    val txtNoReviews = TextView(this)
                    txtNoReviews.text = getString(R.string.no_reviews)
                    findViewById<LinearLayout>(R.id.ll_reviews).addView(txtNoReviews)
                } else {
                    var totalStars = 0

                    for (document in documents) { // fill up reviews
                        var userName = ""
                        val uid: String = document["userId"].toString()

                        // Username lookup happens async hence we need to out dummy data in first.
                        val txtReviewTitle = TextView(this)
                        txtReviewTitle.text =
                            "${document["title"].toString()}, by: $userName"
                        reviewHolder.addView(txtReviewTitle)

                        db.collection("users").document(uid)
                            .get()
                            .addOnSuccessListener { documentUser ->
                                userName = documentUser["userName"] as String
                                Log.d(TAG, "username found: '$userName' for user: '$uid'")
                                txtReviewTitle.text =
                                    "${document["title"].toString()}, by: $userName"
                            }
                            .addOnFailureListener { exception ->
                                Log.d(TAG, "no username fou nd, for user: '$uid'")
                                txtReviewTitle.text =
                                    "${document["title"].toString()}, by: N/A"

                            }

                        val stars = (document["stars"] as Long).toInt()
                        totalStars += stars

                        val txtReviewStars = TextView(this)
                        txtReviewStars.text = "Rated: $stars/5"
                        reviewHolder.addView(txtReviewStars)

                        val txtReviewContent = TextView(this)
                        txtReviewContent.text = document["content"].toString()
                        txtReviewContent.setPadding(0, 0, 0, 16)
                        reviewHolder.addView(txtReviewContent)
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }

                    findViewById<TextView>(R.id.txt_review_summary).text =
                        "${documents.size()} Review(s), Avg Rating: ${totalStars.toDouble() / documents.size()}."
                    // todo display stars
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    private fun getLastLocation() {
        if (isLocationEnabled()) {
            // checking location permission
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // request permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 42
                );
                return
            }
            //once the last location is acquired
            mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                val location: Location? = task.result
                if (location == null) {
                    //if it couldn't be acquired, get some new location data
                    requestNewLocationData()
                } else {
                    val lat = location.latitude
                    val long = location.longitude

                    this.lastUserLocation = GeoPoint(lat, long)
                    Log.i("LocLatLocation", "$lat and $long")
                }
            }
            //couldn't get location, so go to Settings (deprecated??)
        } else {
//            val mRootView = findViewById<View>(R.id.map)
//            val locSnack = Snackbar.make(contextView, "R.string.location_switch", Snackbar.LENGTH_LONG)
//            locSnack.show()
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }

    //call to get new location
    private fun requestNewLocationData() {
        //parameters for location
        val mLocationRequest = LocationRequest.create().apply {
            interval = 100
            fastestInterval = 50
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = 100
        }
        // checking location permission
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 42
            );
            return
        }
        //update the location client
        mFusedLocationClient =
            LocationServices.getFusedLocationProviderClient((this))
        //add a callback so that location is repeatedly updated according to parameters
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()!!
        )
    }

    //callback for repeatedly getting location
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            val lat = mLastLocation.latitude
            val long = mLastLocation.longitude

            val lastLoc = LatLng(lat, long)
            Log.i("LocLatLocationCallback", "$lat and $long")

        }
    }

    //check whether location is enabled
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.UK)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            } else {
                btnSpeak.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }
}
