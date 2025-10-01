package com.kaninitech.salesnote.screens.components




import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kaninitech.salesnote.R
import com.kaninitech.salesnote.model.SingleProductEntity
import com.kaninitech.salesnote.utils.formatDate
import com.kaninitech.salesnote.viewmodel.SingleProductSaleViewModel
import com.kaninitech.salesnote.viewmodel.SingleSaleViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreSaleDetPop(
    onDismiss: () -> Unit,
    notes: String,
    items: List<SingleProductEntity>
) {
    val backgroundColor = colorResource(id = R.color.raspberry)
    var expanded by remember { mutableStateOf(false) }
    var salesDescription by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("") }
    var amountPaid by remember { mutableStateOf("") }
    var amountRemain by remember { mutableStateOf("") }
    var totalSale by remember { mutableStateOf(0f) }

    val singleSaleViewModel: SingleSaleViewModel = koinViewModel()
    val singleProductSaleViewModel: SingleProductSaleViewModel = koinViewModel()


    val currentDate = remember { System.currentTimeMillis() }
    val formattedDate = formatDate(currentDate)




    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                Modifier
                    .padding(16.dp)
                    .imePadding()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "Sales Details", fontWeight = FontWeight.Bold, fontSize = 18.sp)
//                Text(text = "Total to Pay: $total", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)

                Text(text = "Additional notes", fontSize = 18.sp)
                Text(text = notes)

                Text("Items for selected receipt:")
                items.forEachIndexed { index, item ->
                    Text("${item.productName} - Qty: ${item.quantity} - Total: ${item.total}")
                }

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
//                    TextButton(onClick = onDismiss) { Text("Cancel") }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {

                            onDismiss()

                    }) {
                        Text("close")
                    }
                }
            }
        }
    }
}






