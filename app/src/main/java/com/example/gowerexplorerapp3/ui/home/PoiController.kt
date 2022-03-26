package com.example.gowerexplorerapp3.ui.home

import android.content.Context
import com.example.gowerexplorerapp3.R
import java.util.*

object PoiController {
    var pois: Vector<PoiModel> = Vector()

    init {
        // TODO database
    }

    fun loadData(context: Context) {
        pois.clear()
        pois.add(
            PoiModel(
                "Whiteford Lighthouse",
                context.resources.getString(R.string.desc_whiteford_lighthouse),
                51.65308998230439, -4.250174428774476,
                arrayOf(PoiModel.PoiType.LANDMARK),
                null,
                30,
                R.drawable.whiteford_lighthouse,
                true
            )
        )
    }
}