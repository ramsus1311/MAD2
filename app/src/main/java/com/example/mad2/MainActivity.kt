package com.example.travelapp


import ProfileScreen
import UserData
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search // For Explore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.travelapp.screens.*
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.LaunchedEffect // Import LaunchedEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelApp()
        }
    }
}

@Composable
fun TravelApp() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    // Declare state using mutableStateOf, without 'by'
    val startDestination = remember { mutableStateOf("login") }

    // Wait for the Firebase user state to initialize
    LaunchedEffect(currentUser) {
        // Wait until Firebase has fully initialized the user state
        // Only change the startDestination after Firebase has finished its check
        if (currentUser != null) {
            startDestination.value = "home"
        } else {
            startDestination.value = "login"
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination.value, // Use .value to access the state
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("tripDetails") { TripDetailsScreen(navController) }
            composable("planTrip") { PlanTripScreen(navController) }
            composable("profile") { ProfileScreen(navController) }
            composable("login") {
                LoginScreen(auth = auth, navController = navController)
            }
            composable("profile") {
                val dummyData = UserData(
                    name = "John Doe",
                    email = "john@example.com",
                    phoneNumber = "123-456-7890"
                )
                ProfileScreen(logout = { }, navController = navController, userData = dummyData) }
            composable("user") { CreateUserScreen() }
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("home", "Home", Icons.Default.Home),
        BottomNavItem("PlanTrip", "Plan Trip", Icons.Default.Search),
        BottomNavItem("tripDetails", "Trip Details", Icons.Default.AccountCircle),
        BottomNavItem("profile", "Profile", Icons.Default.AccountCircle)
    )

    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentDestination == item.route,
                onClick = {
                    if (currentDestination != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
