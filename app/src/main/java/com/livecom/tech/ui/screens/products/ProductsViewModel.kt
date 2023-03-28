package com.livecom.tech.ui.screens.products

import androidx.lifecycle.ViewModel
import com.livecom.tech.data.SdkDemoAppRepository
import com.livecom.tech.model.ProductInCart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    sdkDemoAppRepository: SdkDemoAppRepository
) : ViewModel() {

    val products: StateFlow<List<ProductInCart>> = sdkDemoAppRepository.products
}
