package com.livecom.tech.ui.screens.main

import android.content.Context

interface MainCallback {
    fun openSdkVideoList(context: Context)
    fun openVideoDialog(showVideoList: Boolean)
    fun openProductDialog(showVideoList: Boolean)
    fun openCheckOut(context: Context, navigateToInternalCheckout: () -> Unit)
    fun updateStreamId(streamId: String)
    fun updateProductSku(productSku: String)
    fun onDialogContinueClick(context: Context)
    fun hideDialog()
}
