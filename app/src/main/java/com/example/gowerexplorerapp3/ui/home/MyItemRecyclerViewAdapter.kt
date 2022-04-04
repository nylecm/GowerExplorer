package com.example.gowerexplorerapp3.ui.home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.gowerexplorerapp3.R

import com.example.gowerexplorerapp3.databinding.FragmentItemBinding
import com.example.gowerexplorerapp3.model.PoiModel
import com.squareup.picasso.Picasso

/**
 * [RecyclerView.Adapter] that can display a [PoiModel].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val values: List<PoiModel>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.poiNameView.text = item.title
        //holder.thumbnail.setImageResource(item.img)
        Picasso.get()
            .load(item.imgUrl)
            .resize(300, 200)
            .centerCrop()
            .into(holder.thumbnail);

        holder.distance.text =
            item.distanceTo().toString() + " | " + item.poiPoints.toString() + " Points"
        holder.poiDescription.text = item.description
        holder.poiIsExplored.text = "Unexplored"

//        if (item.isPoiExplored) { TODO new way of checking if it is explored
//            holder.poiIsExplored.text = "Explored"
//        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val thumbnail: ImageView = binding.imageView
        val poiNameView: TextView = binding.poiName
        val distance: TextView = binding.distance
        val poiDescription: TextView = binding.poiDescription
        val poiIsExplored: TextView = binding.poiIsExplored
        val poiSubPoints: TextView = binding.subPois

        override fun toString(): String {
            return super.toString() + " '" + poiNameView.text + "'"
        }
    }
}
