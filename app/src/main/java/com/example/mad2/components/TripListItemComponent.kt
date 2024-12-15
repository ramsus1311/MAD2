import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.mad2.model.Trip
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TripListItem(
    trip: Trip,
    onSelect: (Trip) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.clickable { onSelect(trip) }) {
            Text(
                text = trip.name,
                fontSize = 16.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(start = 8.dp)
            )
            val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
            Text(
                text = "${dateFormat.format(Date(trip.startDate))} to ${dateFormat.format(Date(trip.endDate))}",
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
            Spacer(Modifier.size(8.dp))
        }
    }
}