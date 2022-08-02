

# Rouax SDK Integration Documentation V2.0.X_ANDROID

### Update Note
| Date | Version | Log |
|--|--|--|
| 2022-06-17 | 2.0.00 | Roulax SDK Release |
| 2022-07-14 | 2.1.01 | Ad template support |
| 2022-08-01 | 2.1.04 | Optimize video player compatibility issues, add crash tracing module |

## How to use

### Add dependency library
1、Contact Roulax business or Roulax technical support classmates to obtain AAR support for the android version of the Roulax SDK. Currently, the androidx version is provided. If you need a non-androidx version, please contact the Roulax platform.  
2、After obtaining, according to the actual situation of the project, add the library reference of RoulaxSDK in the project root directory.


| Roulax library | description | necessary |
|--|--|--|
| rad_library_core-release.aar 			| sdk core lib 		| √ |
| rad_library_common-release.aar 		| sdk common lib 		| √ |
| rad_library_nativeicon-release.aar 	| nativeicon ad lib 	| × |
| rad_library_splash-release.aar 		| splash ad lib 		| × |
| rad_library_banner-release.aar 		| banner ad lib 		| × |
| rad_library_native-release.aar 		| native ad lib 		| × |
| rad_library_flowicon-release.aar 		| flowicon ad lib 	| × |
| rad_library_interstitial-release.aar	| interstitial ad lib | × |
| rad_library_rewardvideo-release.aar 	| rewardvideo ad lib| × |
| rad_library_trace-release.aar   		| crash tracing lib | × |
| rad_library_playercommon-release.aar 	| sdk video player lib| if rewardvideo or interstitial ad is needed, this lib is necessary |
### Add SDK-dependent third-party libraries
	implementation group: 'com.google.android.gms', name: 'play-services-ads', version: '15.0.0'
	implementation 'androidx.appcompat:appcompat:1.3.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'

### Add SDK-dependent permission declaration

    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    

### SDK Obfuscation Rules

	-keep class com.rad.**
	-keepclassmembers public class com.rad.**{
        *;
	}
	-keep interface com.rad.**
    -keepclassmembers interface com.rad.**{
        *;
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

## Rewarded Video
Rewarded Video is one of the best performing Ads formats on the Roulax platform. Users can get rewards by watching a video Ads. The creativity and attention-grabbing nature of the video content attracts users to download the advertised product.
#### Rewarded Video sample：

#### Load ad

```
RXSDK.createRXSdkAd().loadRewardVideo("unit_id", object : RXSdkAd.RXRewardVideoAdListener {
    override fun success(adInfo: RXAdInfo, rewardVideoAd: RXRewardVideoAd) {
        
    }

    override fun failure(adInfo: RXAdInfo, error: RError) {

    }

})
```

#### RXRewardVideoAd

| Method | Description |
| :---: | :---: |
| fun setRewardVideoEventListener(listener: RXRewardVideoEventListener)|Register interactive callback for reward video|
| fun isReady(): Boolean | Return whether the ads ready to display |
| fun show() | Display the reward video ads |
| fun release() | Destroy the ad object and release resource |
| fun setAlertDialogText(text: String) | Set the video pre-close alert content |

#### RXRewardVideoEventListener

| Method | Description |
| :---: | :---: |
| fun onAdShow(adInfo: RXAdInfo) | Ad display callback |
| fun onAdShowFail(adInfo: RXAdInfo, error: RError) | Ad showfail callback with reason|
| fun onAdClick(adInfo: RXAdInfo) | Ad click callback |
| fun onAdClose(adInfo: RXAdInfo) | Ad close callback |
| fun onRewarded(adInfo: RXAdInfo) | Reward verification callback, Developers need to issue rewards in this callback |

#### Sample code

```
RXSDK.createRXSdkAd().loadRewardVideo("unit_id", object : RXSdkAd.RXRewardVideoAdListener {
    override fun success(adInfo: RXAdInfo, rewardVideoAd: RXRewardVideoAd) {
        rewardVideoAd.setRewardVideoEventListener(object : RXRewardVideoEventListener {
            override fun onAdShow(adInfo: RXAdInfo) {

            }

            override fun onAdShowFail(adInfo: RXAdInfo, error: RError) {

            }

            override fun onAdClick(adInfo: RXAdInfo) {

            }

            override fun onAdClose(adInfo: RXAdInfo) {

            }

            override fun onRewarded(adInfo: RXAdInfo) {
                
            }

        })
        if (rewardVideoAd.isReady()) {
            rewardVideoAd.show()
        }
    }

    override fun failure(adInfo: RXAdInfo, error: RError) {

    }

})
```
## Interstitial
The interstitial is divided into full-screen and half-screen. developers can choose image + video, image only to run. It is suitable for use when switching more application scenarios.

#### Load ad
    RXSDK.createRXSdkAd().loadInterstitial("unit_id", object : RXSdkAd.RXInterstitialAdListener {
	    override fun success(adInfo: RXAdInfo, interAd: RXInterstitialAd) {
			// load ad success, return RXInterstitialAd
	    }

        override fun failure(adInfo: RXAdInfo, error: RError) {
            
        }
    })

#### RXInterstitialAd

| Method | Description |
| :---: | :---: |
| fun setInterstitialEventListener(listener: RXInterstitialEventListener)|Register interactive callback for interstitial|
| fun isReady(): Boolean | return whether the ads ready to display |
| fun show() | display the interstitial ads |
| fun release() | destroy the ad object and release resource |

#### RXInterstitialEventListener

| Method | Description |
| :---: | :---: |
| fun onAdShow(adInfo: RXAdInfo) | Ad display callback |
| fun onAdShowFail(adInfo: RXAdInfo, error: RError) | Ad showfail callback with reason|
| fun onAdClick(adInfo: RXAdInfo) | Ad click callback |
| fun onAdClose(adInfo: RXAdInfo) | Ad close callback |

#### Sample code

```
RXSDK.createRXSdkAd().loadInterstitial("unit_id", object : RXSdkAd.RXInterstitialAdListener {
        override fun success(adInfo: RXAdInfo, interAd: RXInterstitialAd) {
            interAd.setInterstitialEventListener(object : RXInterstitialEventListener {
                override fun onAdShow(adInfo: RXAdInfo) {
                    
                }

                override fun onAdShowFail(adInfo: RXAdInfo, error: RError) {
                    
                }

                override fun onAdClick(adInfo: RXAdInfo) {
                    
                }

                override fun onAdClose(adInfo: RXAdInfo) {
                    
                }

            })
            if (interAd.isReady()) {
                interAd.show()
            }
        }

        override fun failure(adInfo: RXAdInfo, error: RError) {

        }

})
```
## Banner
Banner ads is the most traditional type of advertising for mobile advertising. It is easy to implement and highly user-acceptable. It is a good form of realization for ultra-casual games and tools.
#### Load ad

```
RXSDK.createRXSdkAd().loadBanner(context, "unit_id", object : RXSdkAd.RXBannerAdListener {
    override fun success(adInfo: RXAdInfo, bannerAd: RXBannerAd) {
        
    }

    override fun failure(adInfo: RXAdInfo, error: RError) {

    }

})
```

#### RXBannerAd

| Method | Description |
| :---: | :---: |
| fun setRXBannerListener(listener: RXBannerEventListener)|Register interactive callback for banner|
| fun render() | Render the view of banner|


#### RXBannerEventListener

| Method | Description |
| :---: | :---: |
| fun onAdShow(adInfo: RXAdInfo) | Ad display callback |
| fun onAdClick(adInfo: RXAdInfo) | Ad click callback |
| fun onAdClose(adInfo: RXAdInfo) | Ad close callback |
| fun onRenderSuccess(pView: View) | Ad render success callback with the banner's view |
| fun onRenderFail(pAdInfo: RXAdInfo, pError: RError) | Ad render fail callback with reason |

#### Sample code

```
RXSDK.createRXSdkAd().loadBanner(context, "unit_id", object: RXSdkAd.RXBannerAdListener {
            override fun failure(adInfo: RXAdInfo, error: RError) {
               
            }

            override fun success(adInfo: RXAdInfo, bannerAd: RXBannerAd) {
				bannerAd.setRXBannerListener(object : RXBannerEventListener {
                        override fun onAdClick(pAdInfo: RXAdInfo) {
                            
                        }

                        override fun onAdClose(pAdInfo: RXAdInfo) {
                           
                        }

                        override fun onAdShow(pAdInfo: RXAdInfo) {
                            
                        }

                        override fun onRenderFail(pAdInfo: RXAdInfo, pError: RError) {
                            
                        }

                        override fun onRenderSuccess(pView: View) {
                            findViewById<ViewGroup>(R.id.container_banner).addView(pView)
                        }
            	}
				bannerAd.render()
			}
	})

```

####Notice
Please always set RXBannerEventListener before callling render(), in order to get rendered view properly.

## Native
Native Ads is currently one of the most popular Ads types. The Roulax SDK will report back creative material information to your app. You will be able to put it into your product, in turn create the best user experience.
#### Load ad

```
 RXSDK.createRXSdkAd().loadNative(context, "unit_id", requestNum, object: RXSdkAd.RXNativeAdListener {
            override fun failure(adInfo: RXAdInfo, errorList: List<RError>) {
                
            }

            override fun success(adInfo: RXAdInfo, nativeAdList: List<RXNativeAd>) {
               
            }
		})
```

#### RXNativeAd

| Method | Description |
| :---: | :---: |
| fun setRXNativeListener(listener: RXNativeEventListener)|Register interactive callback for native ad|
| fun render() | Render the view of native ad|

#### RXNativeEventListener

| Method | Description |
| :---: | :---: |
| fun onAdShow(adInfo: RXAdInfo) | Ad display callback |
| fun onAdClick(adInfo: RXAdInfo) | Ad click callback |
| fun onAdClose(adInfo: RXAdInfo) | Ad close callback |
| fun onRenderSuccess(pView: View) | Ad render success callback with the native ad's view |
| fun onRenderFail(pAdInfo: RXAdInfo, pError: RError) | Ad render fail callback with reason |

#### Sample code

```
RXSDK.createRXSdkAd().loadNative(context, "unit_id", requestNum, object: RXSdkAd.RXNativeAdListener {
            override fun failure(adInfo: RXAdInfo, errorList: List<RError>) {
                
            }

            override fun success(adInfo: RXAdInfo, nativeAdList: List<RXNativeAd>) {
                if (nativeAdList.size >= 0) {
					nativeAdList[0].setRXNativeListener(object : RXNativeEventListener {
                            override fun onAdClick(pAdInfo: RXAdInfo) {
                                
                            }

                            override fun onAdClose(pAdInfo: RXAdInfo) {
                               
                            }

                            override fun onAdShow(pAdInfo: RXAdInfo) {
                               
                            }

                            override fun onRenderFail(pAdInfo: RXAdInfo, pError: RError) {
                               
                            }

                            override fun onRenderSuccess(pView: View) {
                                findViewById<ViewGroup>(R.id.container_native).addView(pView)
                            }
                        })
					nativeAdList[0].render()
                }
            }
       })
```
#### Notice
Please always set RXNativeEventListener before callling render(), in order to get rendered view properly.

## FlowIcon

FlowIcon is a special form of advertising on the Roulax platform, which can be hovered in the developer's application with a flexible configuration and provide an entry to display ads；

#### Load ad
	
	RXSDK.createRXSdkAd().loadFlowIcon(unitId, new RXSdkAd.RXFlowIconAdListener() {
        @Override
        public void success(@NonNull RXAdInfo adInfo, @NonNull RXFlowIconAd flowIconAd) {
			RXLogUtil.d( "RXSDK flowicon load success")
        }

        @Override
        public void failure(@NonNull RXAdInfo adInfo, @NonNull RError error) {
			RXLogUtil.d( "RXSDK flowicon load fail, error " + error)
        }
    });
	

#### Parameters

| Parameter | Description |
| :---: | :---: |
| YOU_UNIT_ID | FlowIcon Unit ID, please apply in the developer backend or ask the commercial to provide |
| RXFlowIconAdListener | FlowIcon request ad callback |

#### RXFlowIconAdListener


| Method | Description |
| :---: | :---: |
| success | Ad request success callback |
| failure | Ad request failure callback |

#### RXFlowIconAd

| Method | Description |
| :---: | :---: |
| fun show(activity: Activity) | Show FlowIcon |
| fun hide() | Hide FlowIcon |
| fun dismiss() | Dismiss FlowIcon |
| fun setFlowConfig(config: FlowConfig) | Set config for FlowIcon|

#### FlowConfig


| Method | Description |
| :---: | :---: |
| fun setFlowIdelCallback(callback: OnFlowIdelCallback) | Set the hover window ad idle interaction listener to callback when the user clicks FlowIcon when the ad is idle. The SDK will implement it by default |
| fun setFlowEventListener(eventListener: RXFlowIconEventListener) | Set up ad interaction listeners |
| fun setImmersionStatusBar(immersionStatusBar: Boolean) | Set whether the hover window is immersed in the status bar (full-screen applications, games are recommended to pass true) |

#### Set Config for FlowIcon

    mFlowIconAd.setFlowConfig(new FlowConfig.Builder()
            .setImmersionStatusBar(true)
            .setFlowEventListener(new RXFlowIconEventListener() {
                @Override
                public void onCreated(@NonNull RXAdInfo adInfo) {
					RXLogUtil.d("RXSDK flowicon on created")
                }

                @Override
                public void onCreateError(@NonNull RXAdInfo adInfo, @NonNull RError error) {
					RXLogUtil.d("RXSDK flowicon on created error: ${error.getErrorString()}")
                }

                @Override
                public void onShow(@NonNull RXAdInfo adInfo) {
					RXLogUtil.d("RXSDK flowicon on show")
                }

                @Override
                public void onShowFailure(@NonNull RXAdInfo adInfo, @NonNull RError error) {
					RXLogUtil.d("RXSDK flowicon on show fail, error: ${error.getErrorString()}")
                }

                @Override
                public void onHide(@NonNull RXAdInfo adInfo) {
					RXLogUtil.d("RXSDK flowicon on hide")
                }

                @Override
                public void onDismiss(@NonNull RXAdInfo adInfo) {
					RXLogUtil.d("RXSDK flowicon on dismiss")
                }

                @Override
                public void onClick(@NonNull RXAdInfo adInfo) {
					RXLogUtil.d("RXSDK flowicon on click")
                }
             })
			.setFlowIdelCallback(new OnFlowIdelCallback() {
				@Override
                public void idelHandler() {
                    RXLogUtil.d("RXSDK flowicon on idel callback")
                }
            })
			.build());

#### RXFlowIconEventListener
| Method | Description |
| :---: | :---: |
| onCreated | Ad create callback |
| onCreateError | Ad create error callback|
| onShow | Ad show callback |
| onShowFailure | Ad show failure callback|
| onHide | Ad hide callback |
| onDismiss | Ad dismiss callback |
| onClick | Ad click callback |


| Method | Description |
| :---: | :---: |
| idelHandler | Ad free callback |

#### Show FlowIcon
	mFlowIconAd.show(activity: Activity)

#### Hide FlowIcon
	mFlowIconAd.hide()

#### Dismiss FlowIcon
	mFlowIconAd.dismiss()

#### Sample code

    RXSDK.createRXSdkAd().loadFlowIcon(unitId, new RXSdkAd.RXFlowIconAdListener() {
            @Override
            public void success(@NonNull RXAdInfo adInfo, @NonNull RXFlowIconAd flowIconAd) {
                Log.i(BridgeTag, "RXSDK flowicon load success");
                flowIconAd.setFlowConfig(new FlowConfig.Builder()
	                .setImmersionStatusBar(true)
	                .setFlowEventListener(new RXFlowIconEventListener() {
	                    @Override
	                    public void onCreated(@NonNull RXAdInfo adInfo) {
							RXLogUtil.d("RXSDK flowicon on created")
	                    }
	
	                    @Override
	                    public void onCreateError(@NonNull RXAdInfo adInfo, @NonNull RError error) {
							RXLogUtil.d("RXSDK flowicon on created error: ${error.getErrorString()}")
	                    }
	
	                    @Override
	                    public void onShow(@NonNull RXAdInfo adInfo) {
							RXLogUtil.d("RXSDK flowicon on show")
	                    }
	
	                    @Override
	                    public void onShowFailure(@NonNull RXAdInfo adInfo, @NonNull RError error) {
							RXLogUtil.d("RXSDK flowicon on show error: ${error.getErrorString()}")
	                    }
	
	                    @Override
	                    public void onHide(@NonNull RXAdInfo adInfo) {
							RXLogUtil.d("RXSDK flowicon on hide")
	                    }
	
	                    @Override
	                    public void onDismiss(@NonNull RXAdInfo adInfo) {
							RXLogUtil.d("RXSDK flowicon on dismiss")
	                    }
	
	                    @Override
	                    public void onClick(@NonNull RXAdInfo adInfo) {
							RXLogUtil.d("RXSDK flowicon on click")
	                    }
	                })
	                .setFlowIdelCallback(new OnFlowIdelCallback() {
	
	                    @Override
	                    public void idelHandler() {
							RXLogUtil.d("RXSDK flowicon on idel callback")
	                    }
	                })
	                .build());
				flowIconAd.show(activity)
            }

            @Override
            public void failure(@NonNull RXAdInfo adInfo, @NonNull RError error) {
				RXLogUtil.d("RXSDK flowicon on load error: ${error.getErrorString()}")
            }
        });

## NativeIcon

NativeIcon ads are a special form of advertising on the Roulax platform that provides developers with portal material for developers to render and can display exciting ads for developers.

#### Load ad
	
	RXSDK.createRXSdkAd().loadNativeIcon("unit_id",  object: RXSdkAd.RXNativeIconAdListener {
            override fun failure(adInfo: RXAdInfo, error: RXError) {
                RXLogUtil.d("native icon on load fail: ${error.msg}")
            }

            override fun success(adInfo: RXAdInfo, nativeIconAd: RXNativeIconAd) {
                RXLogUtil.d("native icon on load success")
            }
        })

#### NativeIconAd


| Method | Description |
| :---: | :---: |
| fun getIconResource(): String | Get the url of img resource for icon |
| fun click() | Call the click method of native icon |
|fun setRXNativeIconListener(listener: RXNativeIconEventListener)|Set up ad interaction listeners|

#### RXNativeIconEventListener
| Method | Description |
| :---: | :---: |
| fun onAdShowSuccess(adInfo: RXAdInfo) | Ad show success callback |
| fun onAdShowFailure(adInfo: RXAdInfo, adError: RXError) | Ad show failure callback |
| fun onRewarded(adInfo: RXAdInfo) | Ad reward callback |
| fun onClosed(adInfo: RXAdInfo) | Ad close callback|
| fun onRefresh(adInfo: RXAdInfo, imgUrl: String) | Ad refresh callback, returns the url of new icon|

#### Sample code
	RXSDK.createRXSdkAd().loadNativeIcon("unit_id",  object: RXSdkAd.RXNativeIconAdListener {
            override fun failure(adInfo: RXAdInfo, error: RXError) {
                RXLogUtil.d("native icon on load fail: ${error.msg}")
            }

            override fun success(adInfo: RXAdInfo, nativeIconAd: RXNativeIconAd) {
                RXLogUtil.d("native icon on load success")
                nativeIconAd.setRXNativeIconListener(object : RXNativeIconEventListener {
                    override fun onAdShowSuccess(adInfo: RXAdInfo) {
                        RXLogUtil.d("native icon on ad show success")
                    }

                    override fun onAdShowFailure(adInfo: RXAdInfo, adError: RXError) {
                        RXLogUtil.d("native icon on ad show failure")
                    }

                    override fun onRewarded(adInfo: RXAdInfo) {
                        RXLogUtil.d("native icon on ad reward")
                    }

                    override fun onClosed(adInfo: RXAdInfo) {
                        RXLogUtil.d("native icon on ad close")
                    }

                    override fun onRefresh(adInfo: RXAdInfo, imgUrl: String) {
                        findViewById<ImageView>(R.id.container_native_icon).apply {
                            Glide.with(this).load(imgUrl).into(this)
                        }
                        RXLogUtil.d("native icon on ad refresh")
                    }
                })

                findViewById<ImageView>(R.id.container_native_icon).apply {
                    Glide.with(this).load(nativeIconAd.getIconResource()).into(this)
                    setOnClickListener {
                        nativeIconAd.click()
                    }
                }
            }
        })



## Privacy-GDPR
Roulax will collect information such as Language, device information, and GAID and report these data to determine the user ID. If the app needs to be listed on GooglePlay, you need to declare the terms of use on the GooglePlay developer console and in the privacy policy agreement, if you have any questions, please contact the Roulax platform.

## Aggregation Platform Support
The former is the latest version of the adapter, if you need a historical version, please go to the corresponding netowrk document to download

| Aggregation platform|Ad style supported|Access documentation|Download link|
| -------------|:-------------:|:-------------: |:-------------: |
| TopOn| Interstitial | [TopOn-Network-Document](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/adapter/topon/english.md) |[TopOn-Network-Adapter](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/adapter_topon/rad_adapter_topon_0.0.3-release.aar)|
| Max  | Interstitial, RewardVideo, Banner | [Max-Network-Document](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/adapter/Max/RSDK-MAX-English.md) |[Max-Network-Adapter](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/network_max/rad_adapter_max-2.0_release.aar)|
