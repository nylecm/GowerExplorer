package com.example.gowerexplorerapp3.model

import com.google.firebase.firestore.GeoPoint
import java.sql.Types

class PoiModel {
    var title: String
    var description: String
    var location: GeoPoint
    var parkingLocation: GeoPoint
    var directions: String
    var poiType: PoiType
    var poiPoints: Int = 0
    var imgUrl: String = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Question_mark_%28black%29.svg/1024px-Question_mark_%28black%29.svg.png";
    //var isPoiExplored = false

//    constructor() {
//
//    }

    constructor(
        title: String,
        description: String,
        location: GeoPoint,
        parkingLocation: GeoPoint,
        directions: String,
        poiType: PoiType,
        poiPoints: Int,
        imgUrl: String,
    ) {
        this.title = title
        this.description = description
        this.location = location
        this.parkingLocation = parkingLocation
        this.directions = directions
        if (poiType.equals("")) {
            this.poiType = poiType
        } else {
            this.poiType = PoiType.MISC
        }
        this.poiType = poiType
        this.poiPoints = poiPoints
        this.imgUrl = imgUrl
    }

//    // TODO remove lagacy constructor once Firebase integration is complete
//    constructor(
//        title: String,
//        description: String,
//        latitude: Double,
//        longitude: Double,
//        poiType: PoiType,
//        poiPoints: Int,
//        imgUrl: String,
//    ) {
//        this.title = title
//        this.description = description
//        this.location = GeoPoint(latitude, longitude)
//        if (poiType.equals("")) {
//            this.poiType = poiType
//        } else {
//            this.poiType = PoiType.MISC
//        }
//        this.poiType = poiType
//        this.poiPoints = poiPoints
//        this.imgUrl = imgUrl
//    }


    enum class PoiType(val value: Int) {
        BEACH(0),
        NATURE(1),
        LANDMARK(2),
        COMMERCE(3),
        MISC(4);

        companion object {
            fun fromInt(value: Int) = values().first { it.value == value }
        }
    }

    fun distanceTo(): Double {
        // TODO not yet implemented
        return 420.0;
    }
}
