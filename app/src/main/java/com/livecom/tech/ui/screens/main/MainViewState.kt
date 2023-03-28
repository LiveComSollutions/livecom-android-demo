package com.livecom.tech.ui.screens.main

data class MainViewState(
    val sdkIsLoading: Boolean = false,
    val streamId: String = "BrR7PME1J",
    val productSku: String = "c50c2661-dc5e-46e6-8b1d-f508e766a975",
    val dialogModel: DialogModel? = null,
    val countProductsInCart: Int = 0,
)

data class DialogModel(
    val dialogType: DialogType,
    val showVideoList: Boolean,
)

enum class DialogType {
    VIDEO,
    PRODUCT
}
