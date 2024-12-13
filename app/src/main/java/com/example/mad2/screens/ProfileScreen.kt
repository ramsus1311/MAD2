import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.travelapp.R
import com.example.data.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, user: User) {
    var name by remember { mutableStateOf(user.name) }
    var phoneNumber by remember { mutableStateOf(user.phoneNumber) }

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
                )

                // Profile Info Fields
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )

                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                // Save Profile Button
                Button(
                    onClick = {
                        // Save the user profile to Firestore
                        saveUserProfile(User(name, user.email, phoneNumber))
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(0.5f)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Save Profile", color = Color.White)
                }

                // Logout Button
                Button(
                    onClick = {
                        // Sign out the user from Firebase
                        FirebaseAuth.getInstance().signOut()

                        // Navigate to the login screen
                        navController.navigate("login") {
                            // Pop the Profile screen off the stack to prevent back navigation
                            popUpTo("home") { inclusive = true }
                        }
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

fun saveUserProfile(user: User) {
    val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("users").document(user.email)

    val userMap = mapOf(
        "name" to user.name,
        "phoneNumber" to user.phoneNumber
    )

    userRef.set(userMap)
        .addOnSuccessListener {
            // Handle success
        }
        .addOnFailureListener {
            // Handle failure
        }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val sampleUser = User(
        name = "Peter fra L'EASY",
        email = "peter@leasy.dk",
        phoneNumber = "88 88 88 88"
    )

    val previewNavController = rememberNavController()

    ProfileScreen(
        navController = previewNavController,
        user = sampleUser
    )
}
