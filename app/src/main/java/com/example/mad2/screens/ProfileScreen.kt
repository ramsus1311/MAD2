import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.mad2.ui.theme.MAD2Theme
import com.example.travelapp.R

data class UserData(
    val name: String,
    val email: String,
    val phoneNumber: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController,
                  userData: UserData,
                  logout: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Profile") }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.placeholder_avatar),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(250.dp)
                        .padding(top = 24.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp, horizontal = 16.dp)
                ){
                    ProfileInfoRow("Name", userData.name)
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileInfoRow("Email", userData.email)
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileInfoRow("Phone Number", userData.phoneNumber)
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                // Logout button with red background
                Button(
                    onClick = {
                        logout()
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(0.5f) // Set button width to 50% of the screen
                        .align(Alignment.CenterHorizontally), // Center the button
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red // Set the button background color to red
                    )
                ) {
                    Text(text = "Logout", color = Color.White) // Set text color to white for contrast
                }
            }
        }
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val sampleUserData = UserData(
        name = "Peter fra L'EASY",
        email = "peter@leasy.dk",
        phoneNumber = "88 88 88 88"
    )

    // Create a dummy NavController for preview
    val previewNavController = rememberNavController()

    MAD2Theme { // Replace with your app's theme
        ProfileScreen(
            navController = previewNavController,
            userData = sampleUserData,
            logout = { }
        )
    }
}

