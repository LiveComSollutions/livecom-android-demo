package com.livecom.tech.ui.screens.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.livecom.tech.data.SdkDemoAppRepository
import com.livecommerceservice.sdk.domain.api.LiveCom
import com.livecommerceservice.sdk.domain.api.SdkEntrance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val sdkDemoAppRepository: SdkDemoAppRepository
) : ViewModel(), MainCallback {

    val viewState: StateFlow<MainViewState> = MutableStateFlow(MainViewState())

    init {
        viewModelScope.launch {
            sdkDemoAppRepository.products.collect {
                setState { copy(countProductsInCart = it.size) }
            }
        }
    }

    override fun openSdkVideoList(context: Context) {
        viewModelScope.launch {
            setState { copy(sdkIsLoading = true) }
            openVideoList(context)
            setState { copy(sdkIsLoading = false) }
        }
    }

    override fun openVideoDialog(showVideoList: Boolean) {
        setState {
            copy(
                dialogModel = DialogModel(
                    dialogType = DialogType.VIDEO,
                    showVideoList = showVideoList
                )
            )
        }
    }

    override fun openProductDialog(showVideoList: Boolean) {
        setState {
            copy(
                dialogModel = DialogModel(
                    dialogType = DialogType.PRODUCT,
                    showVideoList = showVideoList
                )
            )
        }
    }

    override fun openCheckOut(context: Context, navigateToInternalCheckout: () -> Unit) {
        viewModelScope.launch {
            if (sdkDemoAppRepository.shouldShowCustomCheckout()) {
                navigateToInternalCheckout()
            } else {
                setState { copy(sdkIsLoading = true) }
                LiveCom.openSdkScreen(SdkEntrance.OpenCheckout, context)
                setState { copy(sdkIsLoading = false) }
            }
        }
    }

    override fun updateStreamId(streamId: String) {
        setState { copy(streamId = streamId) }
    }

    override fun updateProductSku(productSku: String) {
        setState { copy(productSku = productSku) }
    }

    override fun hideDialog() {
        setState { copy(dialogModel = null,) }
    }

    override fun onDialogContinueClick(context: Context) {
        val dialogModel = viewState.value.dialogModel ?: return
        when (dialogModel.dialogType) {
            DialogType.VIDEO -> {
                openVideo(
                    context = context,
                    showVideoList = dialogModel.showVideoList,
                    streamId = viewState.value.streamId
                )
                hideDialog()
            }
            DialogType.PRODUCT -> {
                openProduct(context, dialogModel.showVideoList)
                hideDialog()
            }
        }
    }

    private suspend fun openVideoList(context: Context) {
        LiveCom.openSdkScreen(
            SdkEntrance.OpenVideoList(clearScreensStackUpToVideoList = true),
            context
        )
    }

    private fun openVideo(context: Context, showVideoList: Boolean, streamId: String) {
        viewModelScope.launch {
            setState { copy(sdkIsLoading = true) }
            if (showVideoList) openVideoList(context)
            LiveCom.openSdkScreen(SdkEntrance.OpenVideo(streamId = streamId), context)
            setState { copy(sdkIsLoading = false) }
        }
    }

    private fun openProduct(context: Context, showVideoList: Boolean) {
        viewModelScope.launch {
            setState { copy(sdkIsLoading = true) }
            if (showVideoList) openVideoList(context)
            LiveCom.openSdkScreen(
                SdkEntrance.OpenVideo(
                    viewState.value.streamId,
                    viewState.value.productSku
                ),
                context
            )
            setState { copy(sdkIsLoading = false) }
        }
    }

    private fun setState(updateStateFunction: MainViewState.() -> MainViewState) {
        (viewState as MutableStateFlow).update(updateStateFunction)
    }
}
