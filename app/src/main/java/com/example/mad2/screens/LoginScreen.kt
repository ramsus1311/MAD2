package com.example.travelapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

@Composable
fun LoginScreen(auth: FirebaseAuth, navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current

    // States for managing UI feedback
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(text = "Login", style = MaterialTheme.typography.h4, modifier = Modifier.padding(bottom = 32.dp))

        // Email Field
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            isError = errorMessage.isNotEmpty()
        )

        // Password Field
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            isError = errorMessage.isNotEmpty()
        )

        // Error message
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Login Button
        Button(
            onClick = {
                // Clear previous error message
                errorMessage = ""

                // Sign in with Firebase
                auth.signInWithEmailAndPassword(email.value, password.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Successfully signed in
                            val user: FirebaseUser? = auth.currentUser
                            Toast.makeText(
                                context,
                                "Welcome, ${user?.email}",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Navigate to the home screen or other screen
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true } // Pop login from back stack
                            }
                        } else {
                            // Failed to sign in
                            errorMessage = "Authentication failed. Please check your credentials."
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(text = "Login", color = Color.White)
        }

        // Spacer for extra space
        Spacer(modifier = Modifier.height(16.dp))

        // Additional navigation or register option
        TextButton(onClick = { navController.navigate("signup") }) {
            Text(text = "Don't have an account? Sign up", style = MaterialTheme.typography.body2)
        }
    }
}
