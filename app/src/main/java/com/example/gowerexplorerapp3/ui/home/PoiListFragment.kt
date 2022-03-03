package com.example.gowerexplorerapp3.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gowerexplorerapp3.R

/**
 * A simple [Fragment] subclass.
 */
class PoiListFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poi_list, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val recycler_view = view?.findViewById<RecyclerView>(R.id.recycler_view)
        recycler_view.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = RecyclerAdapter()
        }
    }
}

//package com.example.gowerexplorerapp3.ui.home
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.gowerexplorerapp3.PoiModel
//import com.example.gowerexplorerapp3.R
//import com.example.gowerexplorerapp3.databinding.FragmentPoiListBinding
//
//class PoiListFragmentOrig : Fragment() {
//
//    private var _binding: FragmentPoiListBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)
//
//        _binding = FragmentPoiListBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        //my code
//
////        super.onCreate(savedInstanceState)
////        //setContentView(R.layout.fragment_poi_list)
////
////        val imageModelArrayList = populateList()
////
////        val recyclerView = binding.myRecyclerView
////
////        val layoutManager = LinearLayoutManager(this)
////        recyclerView.layoutManager = layoutManager
////        val mAdapter = MyAdapter(imageModelArrayList)
////        recyclerView.adapter = mAdapter
//
//        return root
//    }
//
//    private fun populateList(): ArrayList<PoiModel> {
//        val list = ArrayList<PoiModel>()
//        val myImageList = arrayOf(R.drawable.whiteford_lighthouse, R.drawable.port_eynon,
//            R.drawable.rhossili, R.drawable.arthurs_stone, R.drawable.caswell,
//            R.drawable.three_cliffs, R.drawable.langland, R.drawable.oystermouth_castle)
//        val myTitleList = arrayOf("whiteford_lighthouse", "port_eynon", "rhossili", "arthurs_stone",
//            "caswell", "three_cliffs", "langland", "oystermouth_castle")
//
//        for (i in 0..7) {
//            val imageModel = PoiModel()
//            imageModel.title = myTitleList[i]
//            imageModel.img = myImageList[i]
//            list.add(imageModel)
//        }
//
//        list.sortBy { list -> list.title }
//        return list
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}