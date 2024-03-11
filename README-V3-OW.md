
# Roulax SDK 集成文档 V3.0.X_ANDROID  


### 更新日志
| 日期 | 版本 | 日志 |
|--|--|--|
| 2022-03-23 | 3.0.00 | 增加OfferWall类型广告形式 |
| 2022-04-10 | 3.0.01 | 增加GDPR相关接口，优化OfferWall广告 |
| 2023-05-31 | 3.0.05 | 修复已知问题，优化内部逻辑 |
| 2023-08-14 | 3.0.11 | 修复已知问题；增加SDK offerwall奖励查询和回调接口 |
| 2023-08-28 | 3.0.14 | 修复已知问题；增加全新的激励机制 |
| 2024-03-08 | 3.0.19 | 修复已知问题； |


## 使用方式

### 添加依赖库
1、联系Roulax商务或者Roulax的技术支持同学，获取Roulax SDK android版本的AAR支持，目前提供支持是androidx版本的，如果需要非androidx版本，请联系Roulax平台。  
2、获取到之后，根据项目实际情况，在项目根目录添加RoulaxSDK的库引用。

| Roulax 依赖库 | 描述 | 是否必要 |
|--|--|--|
| rad_library_core-release.aar 			| sdk广告核心库 		| √ |
| rad_library_common-release.aar 		| sdk通用模块库 		| √ |
| rad_library_ow-core-release.aar 		| offerwall广告核心库 	    | √ |
| rad_library_ow_native-release.aar 	| offerwall native广告库 	    | × |
| rad_library_ow_nativeicon-release.aar | offerwall nativeicon广告库 	| × |
| rad_library_ow_flowicon-release.aar 	| offerwall flowicon广告库 	    | × |
| rad_library_trace-release.aar         | sdk奔溃上报库       |  ×  |

### 添加SDK依赖的第三方库
	implementation group: 'com.google.android.gms', name: 'play-services-ads', version: '15.0.0'
	implementation 'androidx.appcompat:appcompat:1.3.0'
	implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
	implementation 'androidx.recyclerview:recyclerview:1.2.1'
	implementation 'androidx.cardview:cardview:1.0.0'

### 添加SDK依赖的权限申明

    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    
    \*when apps update their target to Android 13 or above will need to declare a Google Play services normal permission in the manifest file*/
    <uses-permission android:name="com.google.android.gms.permission.AD_ID"/>
    
    \*注意: 如果接入OfferWall类型广告，为了计算Offer使用时长，rad_library_ow-core-release.aar中会包含以下权限
    <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" tools:ignore="ProtectedPermissions"/>*/


### SDK混淆规则

	-keep class com.rad.**
	-keepclassmembers public class com.rad.**{
	    *;
	}
	-keep interface com.rad.**
	-keepclassmembers interface com.rad.**{
	    *;
	}	



## 隐私-GDPR

Roulax 会收集 Language、设备信息、GAID 这些信息并上报这些数据，用于确定用户ID。如果应用需要上架到 GooglePlay，您需要在 GooglePlay 开发者控制台上和隐私政策协议中声明使用条款，如有疑问，请联系Roulax平台。

**注**：如果开发者的用户在欧盟地区，需要在SDK**初始化之前**设置GDPR授权，如果不调用此接口，SDK将默认在欧盟相关地区不收集信息，SDK初始化失败。

	RXSDK.setGDPRAuth(mode RXSDK.GDPRMode)

#### 参数说明

| 参数              | 含义                                                       |
| ----------------- | ---------------------------------------------------------- |
| GDPRMode.Allow    | 用户同意用户协议及隐私授权；                               |
| GDPRMode.NotAllow | 用户拒绝用户协议及隐私授权；                               |
| GDPRMode.Auto     | SDK默认值；在欧盟相关地区，不收集用户信息，SDK初始化失败； |



## 初始化

原则上请保持在项目Application中进行初始化操作<br>
如果有GDPR相关需求，需要在用户授权GDPR之后进行初始化。

	//在用户授权GDPR后，将其设置为Allow
	RXSDK.setGDPRAuth(RXSDK.GDPRMode.Auto);
	
	RXSDK.init(YOU_APPID, object : RXSDK.RXSDKInitListener {
	    override fun onSDKInitSuccess() {
	        RXLogUtil.d("onSDKInitSuccess")
	    }
	
	    override fun onSDKInitFailure(error: RError?) {
	       RXLogUtil.d("onSDKInitFailure ${error?.msg}")
	    }
	})

#### 参数说明

| 参数 | 含义 |
| --- | --- |
| YOU_APPID | 当前应用的appid，请自行在Roulax开发者后台创建后获取或者联系Roulax商务帮您创建，并且提供给您 |
| RXSDKInitListener | 广告初始化回调 |

#### RXSDKInitListener说明

| 方法 | 含义 |
| --- | --- |
| onSDKInitSuccess | SDK初始化成功回调 |
| onSDKInitFailure | SDK初始化失败回调，返回的错误信息(RError)表示初始化失败的原因 |


**注意**：必须在SDK初始化成功回调后进行才能进行Roulax的广告请求，否则会造成无填充；

#### RXSDKInitListener说明

| 方法 | 含义 |
| --- | --- |
| onSDKInitSuccess | SDK初始化成功回调 |
| onSDKInitFailure | SDK初始化失败回调，返回的错误信息(RError)表示初始化失败的原因 |


**注意**：必须在SDK初始化成功回调后进行才能进行Roulax的广告请求，否则会造成无填充；



## OfferWall类型广告

## 奖励下发方式
目前仅支持S2S的方式进行奖励下发，客户端的奖励回调将会在之后的版本拓展开放。如需要S2S对接，请联系商务获取支持。

## 设置唯一用户标识

<b>如果需要接入OfferWall类型广告，请务必在RXSDK初始化成功之后尽早设置用户标识，这关系到OfferWall奖励的计算以及下发。</b> 
<b>如果不设置，所有的OfferWall广告类型将无法请求。</b>

    RXWallApi.setUserId("USER_ID")

#### 示例代码

	RXSDK.init(YOU_APPID, object : RXSDK.RXSDKInitListener {
	    override fun onSDKInitSuccess() {
	        RXLogUtil.d("onSDKInitSuccess")
			RXWallApi.setUserId("USER_ID")
	    }
	
	    override fun onSDKInitFailure(error: RError?) {
	       RXLogUtil.d("onSDKInitFailure ${error?.msg}")
	    }
	})


#### 参数说明

| 参数 | 含义 |
| --- | --- |
| USER_ID | 开发者自己定义并生成的唯一用户标识 |



## 用户奖励

<b>如果需要通过SDK查询OfferWall的用户奖励，可在设置唯一用户标识后通过RXWallApi.getUserRewarded()进行查询；</b> 

```kotlin
val userReward = RXWallApi.getUserRewarded()
```

<b>如果需要监听SDK OfferWall 用户奖励更新，可通过设置回调</b>

```kotlin
RXWallApi.setOfferWallRewardListener(object : RXWallRewardListener {
    override fun onRewardChanged(userId: String, totalReward: Long) {
        shortToast("$userId reward: $totalReward")
    }
 })
```

注：1、必须设置UserId后才可使用OfferWall用户奖励查询方法；2、SDK不保证用户奖励实时和绝对精准，如需更实时精准的用户奖励，请参考接入S2S；



## 权益奖励

<b>如果需要监听SDK 权益奖励的实时兑换，可通过设置回调</b>

```kotlin
RXWallApi.setOfferWallPrizeRewardListener(object : RXWallPrizeRewardListener {
     override fun onReward(prizeId: String) {
         shortToast("Prize reward: $prizeId")
     }
})
```



## OfferWall Native

#### 请求广告（建议：结合开发者应用使用场景，请在SDK初始化成功后，尽快请求广告，保证在广告展示场景能够快速展示）

```
 RXSDK.createRXSdkAd().loadOWNative(context, "unit_id", requestNum, object: RXSdkAd.RXOWNativeAdListener {
            override fun failure(adInfo: RXAdInfo, errorList: List<RError>) {
                
            }

            override fun success(adInfo: RXAdInfo, nativeAdList: List<RXOWNativeAd>) {
               
            }
		})
```

| 参数 | 含义 |
| --- | --- |
| unit_id | OfferWallNative广告的ID，请在开发者后台获取或者找商务提供 |
| requestNum | 请求OfferWall Native的数量。（当前版本只支持单次请求一个OfferWallNative。无论requestNum设置多少，都将只请求返回一个RXOWNativeAd对象）|
| RXOWNativeAdListener | 广告请求结果监听 |

#### RXOWNativeAdListener

| 方法 | 描述|
| :---: | :---: |
| success | 广告加载完成的回调 |
| failure | 广告请求失败回调 返回的错误信息(RError)表示广告请求失败的原因 |

#### RXOWNativeAd

| 方法 | 描述|
| :---: | :---: |
| fun setRXOWNativeListener(listener: RXOWNativeEventListener)|为native注册交互监听回调|
| fun render() | 渲染native view|

#### RXOWNativeEventListener

| 方法 | 描述|
| :---: | :---: |
| fun onAdShow(adInfo: RXAdInfo) | 广告展示回调 |
| fun onAdClick(adInfo: RXAdInfo) | 广告点击回调 |
| fun onAdClose(adInfo: RXAdInfo) | 广告关闭回调 |
| fun onRenderSuccess(pView: View) | 广告渲染成功，并且返回渲染成功的View |
| fun onRenderFail(pAdInfo: RXAdInfo, pError: RError) | 广告渲染失败，并且返回失败的原因 |

#### 示例代码

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
#### 注意
请在调用render方法之前调用setRXOWNativeListener，以确保第一时间获取到渲染成功的广告view。

## OfferWall悬浮窗广告

#### 请求广告（建议：结合开发者应用使用场景，请在SDK初始化成功后，尽快请求广告，保证在广告展示场景能够快速展示）

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


#### 参数说明

| 参数 | 含义 |
| :---: | :---: |
| YOU_UNIT_ID | FlowIcon广告的ID，请在开发者后台或者找商务提供 |
| RXOWFlowIconAdListener | OWFlowIcon请求广告回调 |

#### RXOWFlowIconAdListener说明

| 方法 | 含义 |
| :---: | :---: |
| success | 广告加载完成的回调，可以在这个回调中进行展示 |
| failure | 广告请求失败回调 返回的错误信息(RError)表示广告请求失败的原因 |

#### RXOWFlowIconAd说明

| 方法 | 含义 |
| :---: | :---: |
| fun show(activity: Activity) | 展示悬浮窗 |
| fun hide() | 隐藏悬浮窗 |
| fun dismiss() | 销毁悬浮窗 |
| fun setOWFlowConfig(config: OWFlowConfig) | 设置悬浮窗参数 |

#### OWFlowConfig说明

| 方法 | 含义 |
| :---: | :---: |
| fun setOWFlowEventListener(eventListener: RXOWFlowIconEventListener) | 设置悬浮窗广告交互监听器 |
| fun setImmersionStatusBar(immersionStatusBar: Boolean) | 设置悬浮窗是否沉浸式状态栏（全屏应用、游戏建议传true） |
| fun setLocation(x: Int, y: Int) | 设置悬浮窗位置（默认位置为屏幕 x轴80%，y轴80%）） |
| fun setPercentLocation(xPercent: Int, yPercent: Int) | 按照屏幕百分比设置悬浮窗位置（默认位置为屏幕 x轴80%，y轴80%）） |
| fun setSize(width: Int, height: Int) | 设置悬浮窗大小（默认大小为60dp） |

#### 设置悬浮窗参数

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

#### RXOWFlowIconEventListener说明
| 方法 | 含义 |
| :---: | :---: |
| onCreated | FlowIcon创建回调 |
| onCreateError | FlowIcon创建失败回调 |
| onShow | FlowIcon展示回调 |
| onShowFailure | FlowIcon展示失败回调 |
| onHide | FlowIcon隐藏回调 |
| onDismiss | FlowIcon销毁回调 |
| onClick | FlowIcon点击回调 |

#### 展示悬浮窗广告
	mFlowIconAd.show(activity: Activity)

#### 隐藏悬浮窗广告
	mFlowIconAd.hide()

#### 销毁悬浮窗广告
	mFlowIconAd.dismiss()

#### 示例代码

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


## OfferWall NativeIcon广告

#### 请求广告（建议：结合开发者应用使用场景，请在SDK初始化成功后，尽快请求广告，保证在广告展示场景能够快速展示）

	RXSDK.createRXSdkAd().loadOWNativeIcon("unit_id",  object: RXSdkAd.RXOWNativeIconAdListener {
	        override fun failure(adInfo: RXAdInfo, error: RXError) {
	            RXLogUtil.d("native icon on load fail: ${error.msg}")
	        }
	
	        override fun success(adInfo: RXAdInfo, nativeIconAd: RXOWNativeIcon) {
	            RXLogUtil.d("native icon on load success")
	        }
	    })

#### 参数说明

| 参数 | 含义 |
| :---: | :---: |
| YOU_UNIT_ID | FlowIcon广告的ID，请在开发者后台或者找商务提供 |
| RXOWNativeIconAdListener | OWNativeIcon请求广告回调 |

#### RXOWNativeIcon

| 方法 | 描述 |
| :---: | :---: |
| fun getIconResource(): String | 获取icon的入口素材url（开发者如需定制化素材，可不使用广告返回的icon素材） |
| fun click() | 调用native icon的点击以展示广告 |
|fun setRXOWNativeIconListener(listener: RXOWNativeIconEventListener)|为native icon设置交互监听回调|


#### RXOWNativeIconEventListener
| 方法 | 描述 |
| :---: | :---: |
| fun onAdShowSuccess(adInfo: RXAdInfo) | 点击展示广告成功回调 |

#### 示例代码
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

## *完成接入后，请尝试查看以下事项是否能正常完成，确保一切正常后联系业务同学进行测试后再上线。
### 1.广告位展示正常
### 2.能够进入积分墙界面，并看到任务
### 3.点击任务能够跳转到google play
### 4.任务下载完成后，可以在my apps中看到任务
### 5.点击play now，能够跳转至任务应用
### 6.完成任务要求后能够获得奖励