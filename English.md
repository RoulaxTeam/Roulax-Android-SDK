

# Roulax SDK Integrated Documentation V1.0.X_ANDROID  

[中文文档](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/README.md)  

## How to use

### Add dependency library
1、Contact Roulax business or Roulax technical support classmates to obtain AAR support for the android version of the Roulax SDK. The current version is 1.0.11, and the current support is the androidx version. If you need a non-androidx version, please contact the Roulax platform.  
2、After obtaining, according to the actual situation of the project, add the library reference of RoulaxSDK in the project root directory.

<!-- 下载地址：[Roulax-SDK-Core](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/main_1.0.11/rsdk_1011_202205091149.aar) -->

### Add SDK-dependent third-party libraries
	implementation group: 'com.google.android.gms', name: 'play-services-ads', version: '15.0.0'

### Add SDK-dependent permission declaration

  	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>


### SDK Obfuscation Rules
     #  Don't obfuscate JS
	-keepattributes *Annotation*
	-keepattributes *JavascriptInterface*
	-keep class com.rad.adlibrary.RSDKJavaScriptInterface{
	*;
	}
	-keep class com.rad.adlibrary.RSSDKJavaScriptInterface{
	*;
	}


## Initialization

In principle, please keep the initialization operation in the project Application.作<br>
**YOU_APPID**:The appid of the current application, please obtain it after creating it in the Roulax developer background or contact Roulax Business to help you create it and provide it to you<br>
**YOU_APPKEY**:The key of your developer account can be obtained in the developer's background, or contact Roulax Business to provide it.<br>


	@Override
	public void onCreate() {
			super.onCreate();

           RSDK.getInstance().init("YOUR_APPID", "YOU_APPKEY", new RSDK.RSDKListener() {
                    @Override
                    public void onSDKInitSuccess() {
                        Log.i("RSDK", "Roulax sdk init success");
                    }

                    @Override
                    public void onSDKInitFailure(RError error) {
                        Log.e("RSDK", "Roulax sdk init failure");
                    }
                });  
	}


## Interactive advertising

You can set the entrance of interactive advertisements at will. Roulax will not have any entrance requirements in the mode of interactive advertisements. However, in order to maximize your income, you can contact Roulax's business and operations to assist you in designing optimal monetization ideas. and way, Roulax will do its best to provide you with the best service.

### Interactive Advertising Access--Initialization

Interactive ad initialization, you can initialize it before you need to display the interactive ad<br>
**YOU_UNIT_ID**:It is the ID of the interactive advertisement, please provide it in the developer's background or find a business.


     RIconInteractiveAd interactiveAd = new RIconInteractiveAd("YOU_UNIT_ID");
        interactiveAd.setRIconInteractiveAdListener(new RIconInteractiveAd.RIconInteractiveAdListener() {
            @Override
            public void onLoadSuccess() {
                Log.i("RSDK", "Load success");
            }

            @Override
            public void onLoadFailed(RError error) {
                Log.i("RSDK", "Load failure");
            }

			/***
			* Showcase success of interactive games
			*/
            @Override
            public void onShowSuccess() {
				  Log.i("RSDK", "onShowSuccess");
            }

            @Override
            public void onShowFailed(RError error) {
				   Log.i("RSDK", "onShowFailed");
            }

            @Override
            public void onClose() {
                 Log.i("RSDK", "onClose");
            }
			/***
			* Display after a successful interactive game
			*/
            @Override
            public void onInteractiveShowSuccess() {
				  Log.i("RSDK", "onInteractiveShowSuccess");
            }
			
            @Override
            public void onInteractiveShowFailed(RError error) {               
				Log.i("RSDK", "onInteractiveShowFailed");
            }

            @Override
            public void onClickInteractive() {
                Log.i("RSDK", "onClickInteractive");
            }

            @Override
            public void onClickAd() {
                Log.i("RSDK", "onClickAd");
            }
        });


### Interactive Advertising Access--Request

Before you need to display the interactive advertisement, you can request to cache the relevant information of the interactive advertisement in advance, and when you need to display it, you can display it immediately.

	 interactiveAd.request();


### Interactive Advertising Access--Display Advertising

The logic of ad display.

 	 if (interactiveAd.isReady()) {
         interactiveAd.show(MainActivity.this);
     }

## Privacy-GDPR
Roulax will collect information such as Language, device information, and GAID and report these data to determine the user ID. If the app needs to be listed on GooglePlay, you need to declare the terms of use on the GooglePlay developer console and in the privacy policy agreement, if you have any questions, please contact the Roulax platform.

## Aggregation Platform Support
The former is the latest version of the adapter, if you need a historical version, please go to the corresponding netowrk document to download
| Aggregation platform|Ad style supported|Access documentation|Download link|
| -------------|:-------------:|:-------------: |:-------------: |
| TopOn| Interstitial | [TopOn-Network-Document](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/network/topon/RoulaxTopOnAdapter.md) |[TopOn-Network-Adapter](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/network_max/rad-network-max-release.aar)|
| Max  | Interstitial | [Max-Network-Document](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/network/Max/RSDK-MAX-English.md) |[Max-Network-Adapter](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/network_max/rad-adapter-max.aar)|


## Update Note
| Date | Version | Log |
|--|--|--|
| 2022-05-11 | 1.0.11 | Roulax SDK Release |
