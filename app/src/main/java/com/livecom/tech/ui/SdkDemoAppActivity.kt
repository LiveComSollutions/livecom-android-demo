package com.livecom.tech.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.livecom.tech.data.SdkDemoAppRepository
import com.livecom.tech.model.ProductInCart
import com.livecom.tech.ui.screens.common.FullScreenLoader
import com.livecom.tech.ui.navigation.AppNavigation
import com.livecom.tech.ui.navigation.Screen
import com.livecom.tech.ui.theme.SdkDemoAppTheme
import com.livecommerceservice.sdk.domain.api.LiveCom
import com.livecommerceservice.sdk.domain.api.LiveComProductInCart
import com.livecommerceservice.sdk.domain.api.SdkEntrance
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SdkDemoAppActivity : ComponentActivity() {

    @Inject
    lateinit var sdkDemoAppRepository: SdkDemoAppRepository

    private val viewModel by viewModels<SdkDemoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SdkDemoAppTheme {
                AppNavigation()
                if (viewModel.isLoading) {
                    FullScreenLoader()
                }
            }
        }
        configureSdk()
        intent?.data?.let { viewModel.startSdkFromDeepLink(context = this, uri = it) }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.let { viewModel.startSdkFromDeepLink(context = this, uri = it) }
    }

    private fun configureSdk() {
        LiveCom.callback = object : LiveCom.Callback {
            override fun openProductCardInsideSdk(productId: String): Boolean {
                val openProductInOurApp = sdkDemoAppRepository.shouldShowCustomProduct()
                if (openProductInOurApp) {
                    OtherScreenActivity.startActivity(
                        this@SdkDemoAppActivity,
                        startDestination = "${Screen.SINGLE_PRODUCT.name}/$productId"
                    )
                }
                return openProductInOurApp.not()
            }

            override fun openCheckoutInsideSdk(productsInCart: List<LiveComProductInCart>): Boolean {
                val openCheckoutInOurApp = sdkDemoAppRepository.shouldShowCustomCheckout()
                if (openCheckoutInOurApp) {
                    OtherScreenActivity.startActivity(
                        this@SdkDemoAppActivity,
                        startDestination = Screen.CHECKOUT.name
                    )
                }
                return openCheckoutInOurApp.not()
            }

            override fun productsInCartChanged(productsInCart: List<LiveComProductInCart>) {
                sdkDemoAppRepository.setProducts(
                    productsInCart.map {
                        ProductInCart(sku = it.sku, count = it.count)
                    }
                )
            }
        }
    }
}
