package com.livecom.tech.ui

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.livecommerceservice.sdk.domain.api.LiveCom
import com.livecommerceservice.sdk.domain.api.SdkEntrance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SdkDemoViewModel @Inject constructor() : ViewModel() {

    var isLoading by mutableStateOf(false)

    // https://livecom.tech/{videoId}?p={productSku}
    fun startSdkFromDeepLink(context: Context, uri: Uri) {
        val streamId = uri.pathSegments?.firstOrNull()
        val productSku = uri.getQueryParameter("p")
        viewModelScope.launch {
            if (!streamId.isNullOrBlank()) {
                isLoading = true
                LiveCom.openSdkScreen(SdkEntrance.OpenVideoList(), context)
                LiveCom.openSdkScreen(SdkEntrance.OpenVideo(streamId, productSku), context)
                isLoading = false
            }
        }
    }
}
