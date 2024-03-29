

# Roulax SDK 集成文档 V2.0.X_ANDROID  


### 更新日志
| 日期 | 版本 | 日志 |
|--|--|--|
| 2022-06-17 | 2.0.00 | 1.Roulax SDK Release |
| 2022-07-14 | 2.1.01 | 1.广告模板支持 |
| 2022-08-01 | 2.1.04 | 1.优化已知问题 |
| 2022-08-23 | 2.1.05 | 1.优化已知问题<br>2.增加下载模块 |
| 2022-09-09 | 2.1.06 | 1.优化已知问题 |
| 2022-09-21 | 2.1.07 | 1.增加Topon头部竞价支持<br>2.FlowIcon增加设置自定义位置，大小的方法以及激励回调<br>3.优化已知问题 |
| 2022-10-27 | 2.1.08 | 1.flowicon新增可选模板<br>2.激励视频新增endcard模板。<br>3.优化已知问题。 |
| 2022-12-01 | 2.1.10 | 1.新增H5互动Game曝光（onGameShow）和参与点击（onGameStart）回调<br>2.splash新增摇一摇跳转<br>3.优化广告流程，优化广告数据和广告效果<br>4.优化已知问题 |
| 2022-12-27 | 2.1.11 | 1.优化RV/IV 类型的广告链路和性能；使得广告投放更加精准，提升广告收益 |

## 版本升级注意事项 
| 原版本 | 新版本 | 注意事项 |
| :---: | :---: | :--- |
| 2.1.04 | 2.1.07 | 1、FlowIcon新增自定位置<br>fun setLocation(x: Int, y: Int) 设置悬浮窗位置（优先级高）<br>fun setPercentLocation(xPercent: Int, yPercent: Int) 按照屏幕百分比设置悬浮窗位置（优先级低）<br>2、FlowIcon新增自定大小<br>fun setSize(width: Int, height: Int) 设置悬浮窗大小<br>3、FlowIcon新增激励回调<br>fun onRewarded(adInfo: RXAdInfo) |

## 使用方式

### 添加依赖库
1、联系Roulax商务或者Roulax的技术支持同学，获取Roulax SDK android版本的AAR支持，目前提供支持是androidx版本的，如果需要非androidx版本，请联系Roulax平台。  
2、获取到之后，根据项目实际情况，在项目根目录添加RoulaxSDK的库引用。

| Roulax 依赖库 | 描述 | 是否必要 |
|--|--|--|
| rad_library_core-release.aar 			| sdk广告核心库 		| √ |
| rad_library_common-release.aar 		| sdk通用模块库 		| √ |
| rad_library_nativeicon-release.aar 	| nativeicon广告库 	| × |
| rad_library_splash-release.aar 		| splash广告库 		| × |
| rad_library_banner-release.aar 		| banner广告库 		| × |
| rad_library_native-release.aar 		| native广告库 		| × |
| rad_library_flowicon-release.aar 		| flowicon广告库 	| × |
| rad_library_interstitial-release.aar	| interstitial广告库 | × |
| rad_library_rewardvideo-release.aar 	| rewardvideo广告库  | × |
| rad_library_trace-release.aar         | sdk奔溃上报库      |  ×  |
| rad_library_playercommon-release.aar 	| sdk视频依赖库      | 如果需要rewardvideo或interstitial广告类型，则必要 |
| rad_library_download-release.aar 		| sdk下载模块 	    | 线下包(非GP开发者)如果需要sdk下载功能，则可依赖.<br>GP开发者建议不依赖.|

### 添加SDK依赖的第三方库
	implementation group: 'com.google.android.gms', name: 'play-services-ads', version: '15.0.0'
	implementation 'androidx.appcompat:appcompat:1.3.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'

### 添加SDK依赖的权限申明

    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>


### SDK混淆规则
    
	-keep class com.rad.**
	-keepclassmembers public class com.rad.**{
        *;
	}
	-keep interface com.rad.**
    -keepclassmembers interface com.rad.**{
        *;
    }	


## 初始化

原则上请保持在项目Application中进行初始化操作<br>

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


## 开屏广告

开屏广告的场景，您可以在应用启动或应用后台切换至前台时进行展示，但是为了您的收益最大化，您可以联系Roulax的商务和运营，辅助您设计最优变现思路和方式，Roulax将倾尽全力为您提供最好的服务。

#### 请求广告
	
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
	

#### 参数说明

| 参数 | 含义 |
| --- | --- |
| YOU_UNIT_ID | 开屏广告的ID，请在开发者后台或者找商务提供 |
| TIMEOUT | 是加载开屏广告允许的最长时间（注：建议 >= 5秒） |
| RXSplashAdListener | 开屏请求广告回调 |

#### RXSplashAdListener说明

| 方法 | 含义 |
| --- | --- |
| success | 广告加载完成的回调，可以在这个回调中进行展示 |
| failure | 广告请求失败回调 返回的错误信息(RError)表示广告请求失败的原因 |
| timeout | 开屏广告加载超时回调 |

#### RXSplashAd说明

| 方法 | 含义 |
| --- | --- |
| fun isReady()| 判断广告是否准备 |
| fun getSplashView(activity: Activity): View? | 获得开屏广告 |
| fun setEventListener(eventListener: RXSplashEventListener) | 设置广告交互监听器 |

#### 设置广告交互监听器

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
    mRXSplashAd.setRXGameListener(object : RXGameListener {
        override fun onGameShow(adInfo: RXAdInfo) {
            logToast("splash ad on game show")
        }
    
        override fun onGameStart(adInfo: RXAdInfo) {
            logToast("splash ad on game start")
        }
    })

#### RXSplashEventListener说明
| 方法 | 含义 |
| --- | --- |
| onShowSuccess | 展示回调 |
| onShowFailure | 展示失败回调 |
| onClick | 广告点击回调 |
| onDismiss | 广告关闭回调 |

#### 展示广告
	val splashView = mRXSplashAd.getSplashView(activity)
	splashView?.let {
	    mSplashContainer.addView(it)
	}?:let {
	    RXLogUtil.d("Splash view isEmpty")
	}

#### 示例代码

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

## 激励视频
激励视频是Roulax平台效果非常好的广告形式，用户可以通过观看一段广告视频而获取奖励，由于广告视频素材非常具有创意和吸引力，因此会引起很多用户的兴趣，进而下载广告产品
#### 广告效果样式：

#### 请求广告

```
RXSDK.createRXSdkAd().loadRewardVideo("unit_id", object : RXSdkAd.RXRewardVideoAdListener {
    override fun success(adInfo: RXAdInfo, rewardVideoAd: RXRewardVideoAd) {
        
    }

    override fun failure(adInfo: RXAdInfo, error: RError) {

    }

})

```
| 参数 | 含义 |
| --- | --- |
| YOU_UNIT_ID | 激励广告的ID，请在开发者后台或者找商务提供 |
| RXRewardVideoAdListener | 激励视频请求广告回调 |

#### RXRewardVideoAd说明

| 方法 | 含义 |
| :---: | :---: |
| fun setRewardVideoEventListener(listener: RXRewardVideoEventListener)|设置广告交互监听器|
| fun isReady(): Boolean | 判断广告是否准备 |
| fun show() | 展示激励视频广告 |
| fun release() | 销毁广告并释放资源 |
| fun setAlertDialogText(text: String) | 激励视频提前关系弹窗提示 |

#### RXRewardVideoEventListener说明

| 方法 | 含义 |
| :---: | :---: |
| fun onAdShow(adInfo: RXAdInfo) | 广告展示回调 |
| fun onAdShowFail(adInfo: RXAdInfo, error: RError) | 广告展示失败回调，附带原因 |
| fun onAdClick(adInfo: RXAdInfo) | 广告点击回调 |
| fun onAdClose(adInfo: RXAdInfo) | 广告关闭回调 |
| fun onRewarded(adInfo: RXAdInfo) | 奖励验证回调，开发者在需要在此回调中做奖励的发放 |

#### 示例代码

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
        rewardVideoAd.setRXGameListener(object : RXGameListener {
            override fun onGameShow(adInfo: RXAdInfo) {
                logToast("rewardvideo ad on game show")
            }

            override fun onGameStart(adInfo: RXAdInfo) {
                logToast("rewardvideo ad on game start")
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

## 插屏
插屏分为全屏视频和插屏，分别为图片+视频、仅图片。适用于更多应用场景切换时使用，如：应用内视图切换、游戏过关或失败、图书翻页、应用退出等。
#### 广告效果样式：

#### 请求广告
    RXSDK.createRXSdkAd().loadInterstitial("unit_id", object : RXSdkAd.RXInterstitialAdListener {
        override fun success(adInfo: RXAdInfo, interAd: RXInterstitialAd) {
    		// load ad success, return RXInterstitialAd
        }
    
        override fun failure(adInfo: RXAdInfo, error: RError) {
            
        }
    })

#### RXInterstitialAd说明

| Method | Description |
| :---: | :---: |
| fun setInterstitialEventListener(listener: RXInterstitialEventListener)|设置广告交互监听器|
| fun isReady(): Boolean | 判断广告是否准备 |
| fun show() | 展示插屏广告 |
| fun release() | 销毁广告并释放资源 |

#### RXInterstitialEventListener说明

| Method | Description |
| :---: | :---: |
| fun onAdShow(adInfo: RXAdInfo) | 广告展示回调 |
| fun onAdShowFail(adInfo: RXAdInfo, error: RError) | 广告展示失败回调，附带原因 |
| fun onAdClick(adInfo: RXAdInfo) | 广告点击回调 |
| fun onAdClose(adInfo: RXAdInfo) | 广告关闭回调 |

#### 示例代码

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
            interAd.setRXGameListener(object : RXGameListener {
                override fun onGameShow(adInfo: RXAdInfo) {
                    logToast("inter ad on game show")
                }

                override fun onGameStart(adInfo: RXAdInfo) {
                    logToast("inter ad on game start")
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
横幅广告是移动广告中最传统的广告类型。它很容易实现，而且用户可接受度高。它是超休闲游戏和工具的良好实现形式。
#### 请求广告

```
RXSDK.createRXSdkAd().loadBanner(context, "unit_id", object : RXSdkAd.RXBannerAdListener {
    override fun success(adInfo: RXAdInfo, bannerAd: RXBannerAd) {
        
    }

    override fun failure(adInfo: RXAdInfo, error: RError) {

    }

})
```

#### RXBannerAd

| 方法 | 描述|
| :---: | :---: |
| fun setRXBannerListener(listener: RXBannerEventListener)|为banner注册交互监听回调|
| fun render() | 渲染banner view |


#### RXBannerEventListener

| 方法 | 描述 |
| :---: | :---: |
| fun onAdShow(adInfo: RXAdInfo) | 广告展示回调 |
| fun onAdClick(adInfo: RXAdInfo) | 广告点击回调 |
| fun onAdClose(adInfo: RXAdInfo) | 广告关闭回调 |
| fun onRenderSuccess(pView: View) | 广告渲染成功，并且返回渲染成功的View |
| fun onRenderFail(pAdInfo: RXAdInfo, pError: RError) | 广告渲染失败，并且返回错误原因 |

#### 示例代码

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
            	})
            	bannerAd.setRXGameListener(object : RXGameListener {
                    override fun onGameShow(adInfo: RXAdInfo) {
                        logToast("banner ad on game show")
                    }

                    override fun onGameStart(adInfo: RXAdInfo) {
                        logToast("banner ad on game start")
                    }
                })
				bannerAd.render()
			}
	})

```

#### 注意
请在调用render方法之前调用setRXBannerListener，以确保第一时间获取到渲染成功的广告view。

## Native
原生广告是目前最流行的广告类型之一。Roulax SDK将向您的应用程序返回创意材料信息。你将能够把它放到你的产品中，进而创造最佳的用户体验。
#### 请求广告

```
 RXSDK.createRXSdkAd().loadNative(context, "unit_id", requestNum, object: RXSdkAd.RXNativeAdListener {
            override fun failure(adInfo: RXAdInfo, errorList: List<RError>) {
                
            }

            override fun success(adInfo: RXAdInfo, nativeAdList: List<RXNativeAd>) {
               
            }
		})
```

#### RXNativeAd

| 方法 | 描述|
| :---: | :---: |
| fun setRXNativeListener(listener: RXNativeEventListener)|为native注册交互监听回调|
| fun render() | 渲染native view|

#### RXNativeEventListener

| 方法 | 描述|
| :---: | :---: |
| fun onAdShow(adInfo: RXAdInfo) | 广告展示回调 |
| fun onAdClick(adInfo: RXAdInfo) | 广告点击回调 |
| fun onAdClose(adInfo: RXAdInfo) | 广告关闭回调 |
| fun onRenderSuccess(pView: View) | 广告渲染成功，并且返回渲染成功的View |
| fun onRenderFail(pAdInfo: RXAdInfo, pError: RError) | 广告渲染失败，并且返回失败的原因 |

#### 示例代码

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
                    nativeAdList[0].setRXGameListener(object : RXGameListener {
                        override fun onGameShow(adInfo: RXAdInfo) {
                            logToast("native ad on game show")
                        }

                        override fun onGameStart(adInfo: RXAdInfo) {
                            logToast("native ad on game start")
                        }
                    })
					nativeAdList[0].render()
                }
            }
       })
```
#### 注意
请在调用render方法之前调用setRXNativeListener，以确保第一时间获取到渲染成功的广告view。

## 悬浮窗广告

悬浮窗广告是Roulax平台特殊的广告形式，该广告形式以灵活的配置可悬浮于开发者应用中，并提供展示广告的入口；

#### 请求广告

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


#### 参数说明

| 参数 | 含义 |
| :---: | :---: |
| YOU_UNIT_ID | FlowIcon广告的ID，请在开发者后台或者找商务提供 |
| RXFlowIconAdListener | FlowIcon请求广告回调 |

#### RXFlowIconAdListener说明

| 方法 | 含义 |
| :---: | :---: |
| success | 广告加载完成的回调，可以在这个回调中进行展示 |
| failure | 广告请求失败回调 返回的错误信息(RError)表示广告请求失败的原因 |

#### RXFlowIconAd说明

| 方法 | 含义 |
| :---: | :---: |
| fun show(activity: Activity) | 展示悬浮窗 |
| fun hide() | 隐藏悬浮窗 |
| fun dismiss() | 销毁悬浮窗 |
| fun setFlowConfig(config: FlowConfig) | 设置悬浮窗参数 |

#### FlowConfig说明

| 方法 | 含义 |
| :---: | :---: |
| fun setFlowIdelCallback(callback: OnFlowIdelCallback) | 设置悬浮窗广告空闲交互监听器，当用户在广告空闲时点击FlowIcon时进行回调。开发者可在该回调中实现广告空闲时的用户交互，SDK默认会实现 |
| fun setFlowEventListener(eventListener: RXFlowIconEventListener) | 设置悬浮窗广告交互监听器 |
| fun setImmersionStatusBar(immersionStatusBar: Boolean) | 设置悬浮窗是否沉浸式状态栏（全屏应用、游戏建议传true） |
| fun setLocation(x: Int, y: Int) | 设置悬浮窗位置（优先级高） |
| fun setPercentLocation(xPercent: Int, yPercent: Int) | 按照屏幕百分比设置悬浮窗位置（优先级低） |
| fun setSize(width: Int, height: Int) | 设置悬浮窗大小 |

#### 设置悬浮窗参数

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
             .setRXGameListener(object : RXGameListener {
                 override fun onGameShow(adInfo: RXAdInfo) {
                     logToast("FlowIcon onGameShow $adInfo")
                 }
    
                 override fun onGameStart(adInfo: RXAdInfo) {
                      logToast("FlowIcon onGameStart $adInfo")
                 }
             })
    		.setFlowIdelCallback(new OnFlowIdelCallback() {
    			@Override
                public void idelHandler() {
                    RXLogUtil.d("RXSDK flowicon on idel callback")
                }
            })
    		.build());

#### RXFlowIconEventListener说明
| 方法 | 含义 |
| :---: | :---: |
| onCreated | FlowIcon创建回调 |
| onCreateError | FlowIcon创建失败回调 |
| onShow | FlowIcon展示回调 |
| onShowFailure | FlowIcon展示失败回调 |
| onHide | FlowIcon隐藏回调 |
| onDismiss | FlowIcon销毁回调 |
| onClick | FlowIcon点击回调 |
| onReward | FlowIcon激励回调 |

#### OnFlowIdelCallback说明
| 方法 | 含义 |
| :---: | :---: |
| idelHandler | FlowIcon广告空闲回调 |

#### 展示悬浮窗广告
	mFlowIconAd.show(activity: Activity)

#### 隐藏悬浮窗广告
	mFlowIconAd.hide()

#### 销毁悬浮窗广告
	mFlowIconAd.dismiss()

#### 示例代码

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
    
                        @Override
                        public void onRewarded(@NonNull RXAdInfo adInfo) {
                            RXLogUtil.d("RXSDK flowicon onRewarded")
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

## NativeIcon广告

NativeIcon广告是Roulax平台特殊的广告形式，该广告可以为开发者提供入口素材以供开发者渲染，并且可以为开发者展示精彩的广告。

#### 请求广告

	RXSDK.createRXSdkAd().loadNativeIcon("unit_id",  object: RXSdkAd.RXNativeIconAdListener {
	        override fun failure(adInfo: RXAdInfo, error: RXError) {
	            RXLogUtil.d("native icon on load fail: ${error.msg}")
	        }
	
	        override fun success(adInfo: RXAdInfo, nativeIconAd: RXNativeIconAd) {
	            RXLogUtil.d("native icon on load success")
	        }
	    })

#### NativeIconAd

| 方法 | 描述 |
| :---: | :---: |
| fun getIconResource(): String | 获取icon的入口素材url |
| fun click() | 调用native icon的点击以展示广告 |
|fun setRXNativeIconListener(listener: RXNativeIconEventListener)|为native icon设置交互监听回调|
| fun onImpression()|如果开发者使用自己准备的图片素材渲染入口，而不是使用getIconResource()中返回的入口素材来渲染入口的话，请在渲染成功后调用omImpression来协助SDK上报展示|

#### RXNativeIconEventListener
| 方法 | 描述 |
| :---: | :---: |
| fun onAdShowSuccess(adInfo: RXAdInfo) | 点击展示广告成功回调 |
| fun onAdShowFailure(adInfo: RXAdInfo, adError: RXError) | 点击展示广告失败回调  |
| fun onRewarded(adInfo: RXAdInfo) | 广告激励回调 |
| fun onClosed(adInfo: RXAdInfo) | 广告页面关闭回调 |
| fun onRefresh(adInfo: RXAdInfo, imgUrl: String) | 广告自动刷新回调,返回刷新后的入口素材 |

#### 示例代码
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
	            nativeIconAd.setRXGameListener(object : RXGameListener {
	                 override fun onGameShow(adInfo: RXAdInfo) {
	                     logToast("native icon on game show")
	                 }
	
	                 override fun onGameStart(adInfo: RXAdInfo) {
	                     logToast("native icon on game start")
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



## 隐私-GDPR
Roulax 会收集 Language、设备信息、GAID 这些信息并上报这些数据，用于确定用户ID。如果应用需要上架到 GooglePlay，您需要在 GooglePlay 开发者控制台上和隐私政策协议中声明使用条款，如有疑问，请联系Roulax平台。

## 聚合平台支持
当前为最新版本适配器，如您需要历史版本，请前往对应netowrk document进行下载

| 聚合平台|支持广告样式|接入文档|下载地址|
| -------------|:-------------:|:-------------: |:-------------: |
| TopOn| 插屏 | [TopOn-Network-Document](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/adapter/topon/chinese.md) |[TopOn-Network-Adapter](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/adapter_topon/rad_adapter_topon_0.0.3-release.aar)|
| Max  | 插屏、激励视频、Banner | [Max-Network-Document](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/adapter/Max/RSDK-MAX-Chinese.md) |[Max-Network-Adapter](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/network_max/rad_adapter_max-2.0_release.aar)|

