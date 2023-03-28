package com.livecom.tech.data

import android.content.Context
import com.livecom.tech.model.ProductInCart
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SdkDemoAppRepository @Inject constructor(
    @ApplicationContext private val appContext: Context
) {

    val products: StateFlow<List<ProductInCart>> = MutableStateFlow(emptyList())

    fun setProducts(newProducts: List<ProductInCart>) {
        (products as MutableStateFlow<List<ProductInCart>>).value = newProducts
    }

    fun getSdkKey(): String {
        val sp = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sp.getString(SDK_KEY, SDK_KEY_VALUE)!!
    }

    fun setNewSdkKey(newKey: String) {
        val sp = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sp.edit().putString(SDK_KEY, newKey).apply()
    }

    fun shouldShowCustomCheckout(): Boolean {
        return appContext
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(SHOW_CHECKOUT_KEY, false)
    }

    fun setShowCustomCheckout(shouldShow: Boolean) {
        val sp = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sp.edit().putBoolean(SHOW_CHECKOUT_KEY, shouldShow).apply()
    }

    fun shouldShowCustomProduct(): Boolean {
        return appContext
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(SHOW_PRODUCT_KEY, false)
    }

    fun setShowCustomProduct(shouldShow: Boolean) {
        val sp = appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sp.edit().putBoolean(SHOW_PRODUCT_KEY, shouldShow).apply()
    }

    companion object {
        private const val PREFS_NAME = "sdk_demo_app_prefs"
        private const val SDK_KEY = "sdk_key"
        private const val SDK_KEY_VALUE = "e2d97b7e-9a65-4edd-a820-67cd91f8973d" // this is demo key, use here your sdk key
        private const val SHOW_CHECKOUT_KEY = "show_custom_checkout_key"
        private const val SHOW_PRODUCT_KEY = "show_custom_product_key"
    }
}