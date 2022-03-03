package com.example.gowerexplorerapp3.ui.home

class PoiModel {
    var subPois: Array<String>? = null;
    var poiPoints: Int = 0
    var title: String? = null
    var description: String? = null
    var img: Int = 0;
    var distance: Double = 0.0; // todo replace with location and then use distanceTo to calculate the distance.
    var isPoiExplored = false

    fun distanceTo(): String {
        return distance.toString() + "mi" // todo replace with distance unit
    }
}
