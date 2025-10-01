package com.kaninitech.salesnote.screens




import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaninitech.salesnote.R
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kaninitech.salesnote.screens.components.MoreSaleDetPop
import com.kaninitech.salesnote.utils.DynamicStatusBar
import com.kaninitech.salesnote.utils.formatDateToReadable
import com.kaninitech.salesnote.viewmodel.SingleProductSaleViewModel
import com.kaninitech.salesnote.viewmodel.SingleSaleViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleSalesReportScreen(navController: NavController, itemId: String?) {
    val backgroundColor = colorResource(id = R.color.jet)
    DynamicStatusBar(backgroundColor)
    // ✅ Define states for search
    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    var selectedNotes by remember { mutableStateOf("") }
    var showPopupDet by remember { mutableStateOf(false) }


    val singleSaleViewModel: SingleSaleViewModel = koinViewModel()
    val singleProductSaleViewModel: SingleProductSaleViewModel = koinViewModel()

    val saleReceipt by singleProductSaleViewModel.salesSummary.collectAsState()
    val products by singleProductSaleViewModel.productsForReceipt.collectAsState()
    val singleSale by singleSaleViewModel.singleSale.collectAsState()

    val context = LocalContext.current



    LaunchedEffect(Unit) {

        if(itemId != null) {
            singleProductSaleViewModel.loadSalesByDate(itemId)
            singleSaleViewModel.getSalesByDate(itemId)
        }
    }


    Scaffold(

        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Daily Sales",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${formatDateToReadable(itemId.toString())}", // replace with dynamic date
                            color = Color(0xFF4CAF50), // green
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                },
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

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF2F4F7) // greyish
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {

                    if (saleReceipt.isEmpty()) {
                        EmptyPlaceholder(
                            imageRes = R.drawable.empty, // Your custom drawable
                            title = "No data Sales available!",
                            description = "once sales are added it will appear here"
                        )

                    }else{

                        // Iterate over sales when not empty
                        for (index in singleSale.indices) {
                            val sale = singleSale[index]

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        // Handle card click if needed\
                                        selectedNotes = sale.description.ifEmpty { "No Notes Added" }
                                        singleProductSaleViewModel.loadProductsByReceipt(sale.receipt)
                                        showPopupDet = true
                                    },
                                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp) // inner spacing for content
                                ) {
                                    Text(
                                        text = sale.receipt,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    Spacer(Modifier.height(8.dp))

                                    Text(text = "Sale Type: ${sale.saleType}")
                                    Spacer(Modifier.height(6.dp))

//                                    Text(text = "On: ${sale.date}")
//                                    Spacer(Modifier.height(6.dp))

                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            "Total Sales: ${sale.totalSale}",
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                    Spacer(Modifier.height(6.dp))

                                    Text(
                                        "Total Paid: ${sale.totalPaid}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(Modifier.height(6.dp))

                                    Text(
                                        "Change: ${sale.change}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(Modifier.height(12.dp))

                                }
                            }

                            // Optional divider between cards (not strictly needed since padding does the job)
                            if (index < saleReceipt.lastIndex) {
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                        }

                    }

                }
            }


        }
    }


    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Additional Notes", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(selectedNotes)
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    if (products.isNotEmpty()) {
                        Text("Items for selected receipt:")
                        products.forEach {
                            Text("${it.productName} - Qty: ${it.quantity} - Total: ${it.total}")
                        }
                    }

                }

            }
        }
    }


    if (showPopupDet) {
        MoreSaleDetPop(
            onDismiss = { showPopupDet = false },
            notes = selectedNotes,
            items = products
        )
    }





}


data class Sales(
    val receipt: String,
    val date: String,
    val totalSales: Float
)






@Preview(showBackground = true)
@Composable
fun SingleSalesReportScreenPreview() {
    SingleSalesReportScreen(navController = rememberNavController(), itemId = "020")
}


@Composable
fun EmptyPlaceholder(
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int = android.R.drawable.ic_menu_gallery,
    title: String = "No data available",
    description: String = "There are no items to show right now. Try adding some.",
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(160.dp)
                    .padding(bottom = 8.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B7280),
                modifier = Modifier.padding(horizontal = 16.dp),
                lineHeight = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
