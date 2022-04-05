package com.example.gowerexplorerapp3.model

import com.google.firebase.firestore.GeoPoint
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class PoiModel {
    var title: String
    var description: String
    var location: GeoPoint
    var parkingLocation: GeoPoint
    var directions: String
    var poiType: PoiType
    var poiPoints: Int = 0
    var imgUrl: String =
        "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Question_mark_%28black%29.svg/1024px-Question_mark_%28black%29.svg.png";
    var poiRangeInM: Int = 100

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

    fun distanceTo(compLocation: GeoPoint): Double {
        return distBetween(
            location.latitude,
            location.longitude,
            compLocation.latitude,
            compLocation.longitude
        )
    }

    // dist between function inspired by :
    // https://stackoverflow.com/questions/8725283/distance-between-geopoints (User: Lucifer)
    // START
    private fun distBetween(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
        val earthRadius = 3958.75
        val dLat = Math.toRadians((lat2 - lat1))
        val dLng = Math.toRadians((lng2 - lng1))
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLng / 2) * sin(dLng / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val dist = earthRadius * c
        val meterConversion = 1609
        return (dist * meterConversion)
    }
    // END
}
