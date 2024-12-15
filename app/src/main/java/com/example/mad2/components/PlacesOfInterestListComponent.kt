import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun PlacesOfInterestListComponent(
    placesOfInterest: List<PlaceOfInterest>,
    selectedPlacesOfInterest: List<PlaceOfInterest>,
    onSelectPlace: (PlaceOfInterest) -> Unit,
    onRemovePlace: (PlaceOfInterest) -> Unit
) {
    Text("Select Places of Interest:", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
    LazyColumn(
        modifier = Modifier
            .border(BorderStroke(2.dp, Color.Gray), shape = RoundedCornerShape(8.dp))
    ) {
        items(placesOfInterest) { place ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = place.name,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.padding(start = 12.dp)
                )
                if (place !in selectedPlacesOfInterest) {
                    IconButton(onClick = { onSelectPlace(place) }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.Black
                        )
                    }
                } else {
                    IconButton(onClick = { onRemovePlace(place) }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove",
                            tint = Color.Red
                        )
                    }
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
        }
    }
}