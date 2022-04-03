package com.example.gowerexplorerapp3.model

import com.google.firebase.firestore.GeoPoint

class PoiModel {
    var title: String
    var description: String
    var location: GeoPoint
    var poiType: PoiType
    var poiPoints: Int = 0
    var img: Int = 0;
    //var isPoiExplored = false

//    constructor() {
//
//    }

    constructor(
        title: String,
        description: String,
        location: GeoPoint,
        poiType: PoiType,
        poiPoints: Int,
        img: Int,
    ) {
        this.title = title
        this.description = description
        this.location = location
        if (poiType.equals("")) {
            this.poiType = poiType
        } else {
            this.poiType = PoiType.MISC
        }
        this.poiType = poiType
        this.poiPoints = poiPoints
        this.img = img
    }

    // TODO remove lagacy constructor once Firebase integration is complete
    constructor(
        title: String,
        description: String,
        latitude: Double,
        longitude: Double,
        poiType: PoiType,
        poiPoints: Int,
        img: Int,
    ) {
        this.title = title
        this.description = description
        this.location = GeoPoint(latitude, longitude)
        if (poiType.equals("")) {
            this.poiType = poiType
        } else {
            this.poiType = PoiType.MISC
        }
        this.poiType = poiType
        this.poiPoints = poiPoints
        this.img = img
    }


    enum class PoiType {
        BEACH,
        NATURE,
        LANDMARK,
        COMMERCE,
        MISC
    }

    fun distanceTo(): Double {
        // TODO not yet implemented
        return 420.0;
    }
}
