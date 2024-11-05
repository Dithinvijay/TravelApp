package com.example.travelapp.ui.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.CityAdapter
import com.example.travelapp.CityItem
import com.example.travelapp.R

class CitiesFragment : Fragment() {

    private val cityItems = ArrayList<CityItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_cities, container, false)

        val recyclerView: RecyclerView = root.findViewById(R.id.recycleView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = activity?.let { CityAdapter(cityItems, it) }

        cityItems.add(CityItem(R.drawable.del, "Delhi", "India", "0", "Population: 33,807,000", "Delhi is located in Northern India, " +
                "at28.61°N 77.23°E. The city is bordered on its northern, western, and southern sides by the state of Haryana and to the east by that of Uttar Pradesh (UP)", "sdfdsfds"))
        cityItems.add(CityItem(R.drawable.hyd, "Hyderabad", "India", "1", "Population: 11,068,900", "Hyderabad is the capital of one " +
                "of the most techno savvy state in India,Telangana. The previous name of this city was Bagyanagaram.", "sssss"))
        cityItems.add(CityItem(R.drawable.mumbai, "Mumbai", "India", "2", "Population: 26,129,000", "Mumbai (also known as Bombay, the " +
                "official name until 1995) is the capital city of the Indian state of Maharashtra. Mumbai lies on the Konkan coast on the west coast of India and has a deep natural " +
                "harbour.", "kdsdsaka"))
        cityItems.add(CityItem(R.drawable.bng, "Bangalore", "India", "3", "Population: 14,008,000", "Bangalore is sometimes referred to as the" +
                " “Silicon Valley of India” (or “IT capital of India”) because of its role as the nation's leading information technology (IT) exporter.", "sdkjfbks"))
        cityItems.add(CityItem(R.drawable.lon, "London", "England", "4", "Population: 8,866,180", "It is the largest metropolis in the United Kingdom," +
                " and it is also the country's economic, transportation, and cultural centre. In addition to its history, art, and politics, London is a popular tourist destination. ", "sdkjfsk"))
        cityItems.add(CityItem(R.drawable.agra, "Agra", "India", "5", "Population: 2,498,000", "Agra is one of the most populous cities in Uttar Pradesh, " +
                "and the 24th most populous city in India. Agra is a major tourist destination because of its many Mughal-era buildings, most notably the Taj Mahal, Agra Fort and Fatehpur Sikri.", "sdsftuna"))
        cityItems.add(CityItem(R.drawable.rome, "Rome", "Italy", "6", "Population: 2,860,009", "Rome is the capital of Italy and the most populous and extensive " +
                "municipality, ranking among the major European capitals by territory size. It stands in the central-western portion of the Italian Peninsula, on the Tiber River within the Lazio region. " , "sdsftunayaka"))
        cityItems.add(CityItem(R.drawable.kolkata, "Kolkata", "India", "7", "Population: 14,035,959", "Kolkata- The Capital city, popularly known as The 'City of Joy'." +
                " The city is dipped in history, art & culture, sports and socio-cultural activities. This was the erstwhile capital of the British Raj, and thus has architectural gems strewn all around.", "sdsftunayaka"))

        return root
    }
}
