package com.example.gowerexplorerapp3.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.recyclerview.widget.RecyclerView
import com.example.gowerexplorerapp3.controller.PoiController
import com.example.gowerexplorerapp3.ui.poiview.PoiView
import com.example.gowerexplorerapp3.databinding.FragmentItemBinding
import com.example.gowerexplorerapp3.model.PoiModel
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt


/**
 * [RecyclerView.Adapter] that can display a [PoiModel].
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
            item.distanceTo(MapHome.lastUserLocation).roundToInt().toString() + "m | " + item.poiPoints.toString() + " Points"
        holder.poiDescription.text = item.description
        holder.poiIsExplored.text = "Unexplored"

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.getContext(), PoiView::class.java)
            PoiController.curPoi = item
//            intent.putExtra("title", item.title)
//            intent.putExtra("description", item.description)
//            intent.putExtra("imgUrl", item.imgUrl)
//            intent.putExtra("parkingLocation", parkingLocation)
//            intent.putExtra("directions", item.directions)

            holder.itemView.context.startActivity(intent)
        }
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
