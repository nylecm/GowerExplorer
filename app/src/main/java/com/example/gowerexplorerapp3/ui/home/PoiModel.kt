package com.example.gowerexplorerapp3.ui.home

import android.location.Location
import java.util.*

class PoiModel {
    var title: String? = null
    var description: String? = null
    var longitude: Double = 0.0
    var latitude: Double = 0.0
    lateinit var poiType: Array<PoiType>
    var subPois: Array<String>? = null
    var poiPoints: Int = 0
    var img: Int = 0;
    var distance: Double =
        0.0; // todo replace with location and then use distanceTo to calculate the distance.
    var isPoiExplored = false

    constructor() {

    }

    constructor(
        title: String?,
        description: String?,
        longitude: Double,
        latitude: Double,
        poiType: Array<PoiType>,
        subPois: Array<String>,
        poiPoints: Int,
        img: Int,
        distance: Double,
        isPoiExplored: Boolean
    ) {
        this.title = title
        this.description = description
        this.longitude = longitude
        this.latitude = latitude

        if (!poiType.isEmpty()) {
            this.poiType = poiType
        } else {
            this.poiType = arrayOf(PoiType.MISC)
        }
        this.poiType = poiType
        this.subPois = subPois
        this.poiPoints = poiPoints
        this.img = img
        this.distance = distance
        this.isPoiExplored = isPoiExplored
    }

    enum class PoiType {
        BEACH,
        NATURE,
        LANDMARK,
        COMMERCE,
        MISC
    }


    fun distanceTo(): String {
        return distance.toString() + "mi" // todo replace with distance unit
    }
}
