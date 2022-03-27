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
    }
}