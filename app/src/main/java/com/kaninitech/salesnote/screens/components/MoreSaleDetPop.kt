package com.kaninitech.salesnote.screens.components




import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kaninitech.salesnote.R
import com.kaninitech.salesnote.model.SingleProductEntity
import com.kaninitech.salesnote.utils.formatDates
import com.kaninitech.salesnote.viewmodel.SingleProductSaleViewModel
import com.kaninitech.salesnote.viewmodel.SingleSaleViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreSaleDetPop(
    onDismiss: () -> Unit,
    notes: String,
    items: List<SingleProductEntity>,
    salesDate: String,
    salesReceipt: String
) {
    val singleSaleViewModel: SingleSaleViewModel = koinViewModel()
    val singleProductSaleViewModel: SingleProductSaleViewModel = koinViewModel()
    var showDeleteDialog by remember { mutableStateOf(false) }
    val currentDate =  System.currentTimeMillis()
    val todayDate = formatDates(currentDate)
    val context = LocalContext.current
    val primaryColor = colorResource(id = R.color.raspberry)   // Your main brand color
    val surfaceColor = colorResource(id = R.color.white)       // Background for dialog
    val cardColor = colorResource(id = R.color.light_bg_color)     // Background for product cards
    val textColor = colorResource(id = R.color.black)          // Normal text
    val subTextColor = colorResource(id = R.color.text_gray)   // For secondary text

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = surfaceColor,
            tonalElevation = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                Modifier
                    .padding(20.dp)
                    .imePadding()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                Text(
                    text = "Sales Details",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = textColor
                )

                // Notes
                Column {
                    Text(
                        text = "Additional Notes",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = primaryColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = notes, fontSize = 14.sp, color = subTextColor)
                }

                // Items list
                Text(
                    text = "Items for selected receipt",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = primaryColor
                )

                items.forEach { item ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = cardColor
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = item.productName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = textColor
                            )
                            Text(
                                text = "Quantity: ${item.quantity}",
                                fontSize = 14.sp,
                                color = subTextColor
                            )
                            Text(
                                text = "Total: ${item.total}",
                                fontSize = 14.sp,
                                color = subTextColor
                            )
                        }
                    }
                }

                // Action button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End) // space + align right
//                    horizontalArrangement = Arrangement.End
                ) {
                    if(salesDate == todayDate) {
                        Button(
                            onClick = {

                                showDeleteDialog = true
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                        ) {
                            Text("delete Sales", color = Color.White)
                        }
                    }

                    Button(
                        onClick = { onDismiss() },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id=R.color.jet))
                    ) {
                        Text("Close", color = Color.White)
                    }
                }
            }
        }


        // AlertDialog logic
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Sales") },
                text = { Text("Do you want permanently delete this sales?") },
                confirmButton = {
                    TextButton(onClick = {
                        items.forEach { item ->
                            singleProductSaleViewModel.hardDeleteSalesProductById(item.receipt)
                        }
                        singleSaleViewModel.hardDeleteSalesById(salesReceipt)
                        Toast.makeText(
                            context,
                            "Sales Deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                        showDeleteDialog = false
                        onDismiss()
                    }) {
                        Text(
                            text = "Delete",
                            color = colorResource(id = R.color.red)
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text(
                            text = "Cancel",
                            color = colorResource(id = R.color.text_gray)
                        )
                    }
                }
            )
        }
    }
}
