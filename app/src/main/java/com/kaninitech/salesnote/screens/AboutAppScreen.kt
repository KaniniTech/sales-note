package com.kaninitech.salesnote.screens



import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaninitech.salesnote.R
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kaninitech.salesnote.utils.DynamicStatusBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.jet)
    DynamicStatusBar(backgroundColor) // ✅ Keeps status bar consistent
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("About & Credits", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = backgroundColor
                )
            )
        },
        containerColor = colorResource(id = R.color.light_bg_color)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()) // ✅ Scrollable content
        ) {
            // ✅ About Section
            Text(
                text = "Sales Note App",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                //set reminders,
                text = "Version: 1.0.1",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )


            // ✅ About Section
            Text(
                text = "About This App",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                //set reminders,
                text = "Sales Note is a simple sales recording tool designed for shop owners, vendors, and seasonal business people. \n" +
                        "It helps you keep track of your sales by allowing you to: Record sales by adding Item name, price and Quantity",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            HorizontalDivider(thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.4f))

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Credits Section
            Text(
                text = "Credits & Acknowledgements",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val iconCredits = listOf(
//
                "App icon designed by IconsNova from Flaticon",

                " In App Screen image icons:",
                "-No data available Placeholder icon image designed by kerismaker from Flaticon",

                )

            iconCredits.forEach { credit ->
                Text(
                    text = credit,
                    style = if (credit.endsWith(":"))
                        MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    else
                        MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            // ✅ External link
            Text(
                text = "Visit Flaticon: www.flaticon.com",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Blue,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .clickable {
                        val url = "https://www.flaticon.com"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                    .padding(vertical = 4.dp)
            )

            Text(
                //set reminders,
                text = "All icons, illustrations, animations, and other media" +
                        " used in the app are sourced " +
                        "from publicly available open-source or free-to-use repositories.  \n" +
                        "The above attribution is in accordance with the original license agreements.  \n" +
                        "The developer of this app does not claim ownership of these assets — " +
                        "they belong to their respective owners.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )


        }
    }
}



@Preview(showBackground = true)
@Composable
fun CreditAuthorScreenPreview() {
    AboutAppScreen(navController = rememberNavController())
}