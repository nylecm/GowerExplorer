package com.example.gowerexplorerapp3.model

import com.example.gowerexplorerapp3.controller.PoiManager
import java.util.*


class UserModel {
    var userName: String = ""
    var isAdmin: Boolean = false
    var numberOfPoints: Int = 0
    var poisExplored: ArrayList<String> = ArrayList()

    /**
     * User model constructor that can construct an arbitrary user based of set params.
     */
    constructor(userName: String, isAdmin: Boolean, numberOfPoints: Int, poisExplored: ArrayList<String>) {
        this.userName = userName
        this.isAdmin = isAdmin
        this.numberOfPoints = numberOfPoints
        this.poisExplored = poisExplored
    }

    private fun addToDB(uid: String) {

    }

    fun prepareEmailOfExploredPois() : String {
        val poiNames: ArrayList<String> = ArrayList()
        for (poiId in poisExplored) {
            for (poi in PoiManager.pois)
                if (poi.poiId == poiId) {
                    poiNames.add(poi.title)
                }
        }
        var emailContent = "You have explored the following PoIs:\n\n"

        for (poiName in poiNames) {
            emailContent += "- $poiName,\n"
        }
        emailContent += "\n\n\n Kind Regards,\n\nGower Explorer"
        return emailContent
    }
}

