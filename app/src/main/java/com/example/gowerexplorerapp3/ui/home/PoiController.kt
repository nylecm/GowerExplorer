package com.example.gowerexplorerapp3.ui.home

import android.content.Context
import com.example.gowerexplorerapp3.R
import java.util.*

object PoiController {
    var pois: Vector<PoiModel> = Vector()

    init {
        // TODO database connection
    }

    fun loadData(context: Context) {
        pois.clear()
        pois.add(
            PoiModel(
                "Whiteford Lighthouse",
                context.resources.getString(R.string.desc_whiteford_lighthouse),
                51.65308998230439, -4.250174428774476,
                PoiModel.PoiType.LANDMARK,
                null,
                30,
                R.drawable.whiteford_lighthouse,
                true
            )
        )
        pois.add(
            PoiModel(
                "Port Eynon Bay",
                context.resources.getString(R.string.desc_port_eynon),
                51.544325787706306, -4.210278367615245,
                PoiModel.PoiType.BEACH,
                null,
                20,
                R.drawable.port_eynon,
                false
            )
        )
        pois.add(
            PoiModel(
                "Rhossili Bay",
                context.resources.getString(R.string.desc_rhossili),
                51.57230253453608, -4.291339367942704,
                PoiModel.PoiType.BEACH,
                null,
                20,
                R.drawable.rhossili,
                true
            )
        )
        pois.add(
            PoiModel(
                "Arthur's Stone",
                context.resources.getString(R.string.desc_arthurs_stone),
                51.593617098371, -4.179297132938529,
                PoiModel.PoiType.LANDMARK,
                null,
                15,
                R.drawable.arthurs_stone,
                true
            )
        )
        pois.add(
            PoiModel(
                "Caswell",
                context.resources.getString(R.string.desc_caswell),
                51.57003166974094, -4.031151391066391,
                PoiModel.PoiType.BEACH,
                null,
                15,
                R.drawable.caswell,
                true
            )
        )
        pois.add(
            PoiModel(
                "Three Cliffs",
                context.resources.getString(R.string.desc_three_cliffs),
                51.57303146866698, -4.1121056531239795,
                PoiModel.PoiType.BEACH,
                null,
                20,
                R.drawable.three_cliffs,
                true
            )
        )
        pois.add(
            PoiModel(
                "Langland Bay",
                context.resources.getString(R.string.desc_langland),
                51.567802269570436, -4.012446099161361,
                PoiModel.PoiType.BEACH,
                null,
                15,
                R.drawable.caswell,
                true
            )
        )
        pois.add(
            PoiModel(
                "Oystermouth Castle",
                context.resources.getString(R.string.desc_oystermouth_castle),
                51.57678604586335, -4.002414253222207,
                PoiModel.PoiType.LANDMARK,
                null,
                20,
                R.drawable.three_cliffs,
                true
            )
        )
        pois.add(
            PoiModel(
                "Gower Inn",
                context.resources.getString(R.string.desc_gower_inn),
                51.582538374589376, -4.091597043192082,
                PoiModel.PoiType.COMMERCE,
                null,
                5,
                R.drawable.gower_inn,
                false
            )
        )
    }
}