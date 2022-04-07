package com.example.gowerexplorerapp3.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.controller.PoiManager

class PoiEditActivity : AppCompatActivity() {
    lateinit var txtTitle: EditText
    lateinit var txtImageUrl: EditText
    lateinit var txtDescription: EditText
    lateinit var txtDirections: EditText
    lateinit var txtPoiLatitude: EditText
    lateinit var txtPoiLongitude: EditText
    lateinit var txtPoiParkingLatitude: EditText
    lateinit var txtPoiParkingLongitude: EditText
    lateinit var txtNumberOfPoints: EditText
    lateinit var txtDiscoveryRange: EditText
    lateinit var btnSubmit: Button


    var isEditing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poi_edit)
    }

    override fun onStart() {
        super.onStart()

        txtTitle = findViewById(R.id.txt_title)
        txtImageUrl = findViewById(R.id.txt_img_url)
        txtDescription = findViewById(R.id.txt_description)
        txtDirections = findViewById(R.id.txt_directions)
        txtPoiLatitude = findViewById(R.id.txt_poi_lat)
        txtPoiLongitude = findViewById(R.id.txt_poi_long)
        txtPoiParkingLatitude = findViewById(R.id.txt_poi_parking_lat)
        txtPoiParkingLongitude = findViewById(R.id.txt_poi_parking_long)
        txtNumberOfPoints = findViewById(R.id.txt_number_of_points)
        txtDiscoveryRange = findViewById(R.id.txt_discovery_range)
        btnSubmit = findViewById(R.id.btn_submit_poi)

        if (PoiManager.curPoi != null) {
            isEditing = true
            fillWithPoiData()
        }
    }

    private fun fillWithPoiData() {
        txtTitle.setText(PoiManager.curPoi!!.title)
        txtImageUrl.setText(PoiManager.curPoi!!.imgUrl)
        txtDescription.setText(PoiManager.curPoi!!.description)
        txtDirections.setText(PoiManager.curPoi!!.directions)
        txtPoiLatitude.setText(PoiManager.curPoi!!.location.latitude.toString())
        txtPoiLongitude.setText(PoiManager.curPoi!!.location.longitude.toString())
        txtPoiParkingLatitude.setText(PoiManager.curPoi!!.parkingLocation.latitude.toString())
        txtPoiParkingLongitude.setText(PoiManager.curPoi!!.parkingLocation.longitude.toString())
        txtNumberOfPoints.setText(PoiManager.curPoi!!.poiPoints.toString())
        txtDiscoveryRange.setText(PoiManager.curPoi!!.poiRangeInM.toString())
    }
}