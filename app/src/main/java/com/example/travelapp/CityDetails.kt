package com.example.travelapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class CityDetails : AppCompatActivity(), View.OnClickListener {

    private lateinit var imageView1: ImageView
    private lateinit var title1TextView: TextView
    private lateinit var populationTextView: TextView
    private lateinit var citydescTextView: TextView
    private lateinit var airportTextView: TextView
    private lateinit var viewTemp: TextView
    private lateinit var viewDesc: TextView
    private lateinit var viewWeather: ImageButton
    private lateinit var addbtn: FloatingActionButton
    private lateinit var city: String
    private var imageResourse1: Int = 0
    private lateinit var population: String
    private lateinit var citydesc: String
    private lateinit var airport: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citydetails)

        imageView1 = findViewById(R.id.image1)
        title1TextView = findViewById(R.id.title1TextView)
        populationTextView = findViewById(R.id.populationTextView)
        citydescTextView = findViewById(R.id.citydescTextView)
        airportTextView = findViewById(R.id.airportTextView)
        viewTemp = findViewById(R.id.temp)
        viewDesc = findViewById(R.id.desc)
        viewWeather = findViewById(R.id.wheather_image)

        city = intent.getStringExtra("title1TextView") ?: ""
        imageResourse1 = intent.getIntExtra("image1", 0)
        population = intent.getStringExtra("populationTextView") ?: ""
        citydesc = intent.getStringExtra("citydescTextView") ?: ""
        airport = intent.getStringExtra("airportTextView") ?: ""

        title1TextView.text = city
        imageView1.setImageResource(imageResourse1)
        populationTextView.text = population
        citydescTextView.text = citydesc
        airportTextView.text = airport

        viewWeather.setOnClickListener{
            val weatherUrl="https://www.google.com/search?q=weather+of+$city"
            val weatherIntent =Intent(Intent.ACTION_VIEW,Uri.parse(weatherUrl))
            startActivity(weatherIntent)
        }

        val citymap :Button = findViewById(R.id.locbutton)
        val airport :Button=findViewById(R.id.airportbutton)
        val citywiki :Button=findViewById(R.id.urlbutton)

        citymap.setOnClickListener {
            val cityLocationUrl = "https://www.google.com/maps/search/$city"
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(cityLocationUrl))
            startActivity(mapIntent)

        }
        airport.setOnClickListener {
            val airportLocationUrl = "https://www.google.com/maps/search/nearest+airport+$city"
            val airportMapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(airportLocationUrl))
            startActivity(airportMapIntent)
        }
        citywiki.setOnClickListener {
            val wikiUrl = "https://en.wikipedia.org/wiki/$city"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl))
            startActivity(browserIntent)
        }


        fetchWeather()

        addbtn = findViewById(R.id.notepadbutton)
        addbtn.setOnClickListener(this)
    }

    private fun fetchWeather() {
        val urlString = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=8d861b87d2e1b22f60f62051142fa763&units=metric"
        thread {
            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()

                    parseWeatherData(response.toString())
                } else {
                    runOnUiThread {
                        Toast.makeText(this@CityDetails, "Failed to fetch weather data", Toast.LENGTH_SHORT).show()
                    }
                }
                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@CityDetails, "Failed to fetch weather data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun parseWeatherData(responseData: String) {
        try {
            val json = JSONObject(responseData)
            val array = json.getJSONArray("weather")
            val weatherObject = array.getJSONObject(0)

            val description = weatherObject.getString("description")
            val icon = weatherObject.getString("icon")

            val tempObj = json.getJSONObject("main")
            val temperature = tempObj.getDouble("temp")

            val temps = "${Math.round(temperature)} °C"
            setText(viewTemp, temps)
            setText(viewDesc, description)
            setImage(viewWeather, icon)
        } catch (e: JSONException) {
            e.printStackTrace()
            runOnUiThread {
                Toast.makeText(this@CityDetails, "Failed to parse weather data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setText(text: TextView, value: String) {
        runOnUiThread {
            text.text = value
        }
    }

    private fun setImage(imageView: ImageView, value: String) {
        runOnUiThread {
            when (value) {
                "01d" -> imageView.setImageResource(R.drawable.d01d)
                "01n" -> imageView.setImageResource(R.drawable.d01d)
                "02d" -> imageView.setImageResource(R.drawable.d02d)
                "02n" -> imageView.setImageResource(R.drawable.d02d)
                "03d" -> imageView.setImageResource(R.drawable.d03d)
                "03n" -> imageView.setImageResource(R.drawable.d03d)
                "04d" -> imageView.setImageResource(R.drawable.d04d)
                "04n" -> imageView.setImageResource(R.drawable.d04d)
                "09d" -> imageView.setImageResource(R.drawable.d09d)
                "09n" -> imageView.setImageResource(R.drawable.d09d)
                "10d" -> imageView.setImageResource(R.drawable.d10d)
                "10n" -> imageView.setImageResource(R.drawable.d10d)
                "11d" -> imageView.setImageResource(R.drawable.d11d)
                "11n" -> imageView.setImageResource(R.drawable.d11d)
                "13d" -> imageView.setImageResource(R.drawable.d13d)
                "13n" -> imageView.setImageResource(R.drawable.d13d)
                else -> imageView.setImageResource(R.drawable.d01d)
            }
        }
    }

    override fun onClick(v: View) {
        val intent = Intent(this@CityDetails, NoteActivity::class.java)
        startActivity(intent)
    }

}
