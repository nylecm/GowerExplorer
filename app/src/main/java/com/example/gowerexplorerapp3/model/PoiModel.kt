package com.example.gowerexplorerapp3.model

class PoiModel {
    lateinit var title: String
    lateinit var description: String
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    lateinit var poiType: PoiType

    //var subPois: Array<String>? = null
    var poiPoints: Int = 0
    var img: Int = 0;
    //var isPoiExplored = false

//    constructor() {
//
//    }

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
        this.latitude = latitude
        this.longitude = longitude

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
