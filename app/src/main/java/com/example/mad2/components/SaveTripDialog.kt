import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveTripDialog(
    tripName: String,
    onTripNameChange: (String) -> Unit,
    onDismissDialog: () -> Unit,
    onSaveTrip: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = onDismissDialog) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Enter Trip Name", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = tripName,
                    onValueChange = onTripNameChange,
                    label = { Text("Trip Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onSaveTrip,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Save")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = onDismissDialog,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}
