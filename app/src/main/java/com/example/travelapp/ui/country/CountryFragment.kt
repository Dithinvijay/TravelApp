package com.example.travelapp.ui.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.CountryAdapter
import com.example.travelapp.CountryItem
import com.example.travelapp.R

class CountryFragment : Fragment() {

    private val countryItems = ArrayList<CountryItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_country, container, false)

        val recyclerView = root.findViewById<RecyclerView>(R.id.recycleView)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = activity?.let { CountryAdapter(countryItems, it) }
        recyclerView.layoutManager = LinearLayoutManager(activity)

        countryItems.apply {
            add(CountryItem(R.drawable.ind, "India", "0", "0"))
            add(CountryItem(R.drawable.sri, "Sri Lanka", "1", "0"))
            add(CountryItem(R.drawable.chi, "China", "2", "0"))
            add(CountryItem(R.drawable.ame, "America", "3", "0"))
            add(CountryItem(R.drawable.jap, "Japan", "4", "0"))
            add(CountryItem(R.drawable.eng, "England", "5", "0"))
            add(CountryItem(R.drawable.aus, "Australia", "6", "0"))
            add(CountryItem(R.drawable.ban, "Bangladesh", "7", "0"))
            add(CountryItem(R.drawable.nz, "New Zealand", "8", "0"))
        }

        return root
    }
}
