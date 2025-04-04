package com.jerry.presentation.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jerry.domain.models.ForecastDay
import com.jerry.presentation.R
import com.jerry.presentation.imageLoader
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SevenDayForecast(
    forecastData: List<ForecastDay>,
    formattedDates: List<String>?
) {

    Text(
        text = "7 Days",
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(3.dp)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(30.dp),
    ) {
        for (i in forecastData.indices) {
            // Check if formattedDates is not null and its size matches forecastData
            if (formattedDates != null && formattedDates.size == forecastData.size) {
                // Access the corresponding data and date using the index
                WeatherCard(
                    data = forecastData[i],
                    date = formattedDates[i]
                )
                Spacer(modifier = Modifier.height(8.dp)) // Adjust spacing between cards
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherCard(data: ForecastDay, date: String) {

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        // Display the date
        if (isToday(date)) {
            Text(text = "Today" )
        }
        else{
            Text(text = date )
        }
        Spacer(modifier = Modifier.height(4.dp))

        // Display average Humidity
        Row {
            Image(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Text(text = data.day.avghumidity.toString())
        }
        Spacer(modifier = Modifier.height(4.dp))

        // Display the weather condition icon
        Image(
            painter = imageLoader(imageUrl = data.day.condition.icon).also {
                Log.e(
                    "imageUrl",
                    data.day.condition.icon
                )
            },
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        // Display max and min temperature
        Text(
            text = "${data.day.maxtemp_c}°C ${data.day.mintemp_c}°C"
        )
    }

}


@RequiresApi(Build.VERSION_CODES.O)
private fun isToday(date: String): Boolean {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("EEEE")
    val formattedCurrentDate = currentDate.format(formatter)
    return date.equals(formattedCurrentDate, ignoreCase = true)
}