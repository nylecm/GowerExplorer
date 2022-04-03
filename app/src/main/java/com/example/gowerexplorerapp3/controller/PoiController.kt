package com.example.gowerexplorerapp3.controller

import android.content.Context
import android.util.Log
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.model.PoiModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

object PoiController {
    var pois: Vector<PoiModel> = Vector()

    init {
        val db = Firebase.firestore

        db.collection("poi")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("PoiController", "${document.id} => ${document.data}")
                    this.pois.add(
                        PoiModel(
                            document.data["title"].toString(),
                            document.data["description"].toString(),
                            document.data["location"] as GeoPoint,
                            PoiModel.PoiType.fromInt(((document.data["poiType"]) as Long).toInt()),
                            (document.data["poiPoints"] as Long).toInt(),
                            R.drawable.whiteford_lighthouse
                        )
                    )
                    Log.d("PoiController", "ADDED: ${document.id} => ${document.data}")

                }
            }
            .addOnFailureListener { exception ->
                Log.d("PoiController", "Error getting documents: ", exception)
            }


        // val docRef = db.collection("poi").document("5dBWvaUJAGnakZuWrGs0")


//
//
//// Source can be CACHE, SERVER, or DEFAULT.
//        val source = Source.SERVER
//
//// Get the document, forcing the SDK to use the offline cache
//        docRef.get(source).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                // Document found in the offline cache
//                val document = task.result
//                Log.d("POI CONT", "Cached document data: ${document?.data}")
//
//                this.pois.add(
//                    PoiModel(
//                        document.data?.get("title").toString(),
//                        document.data?.get("description").toString(),
//                        document.data?.get("location") as GeoPoint,
//                        PoiModel.PoiType.fromInt(document.data?.get("type") as Int),
//                        30,
//                        R.drawable.whiteford_lighthouse
//                    )
//                )
//            } else {
//                Log.d("POI CONT", "Cached get failed: ", task.exception)
//            }
//        }
    }

    fun loadData(context: Context) {
        //val db = Firebase.firestore

//        pois.add(
//            PoiModel(
//                "Port Eynon Bay",
//                context.resources.getString(R.string.desc_port_eynon),
//                GeoPoint(51.544325787706306, -4.210278367615245),
//                PoiModel.PoiType.BEACH,
//                20,
//                R.drawable.port_eynon,
//            )
//
//        )
//
//        pois.add(
//            PoiModel(
//                "Rhossili Bay",
//                context.resources.getString(R.string.desc_rhossili),
//                GeoPoint(51.57230253453608, -4.291339367942704),
//                PoiModel.PoiType.BEACH,
//                20,
//                R.drawable.rhossili,
//            )
//        )
//        pois.add(
//            PoiModel(
//                "Arthur's Stone",
//                context.resources.getString(R.string.desc_arthurs_stone),
//                51.593617098371, -4.179297132938529,
//                PoiModel.PoiType.LANDMARK,
//                15,
//                R.drawable.arthurs_stone,
//            )
//        )
//        pois.add(
//            PoiModel(
//                "Caswell",
//                context.resources.getString(R.string.desc_caswell),
//                51.57003166974094, -4.031151391066391,
//                PoiModel.PoiType.BEACH,
//                15,
//                R.drawable.caswell,
//            )
//        )
//        pois.add(
//            PoiModel(
//                "Three Cliffs",
//                context.resources.getString(R.string.desc_three_cliffs),
//                51.57303146866698, -4.1121056531239795,
//                PoiModel.PoiType.BEACH,
//                20,
//                R.drawable.three_cliffs,
//            )
//        )
//        pois.add(
//            PoiModel(
//                "Langland Bay",
//                context.resources.getString(R.string.desc_langland),
//                51.567802269570436, -4.012446099161361,
//                PoiModel.PoiType.BEACH,
//                15,
//                R.drawable.caswell,
//            )
//        )
//        pois.add(
//            PoiModel(
//                "Oystermouth Castle",
//                context.resources.getString(R.string.desc_oystermouth_castle),
//                51.57678604586335, -4.002414253222207,
//                PoiModel.PoiType.LANDMARK,
//                20,
//                R.drawable.three_cliffs,
//            )
//        )
//        pois.add(
//            PoiModel(
//                "Gower Inn",
//                context.resources.getString(R.string.desc_gower_inn),
//                51.582538374589376, -4.091597043192082,
//                PoiModel.PoiType.COMMERCE,
//                5,
//                R.drawable.gower_inn,
//            )
//        )
//
//        for (poi in pois) {
//            db.collection("poi")
//                .document(poi.description.hashCode().toString())
//                .set(poi)
//        }
    }
}
