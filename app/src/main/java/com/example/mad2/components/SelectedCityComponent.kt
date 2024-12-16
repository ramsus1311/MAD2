import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mad2.model.CityDetails

@Composable
fun SelectedCityComponent(
    selectedCity: CityDetails?,
    onResetSelection: () -> Unit
) {
    selectedCity?.let { it ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(BorderStroke(2.dp, Color.Blue), shape = RoundedCornerShape(8.dp))
                .clickable { onResetSelection() }
                .padding(16.dp)) {
            Text(
                text = it.city,
                fontSize = 20.sp
            )
            Text(
                text = "${it.state.takeIf { it.isNotEmpty() }?.let { "$it, " } ?: ""}${it.country}",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        }
    }
}