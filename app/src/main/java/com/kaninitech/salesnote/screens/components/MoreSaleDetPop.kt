package com.kaninitech.salesnote.screens.components




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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kaninitech.salesnote.R
import com.kaninitech.salesnote.model.SingleProductEntity


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MoreSaleDetPop(
//    onDismiss: () -> Unit,
//    notes: String,
//    items: List<SingleProductEntity>
//) {
//    val backgroundColor = colorResource(id = R.color.raspberry)
//
//    val currentDate = remember { System.currentTimeMillis() }
//    val formattedDate = formatDate(currentDate)
//
//    Dialog(onDismissRequest = { onDismiss() }) {
//        Surface(
//            shape = RoundedCornerShape(16.dp),
//            color = Color.White,
//            tonalElevation = 8.dp,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Column(
//                Modifier
//                    .padding(16.dp)
//                    .imePadding()
//                    .verticalScroll(rememberScrollState()),
//                verticalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                Text(text = "Sales Details", fontWeight = FontWeight.Bold, fontSize = 18.sp)
//
//                Text(text = "Additional notes", fontSize = 18.sp)
//                Text(text = notes)
//
//                Text("Items for selected receipt:")
//                items.forEachIndexed { index, item ->
//                    Text("${item.productName} - Qty: ${item.quantity} - Total: ${item.total}")
//                }
//
//                // Action buttons
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.End
//                ) {
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Button(onClick = {
//                            onDismiss()
//                    },
//                        shape = RoundedCornerShape(4.dp),
//                        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
//                    ) {
//                        Text("close" ,color = Color.White)
//                    }
//                }
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreSaleDetPop(
    onDismiss: () -> Unit,
    notes: String,
    items: List<SingleProductEntity>
) {
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
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { onDismiss() },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                    ) {
                        Text("Close", color = Color.White)
                    }
                }
            }
        }
    }
}
