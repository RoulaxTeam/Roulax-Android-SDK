

# Rouax SDK Integration Documentation V3.0.X_ANDROID

### Update Note
| Date | Version | Log |
|--|--|--|
| 2022-03-23 | 3.0.00 | Add OfferWall type of advertising form |
| 2022-04-10 | 3.0.01 | Add GDPR interface and optimize OfferWall ads |
| 2023-05-31 | 3.0.05 | Fix known issues and optimize internal logic |
| 2023-08-14 | 3.0.11 | Fix known issues; add SDK offerwall reward query and callback interface |
| 2023-08-28 | 3.0.14 | Fix known issues; add new incentive mechanism |
| 2024-04-11 | 3.0.21 | Fixed known issues; increased developer region currency reward dollar value ratio |

## How to use

### Add dependency library
1、Contact Roulax business or Roulax technical support classmates to obtain AAR support for the android version of the Roulax SDK. Currently, the androidx version is provided. If you need a non-androidx version, please contact the Roulax platform.  
2、After obtaining, according to the actual situation of the project, add the library reference of RoulaxSDK in the project root directory.


| Roulax library | description | necessary |
|--|--|--|
| rad_library_core-release.aar 			| sdk core lib 		| √ |
| rad_library_common-release.aar 		| sdk common lib 		| √ |
| rad_library_ow-core-release.aar 		| offerwall core lib 	    | √ |
| rad_library_ow_native-release.aar 	| offerwall native ad lib 	    | × |
| rad_library_ow_nativeicon-release.aar | offerwall nativeicon ad lib	| × |
| rad_library_ow_flowicon-release.aar 	| offerwall flowicon ad lib 	    | × |
| rad_library_trace-release.aar   		| crash tracing lib | × |
### Add SDK-dependent third-party libraries
	implementation group: 'com.google.android.gms', name: 'play-services-ads', version: '15.0.0'
	implementation 'androidx.appcompat:appcompat:1.3.0'
	implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
	implementation 'androidx.recyclerview:recyclerview:1.2.1'
	implementation 'androidx.cardview:cardview:1.0.0'

### Add SDK-dependent permission declaration

    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    
    \*when apps update their target to Android 13 or above will need to declare a Google Play services normal permission in the manifest file*/
    <uses-permission android:name="com.google.android.gms.permission.AD_ID"/>
    
    \*Note: In order to calculate the Offer usage time, the following permissions will be included in rad_library_ow-core-release.aar 
    <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" tools:ignore="ProtectedPermissions"/>*/

### SDK Obfuscation Rules

	-keep class com.rad.**
	-keepclassmembers public class com.rad.**{
	    *;
	}
	-keep interface com.rad.**
	-keepclassmembers interface com.rad.**{
	    *;
	}	



## Privacy-GDPR

Roulax will collect information such as Language, device information, and GAID and report these data to determine the user ID. If the app needs to be listed on GooglePlay, you need to declare the terms of use on the GooglePlay developer console and in the privacy policy agreement, if you have any questions, please contact the Roulax platform.

**Note**: If the developer's users are in the European Union, they need to set GDPR authorization before the SDK is initialized. If this interface is not called, the SDK will default to not collecting SDK information in the EU-related regions, and the SDK initialization will fail.

	RXSDK.setGDPRAuth(mode RXSDK.GDPRMode)

#### Parameter Description

| Parameter         | Description                                                  |
| ----------------- | ------------------------------------------------------------ |
| GDPRMode.Allow    | User agrees to the user agreement and privacy authorization； |
| GDPRMode.NotAllow | User rejects the user agreement and privacy authorization;   |
| GDPRMode.Auto     | SDK default value; in relevant EU regions, user information is not collected and SDK initialization fails; |



## Initialization

In principle, please keep the initialization operation in the project Application<br>
If there is a GDPR-related requirement, it needs to be initialized after user authorizing GDPR.

	//after user authorizing GDPR, set it to true. If user refuse to authorize, set it to false.
	//RXSDK.setGDPRAuth(true);
	
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

## OfferWall Ads

## Reward distribution
Currently only support S2S way to send rewards, client-side rewards callback will be open in later versions of the expansion. If you need S2S docking, please contact business for support.

## Set unique user identification

<b>If you are going to use OfferWall ads, please be sure to set the user identification as soon as possible right after the successful initialization of the RXSDK, which is related to the calculation and distribution of OfferWall rewards.</b> 
<b>If not set, all OfferWall ads cannot be requested.</b>

    RXWallApi.setUserId("USER_ID")

#### Sample Code
	RXSDK.init(YOU_APPID, object : RXSDK.RXSDKInitListener {
	    override fun onSDKInitSuccess() {
	        RXLogUtil.d("onSDKInitSuccess")
			RXWallApi.setUserId("USER_ID")
	    }
	
	    override fun onSDKInitFailure(error: RError?) {
	       RXLogUtil.d("onSDKInitFailure ${error?.msg}")
	    }
	})

#### Parameters

| Parameter | Description |
| --- | --- |
| USER_ID | Unique user identification generated by the developer self |



## Set user area code

<b>If you need to proactively set the user's region identifier, please call this interface to set it up, and contact Roulax operators to configure the region's currency to U.S. dollar value ratio. The SDK will return the corresponding U.S. dollar value in the totalAmount of the user's reward onRewardChanged. Amount</b> 
<b>If not set, the default currency-to-USD value ratio in all regions is the same.</b>

    RXWallApi.setDevArea("US")


#### Parameters

| Parameter | Description                                                  |
| --------- | ------------------------------------------------------------ |
| DevArea   | The developer passes in the Roulax region enumeration value. For details, see [country_code Enum Value](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/OwFullReportEnumValue.md) |



## User Rewards

<b>If you need to query OfferWall user rewards through the SDK, you can query through RXWallApi.getUserRewarded() after setting the unique user ID;</b> 

```kotlin
val userReward = RXWallApi.getUserRewarded()
val userAmount = RXWallApi.getUserAmount() // userAmount is the dollar value of userReward’s corresponding area (dev_area) or the user’s real area.
```

<b>If you need to monitor SDK OfferWall user reward update, you can set the callback</b>

```kotlin
RXWallApi.setOfferWallRewardListener(object : RXWallRewardListener {
    override fun onRewardChanged(userId: String, totalReward: Long, totalAmount: Double) {
        shortToast("$userId reward: $totalReward, amount: $totalAmount")
    }
})
```

Note：1. The UserId must be set before using the OfferWall user reward query method; 2. The SDK does not guarantee real-time and absolute accuracy of user rewards. If you need more real-time and accurate user rewards, please refer to accessing S2S



## Prize Rewards

<b>If you need to monitor the real-time callback of SDK prize rewards, you can set the callback</b>

```kotlin
RXWallApi.setOfferWallPrizeRewardListener(object : RXWallPrizeRewardListener {
     override fun onReward(prizeId: String) {
         shortToast("Prize reward: $prizeId")
     }
})
```



## OfferWall Native

#### Load ad (Suggestion: Based on the developer's application usage scenarios, please request ads as soon as possible after the SDK is successfully initialized to ensure that they can be displayed quickly in ad display scenarios)

```
 RXSDK.createRXSdkAd().loadOWNative(context, "unit_id", requestNum, object: RXSdkAd.RXOWNativeAdListener {
            override fun failure(adInfo: RXAdInfo, errorList: List<RError>) {
                
            }

            override fun success(adInfo: RXAdInfo, nativeAdList: List<RXOWNativeAd>) {
               
            }
		})
```

| Parameter | Description |
| --- | --- |
| unit_id | Unit id of OfferWallNative，please apply in the developer backend or ask the commercial to provide |
| requestNum | The number of requests for OfferWall Native.（The current version only supports a single request for an OfferWallNative. regardless of the requestNum setting, only one RXOWNativeAd object will be requested and returned.）|
| RXOWNativeAdListener | OfferWall Native request callback |

#### RXOWNativeAdListener

| Function | Discription|
| :---: | :---: |
| success | Callback for ad loading completion |
| failure | The error message (RError) returned by the ad request failure callback indicates the reason why the ad request failed |

#### RXOWNativeAd

| Function | Discription|
| :---: | :---: |
| fun setRXOWNativeListener(listener: RXOWNativeEventListener)|Register interaction listener for native|
| fun render() | render native view|

#### RXOWNativeEventListener

| Function | Discription |
| :---: | :---: |
| fun onAdShow(adInfo: RXAdInfo) | Ad display callback |
| fun onAdClick(adInfo: RXAdInfo) | Ad click callback |
| fun onAdClose(adInfo: RXAdInfo) | Ad close callback |
| fun onRenderSuccess(pView: View) | The ad is rendered successfully and the successfully rendered View is returned |
| fun onRenderFail(pAdInfo: RXAdInfo, pError: RError) | Ad rendering fails and returns the reason for the failure |

#### Sapmle Code

```
RXSDK.createRXSdkAd().loadOWNative(context, "unit_id", requestNum, object: RXSdkAd.RXOWNativeAdListener {
            override fun failure(adInfo: RXAdInfo, errorList: List<RError>) {
                
            }

            override fun success(adInfo: RXAdInfo, nativeAdList: List<RXOWNativeAd>) {
                if (nativeAdList.size >= 0) {
					nativeAdList[0].setRXOWNativeListener(object : RXOWNativeEventListener {
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
#### Attention
Please call setRXOWNativeListener before calling the render method to ensure the first time to get the successfully rendered ad view.

## OfferWall FlowIcon

#### Load ad (Suggestion: Based on the developer's application usage scenarios, please request ads as soon as possible after the SDK is successfully initialized to ensure that they can be displayed quickly in ad display scenarios)

	RXSDK.createRXSdkAd().loadOWFlowIcon(unitId, new RXSdkAd.RXOWFlowIconAdListener() {
	    @Override
	    public void success(@NonNull RXAdInfo adInfo, @NonNull RXOWFlowIconAd flowIconAd) {
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
| YOU_UNIT_ID | Unit id of OfferWall FlowIcon，please apply in the developer backend or ask the commercial to provide |
| RXOWFlowIconAdListener | OfferWall FlowIcon request ad callback |

#### RXOWFlowIconAdListener

| Parameter | Description |
| :---: | :---: |
| success | The callback in which the advertisement is loaded and can be displayed |
| failure | The error message (RError) returned by the ad request failure callback indicates the reason why the ad request failed |

#### RXOWFlowIconAd

| Parameter | Description |
| :---: | :---: |
| fun show(activity: Activity) | Display flowicon |
| fun hide() | Hide flowicon |
| fun dismiss() | Dismiss flowicon |
| fun setOWFlowConfig(config: OWFlowConfig) | Set config for flowicon |

#### OWFlowConfig

| Function | Description |
| :---: | :---: |
| fun setOWFlowEventListener(eventListener: RXOWFlowIconEventListener) | Set up ad interaction listeners |
| fun setImmersionStatusBar(immersionStatusBar: Boolean) | Set whether the hover window is immersed in the status bar (full-screen applications, games are recommended to pass true) |
| fun setLocation(x: Int, y: Int) | Set location for FlowIcon|
| fun setPercentLocation(xPercent: Int, yPercent: Int) | Set FlowIcon's position by screen percentage |
| fun setSize(width: Int, height: Int) | Set size for FlowIcon |

#### Set OWFlowConfig

    mFlowIconAd.setOWFlowConfig(new OWFlowConfig.Builder()
            .setImmersionStatusBar(true)
            .setOWFlowEventListener(new RXOWFlowIconEventListener() {
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
    		.build());

#### RXOWFlowIconEventListener
| Function | Description |
| :---: | :---: |
| onCreated | Ad create callback |
| onCreateError | Ad create error callback|
| onShow | Ad show callback |
| onShowFailure | Ad show failure callback|
| onHide | Ad hide callback |
| onDismiss | Ad dismiss callback |
| onClick | Ad click callback |
#### Show OfferWall FlowIcon
	mFlowIconAd.show(activity: Activity)

#### Hide OfferWall FlowIcon
	mFlowIconAd.hide()

#### Dismiss OfferWall FlowIcon
	mFlowIconAd.dismiss()

#### Sample Code

    RXSDK.createRXSdkAd().loadOWFlowIcon(unitId, new RXSdkAd.RXOWFlowIconAdListener() {
            @Override
            public void success(@NonNull RXAdInfo adInfo, @NonNull RXOWFlowIconAd flowIconAd) {
                Log.i(BridgeTag, "RXSDK flowicon load success");
                flowIconAd.setOWFlowConfig(new OWFlowConfig.Builder()
                    .setImmersionStatusBar(true)
                    .setOWFlowEventListener(new RXOWFlowIconEventListener() {
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
                    .build());
    			flowIconAd.show(activity)
            }
    
            @Override
            public void failure(@NonNull RXAdInfo adInfo, @NonNull RError error) {
    			RXLogUtil.d("RXSDK flowicon on load error: ${error.getErrorString()}")
            }
        });


## OfferWall NativeIcon

#### Load ad (Suggestion: Based on the developer's application usage scenarios, please request ads as soon as possible after the SDK is successfully initialized to ensure that they can be displayed quickly in ad display scenarios)

	RXSDK.createRXSdkAd().loadOWNativeIcon("unit_id",  object: RXSdkAd.RXOWNativeIconAdListener {
	        override fun failure(adInfo: RXAdInfo, error: RXError) {
	            RXLogUtil.d("native icon on load fail: ${error.msg}")
	        }
	
	        override fun success(adInfo: RXAdInfo, nativeIconAd: RXOWNativeIcon) {
	            RXLogUtil.d("native icon on load success")
	        }
	    })

#### Parameters

| Parameter | Description |
| :---: | :---: |
| YOU_UNIT_ID | Unit id of OfferWall NativeIcon，please apply in the developer backend or ask the commercial to provide |
| RXOWNativeIconAdListener | OfferWall NativeIcon request ad callback |

#### RXOWNativeIcon

| Function | Description |
| :---: | :---: |
| fun getIconResource(): String | Get the url of img resource for icon |
| fun click() | Call the click method of native icon |
|fun setRXOWNativeIconListener(listener: RXOWNativeIconEventListener)|Set up ad interaction listeners|

#### RXOWNativeIconEventListener
| Function | Description |
| :---: | :---: |
| fun onAdShowSuccess(adInfo: RXAdInfo) | Click to display OfferWall success callback |

#### Sapmle Code
	RXSDK.createRXSdkAd().loadOWNativeIcon("unit_id",  object: RXSdkAd.RXOWNativeIconAdListener {
	        override fun failure(adInfo: RXAdInfo, error: RXError) {
	            RXLogUtil.d("native icon on load fail: ${error.msg}")
	        }
	
	        override fun success(adInfo: RXAdInfo, nativeIconAd: RXNativeIconAd) {
	            RXLogUtil.d("native icon on load success")
	            nativeIconAd.setRXOWNativeIconListener(object : RXOWNativeIconEventListener {
	                override fun onAdShowSuccess(adInfo: RXAdInfo) {
	                    RXLogUtil.d("native icon on ad show success")
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

## *After completing the access, please try to see if the following modules can be completed normally, and after ensuring that everything is normal, contact the business classmate for testing before going online.
### 1. unit displays normally
### 2. Enter the offerwall interface and see the tasks
### 3. Click on the task to jump to google play
### 4. After completing the download of the task, you can see the task you just received in my apps.
### 5. Click play now to jump to the task application
### 6. You can get rewards after completing the task requirements.