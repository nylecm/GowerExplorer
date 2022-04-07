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
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.controller.MyUserManager
import com.example.gowerexplorerapp3.controller.PoiManager
import com.example.gowerexplorerapp3.databinding.ActivityPoiViewBinding
import com.example.gowerexplorerapp3.model.ReviewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import java.util.*

class PoiView : AppCompatActivity(), TextToSpeech.OnInitListener {

    private val TAG: String = "PoiView"
    private lateinit var binding: ActivityPoiViewBinding
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var btnSpeak: Button
    private lateinit var reviewHolder: LinearLayout
    private val curPoi = PoiManager.curPoi!!
    var lastUserLocation: GeoPoint = GeoPoint(0.0, 0.0)
    private var tts: TextToSpeech? = null

    // TODO add location itegration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)
        binding = ActivityPoiViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        //val poiTitle = intent.getStringExtra("title")
        binding.toolbarLayout.title = curPoi.title

        Picasso.get()
            .load(curPoi.imgUrl)
            .resize(1080, 600)
            .centerCrop()
            .into(findViewById<ImageView>(R.id.mainImage));

        findViewById<TextView>(R.id.textview).text = curPoi.description

        btnSpeak = findViewById(R.id.btn_speak)
        btnSpeak.isEnabled = false;
        tts = TextToSpeech(this, this)

        btnSpeak.setOnClickListener { speakOut(curPoi.description) }

        val parkingLocationStr =
            curPoi.parkingLocation.latitude.toString() + ", " + curPoi.parkingLocation.longitude.toString()

        findViewById<Button>(R.id.navigateTo).setOnClickListener {
            val gmmIntentUri =
                Uri.parse("google.navigation:q=$parkingLocationStr")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        findViewById<TextView>(R.id.txtDirections).text = curPoi.directions

        findViewById<Button>(R.id.btn_check_in).setOnClickListener {
            getLastLocation()
            if (curPoi.isCloseEnoughToDiscover(this.lastUserLocation)) {
                Log.i(TAG, "Close Enough")
            } else {
                Log.i(TAG, "Not Close Enough")
            }
        }

        reviewHolder = findViewById(R.id.ll_reviews)

        findViewById<Button>(R.id.btn_post_review).setOnClickListener {
            //ReviewModel()
        }
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
