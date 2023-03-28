package com.livecom.tech.ui.screens.settings

import androidx.lifecycle.ViewModel
import com.livecom.tech.data.SdkDemoAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sdkDemoAppRepository: SdkDemoAppRepository,
) : ViewModel() {

    val viewState: StateFlow<SettingsViewState> = MutableStateFlow(SettingsViewState())

    init {
        setState {
            SettingsViewState(
                sdkKey = sdkDemoAppRepository.getSdkKey(),
                shouldShowCustomCheckout = sdkDemoAppRepository.shouldShowCustomCheckout(),
                shouldShowCustomProduct = sdkDemoAppRepository.shouldShowCustomProduct()
            )
        }
    }

    fun setNewSdkKey(newKey: String) {
        setState { copy(sdkKey = newKey) }
        return sdkDemoAppRepository.setNewSdkKey(newKey)
    }

    fun setShowCustomCheckout(shouldShow: Boolean) {
        setState { copy(shouldShowCustomCheckout = shouldShow) }
        return sdkDemoAppRepository.setShowCustomCheckout(shouldShow)
    }

    fun setShowCustomProduct(shouldShow: Boolean) {
        setState { copy(shouldShowCustomProduct = shouldShow) }
        return sdkDemoAppRepository.setShowCustomProduct(shouldShow)
    }

    private fun setState(updateStateFunction: SettingsViewState.() -> SettingsViewState) {
        (viewState as MutableStateFlow).update(updateStateFunction)
    }
}
