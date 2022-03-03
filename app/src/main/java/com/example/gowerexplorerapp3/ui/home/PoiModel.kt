package com.example.gowerexplorerapp3.ui.home

import android.content.res.Resources
import com.example.gowerexplorerapp3.R

class PoiModel {
    var title: String? = null;
    var description: String? = null;
    var img: Int = 0;
    var distance: Double = 0.0; // todo replace with location and then use distanceTo to calculate the distance.

    fun distanceTo(): String {
        return distance.toString() + "mi" // todo replace with distance unit
    }
}
