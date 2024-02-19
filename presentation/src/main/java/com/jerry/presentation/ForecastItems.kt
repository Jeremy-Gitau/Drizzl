package com.jerry.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jerry.domain.models.Hour
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastItems(
    hourData: List<List<Hour>>?
) {

    Text(
        text = "Today",
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(10.dp)
    )

    LazyRow {
        hourData?.let { data ->
            items(data.size) { index ->
                val dayData = data[index]
                Column {
                    ForecastItem(data = dayData)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastItem(
    data: List<Hour>
) {
    val currentTime = LocalTime.now()
    val currentHour = currentTime.hour

    Row {
        data.forEach { hour ->

            val hourTime = formatHour(hour.time)
            val hourOfDay = hourTime.hour

            val isCurrentHour = currentHour == hourOfDay

            val imageUrl = if (!hour.condition.icon.startsWith("https://")) {
                "https://${ hour.condition.icon.substringAfter("http://")}"
            } else {
                hour.condition.icon
            }

            Card(
                modifier = Modifier
                    .padding(6.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(if (isCurrentHour) 18.dp else 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .then(if (isCurrentHour) Modifier
                            .background(color = MaterialTheme.colorScheme.primary) else Modifier)
                        .padding(8.dp),
                ) {
                    //temperature
                    Text(
                        text = hour.temp_c.toString() + "\u00B0C",
                        style = TextStyle(fontSize = 10.sp),
                        color = if (isCurrentHour) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Image(
                        painter = imageLoader(imageUrl = imageUrl).also { Log.e("imageUrl", imageUrl) },
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    //Time
                    Text(
                        text = formatHour(timeString = hour.time).toString(),
                        style = TextStyle(fontSize = 18.sp,fontWeight = FontWeight.Normal),
                        color = if (isCurrentHour) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun formatHour(timeString: String): LocalTime {

    // Parse the input date-time string
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    return LocalTime.parse(timeString, formatter)
}