package com.kaninitech.salesnote.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaninitech.salesnote.R
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kaninitech.salesnote.utils.DynamicStatusBar
import com.kaninitech.salesnote.viewmodel.SingleSaleViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.DollarSign
import compose.icons.fontawesomeicons.solid.InfoCircle
import compose.icons.fontawesomeicons.solid.MobileAlt
import compose.icons.fontawesomeicons.solid.MoneyBillWave
import compose.icons.fontawesomeicons.solid.Receipt
import compose.icons.fontawesomeicons.solid.University
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(navController: NavController) {
    val backgroundColor = colorResource(id = R.color.jet)
    DynamicStatusBar(backgroundColor)
    // ✅ Define states for search
    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val singleSaleViewModel: SingleSaleViewModel = koinViewModel()
   val dailyReports = singleSaleViewModel.dailySalesReports.collectAsState()


    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Reports", color = Color.White) },
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
        containerColor = colorResource(id = R.color.raspberry)
    ) { paddingValues ->
        // Scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                    top = paddingValues.calculateTopPadding(),
                    end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
                    bottom = paddingValues.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState())
                .background(colorResource(id = R.color.white))
        ) {

            Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {

                    if (dailyReports.value.isEmpty()) {

                        EmptyPlaceholder(
                            imageRes = R.drawable.empty, // Your custom drawable
                            title = "No data Sales available!",
                            description = "once sales are added it will appear here"
                        )
                    }else{

                        for (index in dailyReports.value.indices) {
                            val report = dailyReports.value[index]

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        // Handle click if needed
                                    },
                                shape = RoundedCornerShape(4.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    // Date row
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = report.date,
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = colorResource(id = R.color.raspberry)
                                        )
                                        IconButton(
                                            onClick = {
                                                navController.navigate("singleSalesReport/${report.date}")
                                            },
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(colorResource(id = R.color.bittersweet).copy(alpha = 0.1f))
                                        ) {
                                            Icon(
                                                imageVector = FontAwesomeIcons.Solid.InfoCircle,
                                                contentDescription = "Details",
                                                tint = colorResource(id = R.color.bittersweet),
                                                modifier = Modifier.size(22.dp)
                                            )
                                        }
                                    }

                                    // Totals
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = FontAwesomeIcons.Solid.DollarSign,
                                            contentDescription = null,
                                            tint = Color(0xFF4CAF50),
                                            modifier = Modifier.padding(end = 8.dp).size(18.dp)
                                        )
                                        Text("Total Sales: ${report.total}", style = MaterialTheme.typography.bodyMedium)
                                    }

                                    // Cash
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = FontAwesomeIcons.Solid.MoneyBillWave,
                                            contentDescription = null,
                                            tint = Color(0xFF2196F3),
                                            modifier = Modifier.padding(end = 8.dp).size(18.dp)
                                        )
                                        Text("Cash Payments: ${report.cash}", style = MaterialTheme.typography.bodyMedium)
                                    }

                                    // Bank
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = FontAwesomeIcons.Solid.University,
                                            contentDescription = null,
                                            tint = Color(0xFF9C27B0),
                                            modifier = Modifier.padding(end = 8.dp).size(18.dp)
                                        )
                                        Text("Bank Payments: ${report.bank}", style = MaterialTheme.typography.bodyMedium)
                                    }

                                    // Mpesa
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = FontAwesomeIcons.Solid.MobileAlt,
                                            contentDescription = null,
                                            tint = Color(0xFFFF9800),
                                            modifier = Modifier.padding(end = 8.dp).size(18.dp)
                                        )
                                        Text("M-Pesa Payments: ${report.mpesa}", style = MaterialTheme.typography.bodyMedium)
                                    }

                                    // Other
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = FontAwesomeIcons.Solid.Receipt,
                                            contentDescription = null,
                                            tint = Color(0xFF795548),
                                            modifier = Modifier.padding(end = 8.dp).size(18.dp)
                                        )
                                        Text("Other Payments: ${report.other}", style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                            }
                        }

                    }

                }



        }
    }



}



// ✅ Local book model + list
data class Report(
    val date: String,
    val cash: Int,
    val bank: Int,
    val mpesa: Int,
    val other: Int,
    val total: Float
)


@Preview(showBackground = true)
@Composable
fun ReportsScreenPreview() {
    ReportsScreen(navController = rememberNavController())
}




