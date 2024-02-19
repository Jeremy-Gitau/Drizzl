package com.jerry.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jerry.domain.models.WeatherDomain

@Composable
fun OutlinedCardWithIconAndText(
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
            OutlinedCardDetails(
                icon = painterResource(id = R.drawable._wind),
                value = weatherData.current.wind_kph.toString(),
                unit = "km/h",
                text = "Wind"
            )

            OutlinedCardDetails(
                icon = painterResource(id = R.drawable.humidity),
                value = weatherData.current.humidity.toString(),
                unit = "%",
                text = "Humidity"
            )

            OutlinedCardDetails(
                icon = imageLoader(imageUrl = imageUrl),
                value = weatherData.current.feelslike_c.toString(),
                unit = "\u00B0C",
                text = "Feels Like"
            )
        }
    }
}

@Composable
fun OutlinedCardDetails(
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
