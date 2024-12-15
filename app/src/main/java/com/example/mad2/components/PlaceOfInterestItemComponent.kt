import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mad2.model.PlaceOfInterest

@Composable
fun PlaceOfInterestItem(
    place: PlaceOfInterest,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = place.name,
                fontSize = 16.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(start = 8.dp)
            )
            Text(
                text = "${place.street} ${place.houseNumber}, ${place.city}",
                fontSize = 16.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(start = 8.dp),
                color = Color.DarkGray
            )
            val state = if (place.state.isNotEmpty()) ", ${place.state}" else ""
            Text(
                text = "${place.postcode}${state}, ${place.country}",
                fontSize = 16.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(start = 8.dp),
                color = Color.DarkGray
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            onClick = onDelete
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }
}