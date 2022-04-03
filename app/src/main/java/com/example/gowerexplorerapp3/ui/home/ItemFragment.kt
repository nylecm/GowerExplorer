package com.example.gowerexplorerapp3.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.controller.PoiController
import com.example.gowerexplorerapp3.model.PoiModel
import kotlin.collections.ArrayList

/**
 * A fragment representing a list of Items.
 */
class ItemFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val content = populate()

        val ui = inflater.inflate(R.layout.fragment_item_list, container, false)
        val view = ui.findViewById<RecyclerView>(R.id.list)
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

        PoiController.loadData(requireContext()) // TODO load data when main activity stats up instead

        for (poi in PoiController.pois) {
            list.add(poi)
        }
        //list.sortBy { list -> list.distance }
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
