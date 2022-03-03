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
import com.example.gowerexplorerapp3.placeholder.PlaceholderContent

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

        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

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
        return view
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

        for (i in 0..7) {
            val imageModel = PoiModel()
            imageModel.title = myTitleList[i]
            imageModel.img = myImageList[i]
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
