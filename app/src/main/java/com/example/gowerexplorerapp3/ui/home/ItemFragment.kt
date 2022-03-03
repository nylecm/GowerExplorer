package com.example.gowerexplorerapp3.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.placeholder.PlaceholderContent
import com.google.android.gms.maps.SupportMapFragment
import kotlin.reflect.typeOf

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        arguments?.let {
//            columnCount = it.getInt(7)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val content = populate()

        val ui = inflater.inflate(R.layout.fragment_item_list, container, false)
        val view = ui.findViewById<RecyclerView>(R.id.list)
        Log.i("ddd", view::class.java.typeName)
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyItemRecyclerViewAdapter(content)
            }
        }
        return ui
    }

    private fun populate(): ArrayList<PoiModel> {
        val list = ArrayList<PoiModel>()
        ArrayList<PoiModel>()
        val myImageList = arrayOf(
            R.drawable.whiteford_lighthouse, R.drawable.port_eynon,
            R.drawable.rhossili, R.drawable.arthurs_stone, R.drawable.caswell,
            R.drawable.three_cliffs, R.drawable.langland, R.drawable.oystermouth_castle
        )
        val myTitleList = arrayOf(
            "whiteford_lighthouse", "port_eynon", "rhossili", "arthurs_stone",
            "caswell", "three_cliffs", "langland", "oystermouth_castle"
        )
        val myDescriptionList = arrayOf(
            getText(R.string.desc_whiteford_lighthouse), getText(R.string.desc_port_eynon), getText(R.string.desc_rhossili), getText(R.string.desc_arthurs_stone),
            getText(R.string.desc_caswell), getText(R.string.desc_three_cliffs), getText(R.string.desc_langland), getText(R.string.desc_oystermouth_castle)
        )
        val myDistanceList = arrayOf(
            10.5,5.2,4.2,1.2,5.3,14.1,3.8,2.3
        )
        val myPointsList = arrayOf(
            30,20,20,15,15,20,15,10
        )
        val myExploredList = arrayOf(
            true,false,true,false,true,false,true,false
        )
        val mySubPointList = arrayOf(
            arrayOf("Northernmost Point"), arrayOf("Southernmost Point"), arrayOf("Worm's Head", "Shipwreck", "Coastguard's House") ,null, null,null,null,null
        )

        for (i in 0..7) {
            val imageModel = PoiModel()
            imageModel.title = myTitleList[i]
            imageModel.img = myImageList[i]
            imageModel.description = myDescriptionList[i].toString()
            imageModel.distance = myDistanceList[i]
            imageModel.poiPoints = myPointsList[i]
            imageModel.isPoiExplored = myExploredList[i]
            imageModel.subPois = mySubPointList[i]
            list.add(imageModel)
        }

        list.sortBy { list -> list.title }
        return list
    }
}

//    companion object {
//
//        // TODO: Customize parameter argument names
//        const val ARG_COLUMN_COUNT = "column-count"
//
//        // TODO: Customize parameter initialization
//        @JvmStatic
//        fun newInstance(columnCount: Int) =
//            ItemFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_COLUMN_COUNT, columnCount)
//                }
//            }
//    }
