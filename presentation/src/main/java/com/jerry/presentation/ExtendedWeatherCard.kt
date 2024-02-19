package com.jerry.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jerry.domain.models.WeatherDomain

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExtendedWeatherCard(
    weatherData: WeatherDomain,
    imageUrl: String,
    formatDay: String?,
    formatTime: String?,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {

            //Image loader
            Image(
                painter = imageLoader(imageUrl = imageUrl),
                contentDescription = "weather image",
                modifier = Modifier.size(200.dp)
            )

            // Display Temperature
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "${weatherData.current.temp_c}\u00B0C",
                    style = TextStyle(fontSize = 70.sp),
                    color = MaterialTheme.colorScheme.onSurface
                )

//                Spacer(modifier = Modifier.width(8.dp))

//                Text(
//                    text = "${weatherData.current.temp_f}\u00B0F",
//                    style = TextStyle(fontSize = 18.sp),
//                    color = MaterialTheme.colorScheme.onSurface
//                )
            }

            //weather condition
            Text(
                text = weatherData.current.condition.text,
                style = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(46.dp))
        }

    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Row {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "Location",
                tint = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = weatherData.location.country,
                style = TextStyle(fontSize = 18.sp),
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Display Date
        Row(

        ) {
            if (formatDay != null) {
                Text(
                    text = formatDay,
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            //formatted time
            if (formatTime != null) {
                Text(
                    text = formatTime,
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

    }
}