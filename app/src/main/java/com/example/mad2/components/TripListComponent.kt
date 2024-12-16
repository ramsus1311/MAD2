import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mad2.model.Trip

@Composable
fun TripListComponent(
    trips: List<Trip>,
    onTripSelect: (Trip) -> Unit,
    onDeleteTrip: (Trip) -> Unit
) {
    Row(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "Trips",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
            fontSize = 16.sp
        )
    }
    HorizontalDivider(thickness = 2.dp, color = Color.Black)
    LazyColumn {
        items(trips) { trip ->
            TripListItem(
                trip = trip,
                onSelect = onTripSelect,
                onDelete = { onDeleteTrip(trip) }
            )
            HorizontalDivider(color = Color.Black, thickness = 1.dp)
        }
    }
}
