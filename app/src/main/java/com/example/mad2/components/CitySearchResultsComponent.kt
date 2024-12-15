import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mad2.model.CityDetails

@Composable
fun CitySearchResultsComponent(
    cities: List<CityDetails>,
    onCitySelected: (CityDetails) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(cities) { city ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCitySelected(city) },
                shape = RectangleShape,
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Text(
                        text = city.city,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = buildString {
                            append(city.state.takeIf { it.isNotEmpty() }?.let { "State: $it, " }
                                ?: "")
                            append("Country: ${city.country}")
                        },
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}