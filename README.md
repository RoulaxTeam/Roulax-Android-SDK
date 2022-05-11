

# Roulax SDK 集成文档 V1.0.0_ANDROID

## 使用方式

### 添加依赖库
1、联系Roulax商务或者Roulax的技术支持同学，获取Roulax SDK android版本的AAR支持，当前版本为1.0.11 ，目前提供支持是androidx版本的，如果需要非androidx版本，请联系Roulax平台。  
2、获取到之后，根据项目实际情况，在项目根目录添加RoulaxSDK的库引用。

下载地址：[Roulax-SDK-Core](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/main_1.0.11/rsdk_1011_202205091149.aar)

### 添加SDK依赖的第三方库
	implementation group: 'com.google.android.gms', name: 'play-services-ads', version: '15.0.0'

### 添加SDK依赖的权限申明

  	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>


### SDK混淆规则
     #  不混淆JS
	-keepattributes *Annotation*
	-keepattributes *JavascriptInterface*
	-keep class com.rad.adlibrary.RSDKJavaScriptInterface{
	*;
	}
	-keep class com.rad.adlibrary.RSSDKJavaScriptInterface{
	*;
	}


## 初始化

原则上请保持在项目Application中进行初始化操作<br>
**YOU_APPID**:当前应用的appid，请自行在Roulax开发者后台创建后获取或者联系Roulax商务帮您创建，并且提供给您<br>
**YOU_APPKEY**:您开发者账号的key,可以在开发者后台获取，或者联系Roulax商务提供。<br>


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


## 互动广告

互动广告的入口，您可以随意设定，Roulax在互动广告的模式下，不会有任何入口要求，但是为了您的收益最大化，您可以联系Roulax的商务和运营，辅助您设计最优变现思路和方式，Roulax将倾尽全力为您提供最好的服务。

### 互动广告接入--初始化

互动广告初始化，你可以在需要展示互动广告之前初始化<br>
**YOU_UNIT_ID**:是互动广告的ID，请在开发者后台或者找商务提供。


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
			* 互动游戏的展示成功
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
			* 互动游戏成功之后的展示
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


### 互动广告接入--请求

您可以在需要展示互动广告之前，提前请求缓存好互动广告的相关信息，等需要展示的时候，可以即时展示）。

	 interactiveAd.request();


### 互动广告接入--展示广告

广告展示的逻辑。

 	 if (interactiveAd.isReady()) {
         interactiveAd.show(MainActivity.this);
     }

## 隐私-GDPR
Roulax 会收集 Language、设备信息、GAID 这些信息并上报这些数据，用于确定用户ID。如果应用需要上架到 GooglePlay，您需要在 GooglePlay 开发者控制台上和隐私政策协议中声明使用条款，如有疑问，请联系Roulax平台。

## 聚合平台支持
当前都为最新版本适配器，如您需要历史版本，请前往对应netowrk document进行下载
| 聚合平台|支持广告样式|接入文档|下载地址|
| -------------|:-------------:|:-------------: |:-------------: |
| TopOn| 插屏 | [TopOn-Network-Document](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/network/Max/RSDK-MAX.md) |[TopOn-Network-Adapter](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/network_max/rad-network-max-release.aar)|
| Max  | 插屏 | [Max-Network-Document](https://github.com/RoulaxTeam/Roulax-Android-SDK/blob/master/network/Max/RSDK-MAX.md) |[Max-Network-Adapter](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/network_max/rad-network-max-release.aar)|


## 更新日志
| 日期 | 版本 | 日志 |
|--|--|--|
| 2022-05-11 | 1.0.11 | Roulax SDK Release |
