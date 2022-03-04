package com.example.gowerexplorerapp3

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapHome : Fragment() {

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

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(rhossili))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}