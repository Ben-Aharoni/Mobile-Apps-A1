package com.example.hw1.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hw1.R
import com.google.android.material.textview.MaterialTextView

class MapFragment : Fragment() {

    private lateinit var map_LBL_title: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_map, container, false)
        findViews(v)
        return v
    }

    private fun findViews(v: View) {
        map_LBL_title = v.findViewById(R.id.map_LBL_title)
    }

    fun zoom(lat: Double, lon: Double) {
        map_LBL_title.text = buildString {
            append("📍\n")
            append(lat)
            append(",\n")
            append(lon)
        }
    }


}