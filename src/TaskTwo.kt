
import com.google.gson.Gson
import dataclass.ApiResponse
import dataclass.WeatherData
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val apiUrl = "https://api.openweathermap.org/data/2.5/forecast?lat=-6.202244293617767&lon=106.7392343527677&appid=c679b8786c85aa632fd812cec8e9d2b8&units=metric" //API endpoint

    fetchData(apiUrl)
}

fun fetchData(url: String) {
    val urlConnection = URL(url).openConnection() as HttpURLConnection
    urlConnection.requestMethod = "GET"
    urlConnection.connect()

    if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
        val inputStream = urlConnection.inputStream
        val rawResponse = inputStream.reader().readText()

        val gson = Gson()
        val response = gson.fromJson(rawResponse,ApiResponse::class.java)

        val filteredData = removeSameDateInData(response.list)

         // show data
        println("Weather Forecast")
        filteredData.forEach { data->
            println(formatUnixTime(data.dt, "E, dd MMM yyyy") + ": " + data.main.temp + " Celcius")
        }

    } else {
        println(urlConnection.responseMessage)
    }

    urlConnection.disconnect()
}

fun formatUnixTime(unixTime: Long, pattern: String): String {
    val sdf = SimpleDateFormat(pattern)
    val date = Date(unixTime*1000L)
    return sdf.format(date)
}

fun removeSameDateInData(listData: List<WeatherData>): List<WeatherData> {
    val filterDays = arrayListOf<WeatherData>()
    val sameDate = arrayListOf<String>()
    listData.forEach {item->
        val day = formatUnixTime(item.dt, "dd")

        if (!sameDate.contains(day)) {
            filterDays.add(item)
            sameDate.add(day)
        }
    }

    return filterDays.take(5)
}