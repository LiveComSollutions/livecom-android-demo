package com.livecom.tech.ui.screens.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.livecom.tech.R
import com.livecom.tech.model.ProductInCart
import com.livecom.tech.ui.screens.products.ProductsContent
import java.util.*
import kotlin.random.Random

@Composable
fun CheckoutComposable() {
    val viewModel = hiltViewModel<CheckoutViewModel>()
    CheckoutContent(
        productsCount = viewModel.productsInCart,
        products = viewModel.products.collectAsState().value
    )
}

@Composable
private fun CheckoutContent(productsCount: Int, products: List<ProductInCart>) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(top = 16.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.checkout_title, productsCount),
        )
        ProductsContent(products = products)
    }
}

@Preview
@Composable
fun CheckoutPreview() {
    val products = remember {
        List(4) {
            ProductInCart(
                sku = UUID.randomUUID().toString(),
                count = Random.nextInt(1, 10)
            )
        }
    }
    val productsCount = remember { products.sumOf { it.count } }
    CheckoutContent(productsCount = productsCount, products = products)
}