package com.livecom.tech.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.livecom.tech.R
import java.util.*

@Composable
fun SettingsComposable() {
    val viewModel = hiltViewModel<SettingsViewModel>()
    val viewState = viewModel.viewState.collectAsState().value
    SettingsContent(
        viewState = viewState,
        setNewSdkKey = viewModel::setNewSdkKey,
        setShowCustomCheckout = viewModel::setShowCustomCheckout,
        setShowCustomProduct = viewModel::setShowCustomProduct
    )
}

@Composable
private fun SettingsContent(
    viewState: SettingsViewState,
    setNewSdkKey: (String) -> Unit,
    setShowCustomCheckout: (Boolean) -> Unit,
    setShowCustomProduct: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = viewState.sdkKey,
            onValueChange = setNewSdkKey,
            label = { Text(text = stringResource(R.string.sdk_key)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.show_custom_checkout))
            Switch(
                checked = viewState.shouldShowCustomCheckout,
                onCheckedChange = setShowCustomCheckout
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.show_custom_product))
            Switch(
                checked = viewState.shouldShowCustomProduct,
                onCheckedChange = setShowCustomProduct
            )
        }
    }
}

@Preview
@Composable
fun SettingsPreview() {
    SettingsContent(
        viewState = SettingsViewState(
            sdkKey = UUID.randomUUID().toString(),
            shouldShowCustomCheckout = true,
            shouldShowCustomProduct = true
        ),
        setNewSdkKey = {},
        setShowCustomCheckout = {},
        setShowCustomProduct = {}
    )
}