package com.example.gowerexplorerapp3.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.controller.PoiManager
import com.google.android.material.snackbar.Snackbar

class PoiEditActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val TAG = "PoiEditActivity"
    private lateinit var txtTitle: EditText
    private lateinit var spinnerPoiType: Spinner
    private lateinit var txtImageUrl: EditText
    private lateinit var txtDescription: EditText
    private lateinit var txtDirections: EditText
    private lateinit var txtPoiLatitude: EditText
    private lateinit var txtPoiLongitude: EditText
    private lateinit var txtPoiParkingLatitude: EditText
    private lateinit var txtPoiParkingLongitude: EditText
    private lateinit var txtNumberOfPoints: EditText
    private lateinit var txtDiscoveryRange: EditText
    private lateinit var btnSubmit: Button
    private var curSelectedPos: Int = 0


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

        spinnerPoiType = findViewById(R.id.spinner_poi_type)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.poi_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerPoiType.adapter = adapter
        }

        if (PoiManager.curPoi != null) {
            isEditing = true
            fillWithPoiData()
        }

        btnSubmit.setOnClickListener {
            if (isEditing) {
                PoiManager.deletePoi(PoiManager.curPoi!!)
                PoiManager.addPoi(
                    PoiManager.curPoi!!.poiId,
                    spinnerPoiType.selectedItemPosition,
                    txtTitle.text.toString(),
                    txtImageUrl.text.toString(),
                    txtDescription.text.toString(),
                    txtDirections.text.toString(),
                    txtPoiLatitude.text.toString().toDouble(),
                    txtPoiLongitude.text.toString().toDouble(),
                    txtPoiParkingLatitude.text.toString().toDouble(),
                    txtPoiParkingLongitude.text.toString().toDouble(),
                    txtNumberOfPoints.text.toString().toInt(),
                    txtDiscoveryRange.text.toString().toInt()
                )
                Snackbar.make(it, "Poi Updated, Please Re-open App to See Effect.", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                // Not editing
                PoiManager.addPoi(
                    curSelectedPos,
                    txtTitle.text.toString(),
                    txtImageUrl.text.toString(),
                    txtDescription.text.toString(),
                    txtDirections.text.toString(),
                    txtPoiLatitude.text.toString().toDouble(),
                    txtPoiLongitude.text.toString().toDouble(),
                    txtPoiParkingLatitude.text.toString().toDouble(),
                    txtPoiParkingLongitude.text.toString().toDouble(),
                    txtNumberOfPoints.text.toString().toInt(),
                    txtDiscoveryRange.text.toString().toInt()
                )
                Snackbar.make(it, "Poi Created, Please Re-open App to See Effect.", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun fillWithPoiData() {
        txtTitle.setText(PoiManager.curPoi!!.title)
        txtImageUrl.setText(PoiManager.curPoi!!.img)
        txtDescription.setText(PoiManager.curPoi!!.description)
        txtDirections.setText(PoiManager.curPoi!!.directions)
        txtPoiLatitude.setText(PoiManager.curPoi!!.location.latitude.toString())
        txtPoiLongitude.setText(PoiManager.curPoi!!.location.longitude.toString())
        txtPoiParkingLatitude.setText(PoiManager.curPoi!!.parkingLocation.latitude.toString())
        txtPoiParkingLongitude.setText(PoiManager.curPoi!!.parkingLocation.longitude.toString())
        txtNumberOfPoints.setText(PoiManager.curPoi!!.poiPoints.toString())
        txtDiscoveryRange.setText(PoiManager.curPoi!!.poiRangeInM.toString())
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        curSelectedPos = id.toInt()
        Log.d(TAG, curSelectedPos.toString())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}