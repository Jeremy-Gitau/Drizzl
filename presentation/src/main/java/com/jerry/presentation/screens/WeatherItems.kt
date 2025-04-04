package com.jerry.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jerry.domain.models.WeatherDomain
import com.jerry.presentation.R
import com.jerry.presentation.imageLoader

@Composable
fun WeatherItems(
    weatherData: WeatherDomain,
    imageUrl: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            WeatherItem(
                icon = painterResource(id = R.drawable._wind),
                value = weatherData.current.wind_kph.toString(),
                unit = "km/h",
                text = "Wind"
            )

            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .height(40.dp)
                    .width(0.5.dp)
                    .align(Alignment.CenterVertically)
            )

            WeatherItem(
                icon = painterResource(id = R.drawable.humidity),
                value = weatherData.current.humidity.toString(),
                unit = "%",
                text = "Humidity"
            )

            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .height(40.dp)
                    .width(0.5.dp)
                    .align(Alignment.CenterVertically)
            )

            WeatherItem(
                icon = imageLoader(imageUrl = imageUrl),
                value = weatherData.current.feelslike_c.toString(),
                unit = "\u00B0C",
                text = "Feels Like"
            )
        }
    }
}

@Composable
fun WeatherItem(
    icon: Painter,
    unit: String? = null,
    value: String,
    text: String
) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = "$value $unit",
            style = TextStyle(fontSize = 18.sp),
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = text,
            style = TextStyle(fontSize = 14.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
