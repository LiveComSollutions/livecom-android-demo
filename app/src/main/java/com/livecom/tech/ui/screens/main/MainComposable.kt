package com.livecom.tech.ui.screens.main

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.livecom.tech.R
import com.livecom.tech.ui.screens.common.FullScreenLoader
import com.livecom.tech.ui.navigation.Screen

@Composable
fun MainComposable(navController: NavHostController) {
    val viewModel = hiltViewModel<MainViewModelImpl>()
    val viewState = viewModel.viewState.collectAsState().value
    MainContent(
        navController = navController,
        viewState = viewState,
        callback = viewModel
    )
    if (viewState.sdkIsLoading) {
        FullScreenLoader()
    }
}

@Composable
private fun MainContent(
    navController: NavHostController,
    viewState: MainViewState,
    callback: MainCallback
) {
    Dialog(viewState, callback)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val context = LocalContext.current
            Button(
                content = { Text(text = stringResource(R.string.video_list_screen)) },
                onClick = { callback.openSdkVideoList(context) },
            )
            Button(
                content = { Text(text = stringResource(R.string.video_screen)) },
                onClick = { callback.openVideoDialog(showVideoList = false) },
            )
            Button(
                content = { Text(text = stringResource(R.string.video_list_plus_video_screen)) },
                onClick = { callback.openVideoDialog(showVideoList = true) },
            )
            Button(
                content = {
                    Text(text = stringResource(R.string.video_list_plus_video_plus_product_screen))
                },
                onClick = { callback.openProductDialog(showVideoList = true) },
            )
            Button(
                content = { Text(text = stringResource(R.string.checkout_screen)) },
                onClick = {
                    callback.openCheckOut(context) {
                        navController.navigate(Screen.CHECKOUT.name)
                    }
                },
            )
            Button(
                content = {
                    Text(
                        text = stringResource(
                            R.string.product_in_cart,
                            viewState.countProductsInCart
                        )
                    )
                },
                onClick = {
                    navController.navigate(Screen.PRODUCTS.name)
                }
            )
        }
        Button(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = { navController.navigate(Screen.SETTINGS.name) }) {
            Text(text = stringResource(R.string.settings))
        }
    }
}

@Composable
private fun Dialog(viewState: MainViewState, callback: MainCallback) {
    val dialogModel = viewState.dialogModel
    if (dialogModel != null) {
        AlertDialog(
            onDismissRequest = callback::hideDialog,
            buttons = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val context = LocalContext.current
                    Button(onClick = { callback.onDialogContinueClick(context) }) {
                        Text(text = stringResource(R.string.continue_button))
                    }
                }
            },
            text = {
                Column {
                    TextField(
                        label = { Text(text = stringResource(R.string.stream_id_label)) },
                        value = viewState.streamId,
                        onValueChange = callback::updateStreamId
                    )
                    if (dialogModel.dialogType == DialogType.PRODUCT) {
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            label = { Text(text = stringResource(R.string.product_sku_label)) },
                            value = viewState.productSku,
                            onValueChange = callback::updateProductSku
                        )
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun MainPreview() {
    MainContent(
        navController = rememberNavController(),
        viewState = MainViewState(),
        callback = object : MainCallback {
            override fun openSdkVideoList(context: Context) {
            }

            override fun openVideoDialog(showVideoList: Boolean) {
            }

            override fun openProductDialog(showVideoList: Boolean) {
            }

            override fun openCheckOut(context: Context, navigateToInternalCheckout: () -> Unit) {
            }

            override fun hideDialog() {
            }

            override fun onDialogContinueClick(context: Context) {
            }

            override fun updateStreamId(streamId: String) {
            }

            override fun updateProductSku(productSku: String) {
            }
        }
    )
}