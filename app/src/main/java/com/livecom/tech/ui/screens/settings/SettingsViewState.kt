package com.livecom.tech.ui.screens.settings

data class SettingsViewState(
    val sdkKey: String = "",
    val shouldShowCustomCheckout: Boolean = false,
    val shouldShowCustomProduct: Boolean = false
)
