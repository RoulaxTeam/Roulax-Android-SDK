

# Rouax SDK Integration Documentation V2.0.X_ANDROID

[English Document](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/English.md)  

### Update Note
| Date | Version | Log |
|--|--|--|
| 2022-05-17 | 2.0.00 | Roulax SDK Release |

## How to use

### Add dependency library
1、Contact Roulax business or Roulax technical support classmates to obtain AAR support for the android version of the Roulax SDK. Currently, the androidx version is provided. If you need a non-androidx version, please contact the Roulax platform.  
2、After obtaining, according to the actual situation of the project, add the library reference of RoulaxSDK in the project root directory.

<!-- 下载地址：[Roulax-SDK-Core](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/main_1.0.11/rsdk_1011_202205091149.aar) -->

### Add SDK-dependent third-party libraries
	implementation group: 'com.google.android.gms', name: 'play-services-ads', version: '15.0.0'
	implementation 'com.google.guava:guava:31.0.1-android'
	implementation 'androidx.media:media:1.4.3'

### Add SDK-dependent permission declaration

  	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>


### SDK Obfuscation Rules

	-keep public class com.rad.cache.database.entity.**
	-keepclassmembers public class com.rad.cache.database.entity.**{
   		public *;
	}
	-keep public class com.rad.cache.database.dao.**
	-keepclassmembers public class com.rad.cache.database.dao.**{
   		public *;
	}	


## Initialization

In principle, please keep the initialization operation in the project Application<br>
    RXSDK.init(YOU_APPID, object : RXSDK.RXSDKInitListener {
        override fun onSDKInitSuccess() {
            RXLogUtil.d("onSDKInitSuccess")
        }

        override fun onSDKInitFailure(error: RError?) {
           RXLogUtil.d("onSDKInitFailure ${error?.msg}")
        }
    })

#### Parameter Description

| Parameter | Description |
| --- | --- |
| YOU_APPID | The appid of the current application, please obtain it after creating it in the Roulax developer background or contact Roulax Business to help you create it and provide it to you |
| RXSDKInitListener | Ad initialization callback |

#### RXSDKInitListener

| Method | Description |
| --- | --- |
| onSDKInitSuccess | SDK initialization success callback |
| onSDKInitFailure | SDK initialization failure callback, the returned error message (RError) indicates the reason for the initialization failure |

**Notice**：The Roulax ad request must be made after the SDK initialization is successful, otherwise it will cause no filling;


## Splash Ad

In the scenario of opening screen advertisement, you can display it when the app starts or when the app background switches to the foreground, but in order to maximize your revenue, you can contact Roulax's business and operations to assist you in designing the optimal monetization ideas and methods, Roulax will Do our best to provide you with the best service.

#### Load ad
	
	RXSDK.createRXSdkAd().loadSplash(YOU_UNIT_ID, TIMEOUT, object : RXSplashAdListener {
		
        override fun success(adInfo: RXAdInfo, splashAd: RXSplashAd) {
            RXLogUtil.d( "Splash load success")
        }
		
        override fun failure(adInfo: RXAdInfo, error: RError) {
            RXLogUtil.d("Splash load failure $error")
        }
		
        override fun timeout(adInfo: RXAdInfo) {
            RXLogUtil.d("Splash load timeout")
        }
    })
	

#### Parameter Description

| Parameter | Description |
| --- | --- |
| YOU_UNIT_ID | The ID of the open screen advertisement, please provide it in the developer's background or find a business |
| TIMEOUT | It is the maximum time allowed to load an open screen ad (note: >= 5 seconds is recommended) |
| RXSplashAdListener | Splash request advertisement callback |

#### RXSplashAdListener

| Method | Description |
| --- | --- |
| success | The callback when the ad is loaded, which can be displayed in this callback|
| failure | The ad request failure callback The returned error message (RError) indicates the reason for the ad request failure |
| timeout | Open screen ad loading timeout callback |

#### RXSplashAd

| Method | Description |
| --- | --- |
| fun isReady()| Determine if an ad is ready |
| fun getSplashView(activity: Activity): View? | Get Splash Ads |
| fun setEventListener(eventListener: RXSplashEventListener) | Set up ad interaction listeners |

#### Set up ad interaction listeners

    mRXSplashAd.setEventListener(object : RXSplashEventListener {
        override fun onShowSuccess(adInfo: RXAdInfo) {
            RXLogUtil.d("Splash onShowSuccess")

        }

        override fun onShowFailure(adInfo: RXAdInfo, error: RError) {
            RXLogUtil.d("Splash onShowFailure==>$error")
        }

        override fun onClick(adInfo: RXAdInfo) {
            RXLogUtil.d("Splash onClick")
        }

        override fun onDismiss(adInfo: RXAdInfo) {
            RXLogUtil.d("Splash onDismiss")
        }
    })

#### RXSplashEventListener
| Method | Description |
| --- | --- |
| onShowSuccess | Show callback |
| onShowFailure | Show failure callback |
| onClick | Ad click callback |
| onDismiss | Ad close callback |

#### Display ads
	val splashView = mRXSplashAd.getSplashView(activity)
    splashView?.let {
        mSplashContainer.addView(it)
    }?:let {
        RXLogUtil.d("Splash view isEmpty")
    }

#### Sample

    RXSDK.createRXSdkAd().loadSplash(YOU_UNIT_ID, TIMEOUT, object : RXSplashAdListener {
		
        override fun success(adInfo: RXAdInfo, splashAd: RXSplashAd) {
            RXLogUtil.d( "Splash load success")
			splashAd.setEventListener(object : RXSplashEventListener {
		        override fun onShowSuccess(adInfo: RXAdInfo) {
		            RXLogUtil.d("Splash onShowSuccess")
		        }
		
		        override fun onShowFailure(adInfo: RXAdInfo, error: RError) {
		            RXLogUtil.d("Splash onShowFailure==>$error")
		        }
		
		        override fun onClick(adInfo: RXAdInfo) {
		            RXLogUtil.d("Splash onClick")
		        }
		
		        override fun onDismiss(adInfo: RXAdInfo) {
		            RXLogUtil.d("Splash onDismiss")
		        }
		    })
			val splashView = splashAd.getSplashView(activity)
		    splashView?.let {
		        mSplashContainer.addView(it)
		    }?:let {
		        RXLogUtil.d("Splash view isEmpty")
		    }
        }
		
        override fun failure(adInfo: RXAdInfo, error: RError) {
            RXLogUtil.d("Splash load failure $error")
        }
		
        override fun timeout(adInfo: RXAdInfo) {
            RXLogUtil.d("Splash load timeout")
        }
    })



## Privacy-GDPR
Roulax will collect information such as Language, device information, and GAID and report these data to determine the user ID. If the app needs to be listed on GooglePlay, you need to declare the terms of use on the GooglePlay developer console and in the privacy policy agreement, if you have any questions, please contact the Roulax platform.

## Aggregation Platform Support
The former is the latest version of the adapter, if you need a historical version, please go to the corresponding netowrk document to download

| Aggregation platform|Ad style supported|Access documentation|Download link|
| -------------|:-------------:|:-------------: |:-------------: |
| TopOn| Interstitial | [TopOn-Network-Document](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/adapter/topon/english.md) |[TopOn-Network-Adapter](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/adapter_topon/rad_adapter_topon_0.0.3-release.aar)|
| Max  | Interstitial | [Max-Network-Document](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/adapter/Max/RSDK-MAX-English.md) |[Max-Network-Adapter](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/network_max/rad-adapter-max.aar)|
