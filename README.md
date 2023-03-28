# LiveCom SDK

It is a sample project with LiveCom SDK.

You should call LiveCom.configure method as soon as possible. Because it needs time to loads some sdk configuration from network. [Methods and parameters describing](https://github.com/LiveComSollutions/livecom-android-documentation)

    LiveCom.configure(  
        applicationContext = this,  
        sdkToken = sdkDemoAppRepository.getSdkKey(),  
        shareDomain = "livecom.tech"  
    )

You can insert you personal **SDK key** in `SdkDemoAppRepository.SDK_KEY_VALUE` or enter it on the settings screen of the demo app.   
**Warning!**  
SDK will not work without sdk-key! This is your main identifier!
