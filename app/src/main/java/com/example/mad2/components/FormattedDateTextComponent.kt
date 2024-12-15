import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FormattedDateTextComponent(
    selectedStartDate: Long?,
    selectedEndDate: Long?,
    onResetDate: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
    val formattedStartDate = selectedStartDate?.let { dateFormat.format(Date(it)) } ?: "N/A"
    val formattedEndDate = selectedEndDate?.let { dateFormat.format(Date(it)) } ?: "N/A"
    Text(
        text = "$formattedStartDate to $formattedEndDate", fontSize = 20.sp,
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(2.dp, Color.Blue), RoundedCornerShape(8.dp))
            .clickable { onResetDate() }
            .padding(16.dp)
    )
}