package com.livecom.tech.ui.screens.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.livecom.tech.data.SdkDemoAppRepository
import com.livecom.tech.model.ProductInCart
import java.util.UUID
import kotlin.random.Random

@Composable
fun ProductsComposable() {
    val viewModel = hiltViewModel<ProductsViewModel>()
    ProductsContent(products = viewModel.products.collectAsState().value)
}

@Composable
fun ProductsContent(products: List<ProductInCart>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        contentAlignment = if (products.isEmpty()) Alignment.Center else Alignment.TopCenter
    ) {
        if (products.isEmpty()) {
            Text(text = "No products in cart")
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                products.forEach {
                    Product(product = it)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun Product(product: ProductInCart) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Product SKU: ")
            }
            append(product.sku)
        }
    )
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Product count: ")
            }
            append(product.count.toString())
        }
    )
}

@Preview
@Composable
fun EmptyProductsPreview() {
    ProductsComposable()
}

@Preview
@Composable
fun ProductsPreview() {
    ProductsContent(
        List(4) {
            ProductInCart(
                sku = UUID.randomUUID().toString(),
                count = Random.nextInt(1, 10)
            )
        }
    )
}