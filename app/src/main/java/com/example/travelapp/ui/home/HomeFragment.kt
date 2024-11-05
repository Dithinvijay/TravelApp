package com.example.travelapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.travelapp.R

class HomeFragment : Fragment() {

    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        button2 = view.findViewById(R.id.button2)
        button3 = view.findViewById(R.id.button3)
        button4 = view.findViewById(R.id.button4)

        button2.setOnClickListener {
            findNavController().navigate(R.id.navigation_country)
        }

        button3.setOnClickListener {
            findNavController().navigate(R.id.navigation_favourite)
        }

        button4.setOnClickListener {
            findNavController().navigate(R.id.navigation_cities)
        }

        return view
    }
}
