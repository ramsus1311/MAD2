import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mad2.model.CityDetails
import com.example.mad2.network.GeoapifyApiHelper
import kotlinx.coroutines.launch

@Composable
fun SearchBarComponent(
    query: String,
    onQueryChange: (String) -> Unit,
    geoapifyApiHelper: GeoapifyApiHelper,
    onCitiesFetched: (List<CityDetails>) -> Unit,
    onLoadingStateChange: (Boolean) -> Unit
) {
    val scope = rememberCoroutineScope()
    TextField(
        value = query,
        onValueChange = { newQuery ->
            onQueryChange(newQuery)
            if (newQuery.isNotEmpty()) {
                scope.launch {
                    onLoadingStateChange(true)
                    try {
                        val fetchedCities = geoapifyApiHelper.searchForCityByName(newQuery)
                        onCitiesFetched(fetchedCities ?: emptyList())
                    } catch (e: Exception) {
                        Log.e("Geoapify", "Error fetching cities: ${e.localizedMessage}")
                        onCitiesFetched(emptyList())
                    } finally {
                        onLoadingStateChange(false)
                    }
                }
            } else {
                onCitiesFetched(emptyList())
            }
        },
        label = { Text(text = "Search for a city", fontSize = 20.sp) },
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(2.dp, Color.Gray), shape = RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp)
            )
    )
}