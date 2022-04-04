package com.example.gowerexplorerapp3

import android.os.Bundle
import android.widget.TextView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.gowerexplorerapp3.databinding.ActivityPoiViewBinding

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

        findViewById<TextView>(R.id.textview).text = intent.getStringExtra("title")
    }
}