

# Roulax SDK 集成文档 V2.0.X_ANDROID  

[English Document](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/English.md)  

### 更新日志
| 日期 | 版本 | 日志 |
|--|--|--|
| 2022-05-17 | 2.0.00 | Roulax SDK Release |

## 使用方式

### 添加依赖库
1、联系Roulax商务或者Roulax的技术支持同学，获取Roulax SDK android版本的AAR支持，目前提供支持是androidx版本的，如果需要非androidx版本，请联系Roulax平台。  
2、获取到之后，根据项目实际情况，在项目根目录添加RoulaxSDK的库引用。

<!-- 下载地址：[Roulax-SDK-Core](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/main_1.0.11/rsdk_1011_202205091149.aar) -->

### 添加SDK依赖的第三方库
	implementation group: 'com.google.android.gms', name: 'play-services-ads', version: '15.0.0'
	implementation 'com.google.guava:guava:31.0.1-android'
	implementation 'androidx.media:media:1.4.3'

### 添加SDK依赖的权限申明

  	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>


### SDK混淆规则
    # 不混淆JS
	-keep public class com.rad.cache.database.entity.**
	-keepclassmembers public class com.rad.cache.database.entity.**{
   		public *;
	}
	-keep public class com.rad.cache.database.dao.**
	-keepclassmembers public class com.rad.cache.database.dao.**{
   		public *;
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

##### RXSplashAd说明

| 方法 | 含义 |
| --- | --- |
| fun isReady()| 判断广告是否准备 |
| fun getSplashView(activity: Activity): View? | 获得开屏广告 |
| fun setEventListener(eventListener: RXSplashEventListener) | 设置广告交互监听器 |

##### 设置广告交互监听器

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

##### RXSplashEventListener说明
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


## 隐私-GDPR
Roulax 会收集 Language、设备信息、GAID 这些信息并上报这些数据，用于确定用户ID。如果应用需要上架到 GooglePlay，您需要在 GooglePlay 开发者控制台上和隐私政策协议中声明使用条款，如有疑问，请联系Roulax平台。

## 聚合平台支持
当前为最新版本适配器，如您需要历史版本，请前往对应netowrk document进行下载

| 聚合平台|支持广告样式|接入文档|下载地址|
| -------------|:-------------:|:-------------: |:-------------: |
| TopOn| 插屏 | [TopOn-Network-Document](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/adapter/topon/chinese.md) |[TopOn-Network-Adapter](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/adapter_topon/rad_adapter_topon_0.0.3-release.aar)|
| Max  | 插屏 | [Max-Network-Document](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/adapter/Max/RSDK-MAX-Chinese.md) |[Max-Network-Adapter](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/network_max/rad-adapter-max.aar)|

