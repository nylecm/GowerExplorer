package com.example.gowerexplorerapp3.ui.home

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.gowerexplorerapp3.databinding.FragmentItemBinding
import org.w3c.dom.Text

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
        holder.distance.text = item.distanceTo() + " | " + item.poiPoints.toString() + " Points"
        holder.poiDescription.text = item.description
        holder.poiIsExplored.text = if (item.isPoiExplored) "Explored" else "Unexplored" // todo Does Kotlin do ternaries
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val thumbnail: ImageView = binding.imageView
        val poiNameView: TextView = binding.poiName
        val distance: TextView = binding.distance
        val poiDescription: TextView = binding.poiDescription
        val poiIsExplored: TextView = binding.poiIsExplored

        override fun toString(): String {
            return super.toString() + " '" + poiNameView.text + "'"
        }
    }
}
