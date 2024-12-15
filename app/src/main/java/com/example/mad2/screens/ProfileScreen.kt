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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val email = currentUser?.email ?: "Unknown Email"

    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }

    // Fetch user data from Firestore
    LaunchedEffect(email) {
        if (email != "Unknown Email") {
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(email)

            userRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        name = document.getString("name") ?: ""
                        phoneNumber = document.getString("phoneNumber") ?: ""
                    }
                }
                .addOnFailureListener {
                    // Handle failure (e.g., log error)
                }
        }
    }

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

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Email: $email", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(16.dp))

                if (isEditing) {
                    // Editable fields
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

                    Button(
                        onClick = {
                            isEditing = false
                            saveUserProfile(User(name, email, phoneNumber))
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(0.5f)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Save", color = Color.White)
                    }
                } else {
                    // Display fields
                    Text(text = "Name: $name", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(16.dp))
                    Text(text = "Phone: $phoneNumber", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(16.dp))

                    Button(
                        onClick = { isEditing = true },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(0.5f)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Edit", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Logout Button
                Button(
                    onClick = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )
                ) {
                    Text(text = "Logout", color = Color.White)
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

