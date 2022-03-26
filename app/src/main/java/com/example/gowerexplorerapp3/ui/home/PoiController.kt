package com.example.gowerexplorerapp3.ui.home

import com.example.gowerexplorerapp3.R
import java.util.*

object PoiController {
    lateinit var pois: Vector<PoiModel>

    init {
        // load data from database for now hardcoded:
        pois = Vector()
        pois.add(
            PoiModel(
                "Whiteford Lighthouse",
                "It is an unusual cast-iron lighthouse built in 1865 to a design by John Bowen (1825–1873) of Llanelli, by the Llanelli Harbour and Burry Navigation Commissioners to mark the shoals of Whiteford Point, replacing an earlier piled structure of 1854, of which nothing remains",
                51.65308998230439, -4.250174428774476,
                arrayOf(PoiModel.PoiType.LANDMARK),
                null,
                30,
                R.drawable.whiteford_lighthouse,
                true
            )
        )
        //pois.add()
    }
}