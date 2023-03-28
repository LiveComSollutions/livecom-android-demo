package com.livecom.tech.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.livecom.tech.ui.screens.checkout.CheckoutComposable
import com.livecom.tech.ui.screens.main.MainComposable
import com.livecom.tech.ui.screens.products.ProductsComposable
import com.livecom.tech.ui.screens.products.SingleProductComposable
import com.livecom.tech.ui.screens.settings.SettingsComposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

enum class Screen {
    MAIN, PRODUCTS, SETTINGS, SINGLE_PRODUCT, CHECKOUT
}

@Composable
fun AppNavigation(
    startRoute: String = DEFAULT_START_ROUTE,
    navController: NavHostController = rememberNavController(),
) {
    val navigationInitialized = remember { MutableStateFlow(false) }
    NavHost(navController = navController, startDestination = Screen.MAIN.name) {
        composable(Screen.MAIN.name) { MainComposable(navController) }
        composable(route = Screen.MAIN.name) {
            MainComposable(navController = navController)
        }
        composable(Screen.PRODUCTS.name) { ProductsComposable() }
        composable(Screen.SETTINGS.name) { SettingsComposable() }
        composable(Screen.CHECKOUT.name) { CheckoutComposable() }
        composable(
            route = "${Screen.SINGLE_PRODUCT.name}/{$PRODUCT_SKU_ARG}",
            arguments = listOf(
                navArgument(PRODUCT_SKU_ARG) {
                    type = NavType.StringType
                }
            )
        ) {
            SingleProductComposable(
                productSku = it.arguments?.getString(PRODUCT_SKU_ARG).orEmpty()
            )
        }
        navigationInitialized.tryEmit(true)
    }

    LaunchedEffect(Unit) {
        navigationInitialized.filter { it }.first()
        if (startRoute != DEFAULT_START_ROUTE) {
            navController.navigate(
                route = startRoute,
                navOptions = navOptions { popUpTo(0) { inclusive = true } }
            )
        }
    }
}

private val DEFAULT_START_ROUTE = Screen.MAIN.name
private const val PRODUCT_SKU_ARG = "PRODUCT_SKU_ARG"