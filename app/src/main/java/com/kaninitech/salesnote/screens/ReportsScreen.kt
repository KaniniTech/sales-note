package com.kaninitech.salesnote.screens

import com.kaninitech.salesnote.R
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kaninitech.salesnote.screens.components.SalesSummaryCard
import com.kaninitech.salesnote.utils.DynamicStatusBar
import com.kaninitech.salesnote.utils.formatDates
import com.kaninitech.salesnote.viewmodel.SingleSaleViewModel
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.DollarSign
import compose.icons.fontawesomeicons.solid.InfoCircle
import compose.icons.fontawesomeicons.solid.MobileAlt
import compose.icons.fontawesomeicons.solid.MoneyBillAlt
import compose.icons.fontawesomeicons.solid.MoneyBillWave
import compose.icons.fontawesomeicons.solid.Receipt
import compose.icons.fontawesomeicons.solid.Search
import compose.icons.fontawesomeicons.solid.University
import org.koin.androidx.compose.koinViewModel
import java.time.YearMonth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(navController: NavController) {
    val backgroundColor = colorResource(id = R.color.jet)
    DynamicStatusBar(backgroundColor)
    val currentYearMonth = remember{ YearMonth.now().toString()} // "2025-05"

    val currentDate =  System.currentTimeMillis()
    val todayDate = formatDates(currentDate)

    // ✅ Define states for search
    var searchQuery by remember { mutableStateOf("") }

    val singleSaleViewModel: SingleSaleViewModel = koinViewModel()
   val dailyReports = singleSaleViewModel.dailySalesReports.collectAsState()
    val totalMonthlySales by  singleSaleViewModel.totalMonthSales.collectAsState()
    val totalNoOfSaleThisMonth by  singleSaleViewModel.totalNoOfSaleThisMonth.collectAsState()


    // ✅ **Filter the list based on search query**
    val filteredReportList = dailyReports.value.filter {
        it.date.contains(searchQuery, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        singleSaleViewModel.getMonthlyTotalSales(currentYearMonth)
        singleSaleViewModel.getNumberOfMonthlySales(currentYearMonth)
    }

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


                    if(dailyReports.value.isNotEmpty()){
                        SalesSummaryCard(
                            backgroundColor = Color(0xFF56E39F),
                            icon = FontAwesomeIcons.Solid.MoneyBillAlt,
                            title = "This Month’s Sales",
                            value = totalMonthlySales.toString(),
                            subtitle = "No. Of Sales: $totalNoOfSaleThisMonth"
                        )
                    }

                    // 🔍 Search Field
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text(text = "Search with Date...") },
                        leadingIcon = {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Search,
                                contentDescription = "Search Icon",
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )

                    if (dailyReports.value.isEmpty()) {

                        EmptyPlaceholder(
                            imageRes = R.drawable.empty, // Your custom drawable
                            title = "No Sales Sales available!",
                            description = "once sales are added it will appear here"
                        )
                    }else if(filteredReportList.isEmpty()) {
                        EmptyPlaceholder(
                            imageRes = R.drawable.empty, // Your custom drawable
                            title = "No Sales data available!",
                            description = "Seems like what you looking for is not found!"
                        )
                    }else{




//                        for (index in dailyReports.value.indices) {
//                            val report = dailyReports.value[index]
                        for (index in filteredReportList.indices) {
                            val report = filteredReportList[index]
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

                                    // mobile wallet
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = FontAwesomeIcons.Solid.MobileAlt,
                                            contentDescription = null,
                                            tint = Color(0xFFFF9800),
                                            modifier = Modifier.padding(end = 8.dp).size(18.dp)
                                        )
                                        Text("Mobile Wallet Payments: ${report.mobile}", style = MaterialTheme.typography.bodyMedium)
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





@Preview(showBackground = true)
@Composable
fun ReportsScreenPreview() {
    ReportsScreen(navController = rememberNavController())
}




