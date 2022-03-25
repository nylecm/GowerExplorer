package com.example.gowerexplorerapp3

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class MapHome : Fragment() {

    private lateinit var mMap: GoogleMap
    // private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as MainActivity)

        return inflater.inflate(R.layout.fragment_map_home, container, false)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        // Add a marker in Sydney and move the camera
        mMap = googleMap

        val rhossili = LatLng(51.57230253453608, -4.291339367942704)
        googleMap.addMarker(MarkerOptions().position(rhossili).title("Rhossili")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)))
        val portEynon = LatLng(51.544325787706306, -4.210278367615245)
        googleMap.addMarker(MarkerOptions().position(portEynon).title("Port Eynon")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)))
        val whitefordLightHouse = LatLng(51.65308998230439, -4.250174428774476)
        googleMap.addMarker(MarkerOptions().position(whitefordLightHouse).title("Whiteford Light House")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
        val arthursStone = LatLng(51.593617098371, -4.179297132938529)
        googleMap.addMarker(MarkerOptions().position(arthursStone).title("Arthur's Stone")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
        val caswell = LatLng(51.57003166974094, -4.031151391066391)
        googleMap.addMarker(MarkerOptions().position(caswell).title("Caswell")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)))
        val threeCliffs = LatLng(51.57303146866698, -4.1121056531239795)
        googleMap.addMarker(MarkerOptions().position(threeCliffs).title("Three Cliffs")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)))
        val langlandBay = LatLng(51.567802269570436, -4.012446099161361)
        googleMap.addMarker(MarkerOptions().position(langlandBay).title("Langland Bay")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)))
        val oystermouthCastle = LatLng(51.57678604586335, -4.002414253222207)
        googleMap.addMarker(MarkerOptions().position(oystermouthCastle).title("Oystermouth Castle")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))

//        mMap.moveCamera(CameraUpdateFactory.zoomTo(10F))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(rhossili))
        getLastLocation()

        //toCoFo()
    }

//    private fun toCoFo() {
//        //co-ordinates for CoFo (pull out of Google Maps URL or right click the point)
//        val coFo = LatLng(51.61921664227731, -3.8786439498970613)
//        mMap.addMarker(MarkerOptions().position(coFo)
//            .title("Computational Foundry"))
//        //map appears as though under a `camera'
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(coFo))
//        //zoom level from 1--20 as float
//        mMap.moveCamera(CameraUpdateFactory.zoomTo(18F))
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    private fun getLastLocation() {
        if (isLocationEnabled()) {
            // checking location permission
            if (ActivityCompat.checkSelfPermission(
                    activity as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // request permission
                ActivityCompat.requestPermissions(
                    activity as MainActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 42
                );
                return
            }
            //once the last location is acquired
            mFusedLocationClient.lastLocation.addOnCompleteListener(activity as MainActivity) { task ->
                val location: Location? = task.result
                if (location == null) {
                    //if it couldn't be acquired, get some new location data
                    requestNewLocationData()
                } else {
                    val lat = location.latitude
                    val long = location.longitude

                    Log.i("LocLatLocation", "$lat and $long")

                    val lastLoc = LatLng(lat, long)

                    //update camera
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(10F))
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(lastLoc))

                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(lastLoc))
                    mMap.addMarker(MarkerOptions().position(lastLoc)
                        .title("Current Location"))
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
            maxWaitTime= 100
        }

        // checking location permission
        if (ActivityCompat.checkSelfPermission(
                activity as MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request permission
            ActivityCompat.requestPermissions(
                activity as MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 42
            );
            return
        }
        //update the location client

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient((activity as MainActivity))
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
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
}