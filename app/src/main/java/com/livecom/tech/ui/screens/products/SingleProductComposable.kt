package com.livecom.tech.ui.screens.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.livecom.tech.R
import java.util.UUID

@Composable
fun SingleProductComposable(productSku: String) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
            .wrapContentHeight(),
        text = stringResource(R.string.product_sku, productSku),
        textAlign = TextAlign.Center,
    )
}

@Preview
@Composable
fun SingleProductPreview() {
    SingleProductComposable(productSku = UUID.randomUUID().toString())
}