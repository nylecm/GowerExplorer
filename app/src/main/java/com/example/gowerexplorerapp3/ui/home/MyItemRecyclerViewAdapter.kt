package com.example.gowerexplorerapp3.ui.home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.gowerexplorerapp3.databinding.FragmentItemBinding
import com.example.gowerexplorerapp3.model.PoiModel

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
        holder.thumbnail.setImageResource(item.img)
        holder.distance.text = item.distanceTo().toString() + " | " + item.poiPoints.toString() + " Points"
        holder.poiDescription.text = item.description

        if (item.isPoiExplored) {
            holder.poiIsExplored.text = "Explored"
        }
        // todo Does Kotlin do ternaries

        if (item.subPois != null) {
            var subPointsString = "Sub-PoIs: "
            for (i in item.subPois!!.indices) {
                subPointsString += item.subPois!![i]
                if (i < item.subPois!!.size - 2)
                    subPointsString += ", "
                else if (i == item.subPois!!.size - 2)
                    subPointsString += ", and "
                else
                    subPointsString += "."
            }
            holder.poiSubPoints.maxLines = 10
            holder.poiSubPoints.setPadding(0,8,0,0)
            holder.poiSubPoints.text = subPointsString
        }
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
