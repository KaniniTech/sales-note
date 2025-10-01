package com.kaninitech.salesnote.screens.components




import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kaninitech.salesnote.R
import androidx.compose.ui.window.Dialog
import com.kaninitech.salesnote.model.SingleProductEntity
import com.kaninitech.salesnote.model.SingleSaleEntity
import com.kaninitech.salesnote.screens.NamePriceItem
import com.kaninitech.salesnote.utils.formatDate
import com.kaninitech.salesnote.viewmodel.SingleProductSaleViewModel
import com.kaninitech.salesnote.viewmodel.SingleSaleViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.*





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSalePopUp(
    onDismiss: () -> Unit,
    total: Double,
    items: List<NamePriceItem>
) {
    val accentColor = colorResource(id = R.color.raspberry)

    var expanded by remember { mutableStateOf(false) }
    var salesDescription by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("") }
    var amountPaid by remember { mutableStateOf("") }
    var amountRemain by remember { mutableStateOf("") }

    val singleSaleViewModel: SingleSaleViewModel = koinViewModel()
    val singleProductSaleViewModel: SingleProductSaleViewModel = koinViewModel()

    val currentDate = remember { System.currentTimeMillis() }
    val formattedDate = formatDate(currentDate)

    var paymentMethodError by remember { mutableStateOf(false) }
    var amountPaidError by remember { mutableStateOf(false) }

    val paymentMethodType = listOf("Cash", "Bank", "M-pesa", "Paypal", "Vine", "Other")
    val idSales = generateTimestampBased10DigitNumber()

    // Auto calculate change
    LaunchedEffect(amountPaid) {
        val paid = amountPaid.toDoubleOrNull() ?: 0.0
        amountRemain = (paid - total).toString()
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .imePadding()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                Text(
                    text = "Complete Sale",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
                Text(
                    text = "Total to Pay: $total",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )

                // Items
                Text(
                    text = "Items Added",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                items.forEachIndexed { index, item ->
                    Text(
                        text = "${index + 1}. ${item.name} - ${item.price} : qty ${item.quantity}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Payment method dropdown
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = paymentMethod,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Payment Method *") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        singleLine = true,
                        isError = paymentMethodError,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = Color.Gray,
                            cursorColor = accentColor
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        paymentMethodType.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    paymentMethod = selectionOption
                                    paymentMethodError = false
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                if (paymentMethodError) {
                    Text(
                        text = "Please select a payment method",
                        color = Color.Red,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                // Amount Paid
                OutlinedTextField(
                    value = amountPaid,
                    onValueChange = {
                        amountPaid = it
                        amountPaidError = false
                    },
                    label = { Text("Amount Paid *") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = amountPaidError,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = accentColor
                    ),
                    singleLine = true
                )
                if (amountPaidError) {
                    Text(
                        text = "Please enter a valid amount",
                        color = Color.Red,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                // Change
                OutlinedTextField(
                    value = amountRemain,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Change") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = Color.Gray
                    ),
                    singleLine = true
                )

                // Notes
                Text(
                    text = "Add reference notes (optional)",
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelMedium
                )
                OutlinedTextField(
                    value = salesDescription,
                    onValueChange = { salesDescription = it },
                    label = { Text("Reference Notes") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp, max = 200.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentColor,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = accentColor
                    ),
                    singleLine = false,
                    maxLines = 4
                )

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = {
                            var valid = true
                            if (paymentMethod.isBlank()) {
                                paymentMethodError = true
                                valid = false
                            }
                            if (amountPaid.toDoubleOrNull() == null) {
                                amountPaidError = true
                                valid = false
                            }
                            if (valid) {
                                singleSaleViewModel.insertSingleSale(
                                    SingleSaleEntity(
                                        date = formattedDate,
                                        receipt = idSales.toString(),
                                        saleType = paymentMethod,
                                        description = salesDescription,
                                        totalSale = total.toFloat(),
                                        totalPaid = amountPaid.toFloat(),
                                        change = amountRemain.toFloat(),
                                    )
                                )
                                items.forEachIndexed { _, item ->
                                    singleProductSaleViewModel.insertSingleProduct(
                                        SingleProductEntity(
                                            date = formattedDate,
                                            receipt = idSales.toString(),
                                            productName = item.name,
                                            price = item.price.toFloat(),
                                            quantity = item.quantity.toInt(),
                                            total = item.subTotal.toFloat()
                                        )
                                    )
                                }
                                onDismiss()
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                    ) {
                        Text("Save", color = Color.White)
                    }
                }
            }
        }
    }
}



fun generateTimestampBased10DigitNumber(): Long {
    val timestampPart = (System.currentTimeMillis() / 1000) % 100000 // Last 5 digits of timestamp (seconds)
    val randomPart = (10000..99999).random() // 5 random digits
    return "$timestampPart$randomPart".toLong()
}



