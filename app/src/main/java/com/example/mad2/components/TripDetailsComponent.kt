import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mad2.model.PlaceOfInterest
import com.example.mad2.model.Trip

@Composable
fun TripDetails(
    trip: Trip,
    onBack: () -> Unit,
    onDeletePlace: (PlaceOfInterest) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(
                text = trip.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(8.dp),
                textAlign = TextAlign.Right,
                fontSize = 16.sp
            )
            Button(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Return"
                )
            }
        }
        HorizontalDivider(color = Color.Black, thickness = 2.dp)
        LazyColumn {
            items(trip.placesOfInterest) { place ->
                PlaceOfInterestItem(
                    place = place,
                    onDelete = { onDeletePlace(place) }
                )
                HorizontalDivider(color = Color.Black, thickness = 1.dp)
            }
        }
    }
}