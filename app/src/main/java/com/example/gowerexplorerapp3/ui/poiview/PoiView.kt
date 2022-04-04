package com.example.gowerexplorerapp3.ui.poiview

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.gowerexplorerapp3.R
import com.example.gowerexplorerapp3.databinding.ActivityPoiViewBinding
import com.squareup.picasso.Picasso

class PoiView : AppCompatActivity() {

    private lateinit var binding: ActivityPoiViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPoiViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val poiTitle = intent.getStringExtra("title")
        binding.toolbarLayout.title = poiTitle

        Picasso.get()
            .load(intent.getStringExtra("imgUrl"))
            .resize(1080, 600)
            .centerCrop()
            .into(findViewById<ImageView>(R.id.mainImage));

        findViewById<TextView>(R.id.textview).text = intent.getStringExtra("description")

        findViewById<Button>(R.id.navigateTo).setOnClickListener {
            val gmmIntentUri =
                Uri.parse("google.navigation:q=$poiTitle,+Swansea")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }
}
