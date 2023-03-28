package com.livecom.tech

import android.app.Application
import com.livecom.tech.data.SdkDemoAppRepository
import com.livecommerceservice.sdk.domain.api.LiveCom
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SdkDemoApp : Application() {

    @Inject
    lateinit var sdkDemoAppRepository: SdkDemoAppRepository

    override fun onCreate() {
        super.onCreate()
        LiveCom.configure(
            applicationContext = this,
            sdkToken = sdkDemoAppRepository.getSdkKey(),
            shareDomain = "livecom.tech"
        )
    }
}
