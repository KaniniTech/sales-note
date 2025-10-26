package com.kaninitech.salesnote.screens

import com.kaninitech.salesnote.R
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kaninitech.salesnote.navigation.Screen
import com.kaninitech.salesnote.utils.DynamicStatusBar
import androidx.core.net.toUri


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController) {

    val backgroundColor = colorResource(id = R.color.jet)
    DynamicStatusBar(backgroundColor) // ✅ Keeps status bar consistent
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Settings", color = Color.White) },
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
//                .background(colorResource(id = R.color.light_bg_color))
        ) {



// Settings card container (optional)
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = 0.dp,
                border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    SettingsItem(
                        icon = { Icon(Icons.Filled.Info, contentDescription = "About") },
                        title = "About",
                        subtitle = "About the app",
                        onClick = { /* Navigate */
                            navController.navigate(Screen.CreditAuthor.route)
                        }
                    )

                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)


                    SettingsItem(
                        icon = { Icon(Icons.Filled.Lock, contentDescription = "Privacy Policy") },
                        title = "Privacy Policy",
                        subtitle = "View our privacy policy",
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                "https://kaninitech.github.io/SalesApp/privacyPolicy.html".toUri()
                            )
                            context.startActivity(intent)
                            /* Navigate */
                        }
                    )

                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

                    SettingsItem(
                        icon = { Icon(Icons.Filled.Email, contentDescription = "email support") },
                        title = "App Support",
                        subtitle = "Need any help with the app?",
                        onClick = {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "message/rfc822"
                                putExtra(Intent.EXTRA_EMAIL, arrayOf("puritykan254@gmail.com"))
                                putExtra(Intent.EXTRA_SUBJECT, "App Support Request")
                                putExtra(Intent.EXTRA_TEXT, "Hello, I need help with...")
                            }
                            try {
                                context.startActivity(Intent.createChooser(intent, "Contact Support"))
                            } catch (e: Exception) {
                                Toast.makeText(context, "No email app found.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )



                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

                }
            }


        }
    }
}



@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    SettingScreen(navController = rememberNavController())
}


@Composable
private fun SettingsItem(
    icon: @Composable () -> Unit,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
// Icon with an outline-like background
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = 0.dp,
            border = BorderStroke(1.dp, Color(0xFFBDBDBD)),
            modifier = Modifier.size(44.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                icon()
            }
        }


        Spacer(modifier = Modifier.width(12.dp))


        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.labelMedium)
            if (!subtitle.isNullOrEmpty()) {
                Text(text = subtitle, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }
        }


// Right chevron (optional)
        Text(text = ">", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
    }
}