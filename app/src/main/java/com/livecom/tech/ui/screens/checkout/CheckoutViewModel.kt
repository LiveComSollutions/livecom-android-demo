package com.livecom.tech.ui.screens.checkout

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.livecom.tech.data.SdkDemoAppRepository
import com.livecom.tech.model.ProductInCart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    sdkDemoAppRepository: SdkDemoAppRepository
) : ViewModel() {

    val products: StateFlow<List<ProductInCart>> = sdkDemoAppRepository.products
    val productsInCart: Int
        get() = products.value.sumOf { it.count }
}
