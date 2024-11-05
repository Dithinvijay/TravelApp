package com.example.travelapp.ui.favourites

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travelapp.FavAdapter
import com.example.travelapp.FavItem
import com.example.travelapp.R
import com.example.travelapp.TravelDB

class FavouriteFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var travelDB: TravelDB
    private val favItemList = mutableListOf<FavItem>()
    private lateinit var favAdapter: FavAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favourite, container, false)

        travelDB = TravelDB(requireActivity())
        recyclerView = root.findViewById(R.id.recycleView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        loadData()

        return root
    }

    @SuppressLint("Range")
    private fun loadData() {
        favItemList.clear() // Clear the list before loading new data
        val db: SQLiteDatabase = travelDB.readableDatabase
        val cursor: Cursor = travelDB.selectAllFavoriteList()

        cursor.use { // Automatically closes the cursor
            while (it.moveToNext()) {
                val title = it.getString(it.getColumnIndex(TravelDB.ITEM_TITLE))
                val id = it.getString(it.getColumnIndex(TravelDB.KEY_ID))
                val image = it.getInt(it.getColumnIndex(TravelDB.ITEM_IMAGE))
                val favItem = FavItem(title, id, image)
                favItemList.add(favItem)
            }
        }

        favAdapter = FavAdapter(requireActivity(), favItemList)
        recyclerView.adapter = favAdapter
        cursor.close()
        db.close()
    }
}
