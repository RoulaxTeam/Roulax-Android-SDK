# Integrate Roulax by TopOn custom Networks

## Language
* ch [中文](chinese.md)
## 更新日志
| RoulaxSDK Version | Adapter Version | Log |
|--|--|--|
| 2.1.01 | [Roulax-Topon-Adapter](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/adapter_topon/rad_adapter_topon_2.1.01_release.aar) | Add support for ad templates |
| 2.1.07 | [Roulax-Topon-Adapter](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/adapter_topon/rad_adapter_topon_2.1.07-release.aar) | Support for head bidding |
| 2.1.08 | [Roulax-Topon-Adapter](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/adapter_topon/rad_adapter_topon_2.1.08-release.aar) | Optimise known issues |
### Support ads
1. Interstitial
2. Banner
3. RewardVideo
4. Splash
5. Native

### Support platforms
1. Android

### TopOn version
v5.6.4 or latest

## Add Network configurations in TopOn Dashboard

### 1. Add Roulax SDK Network
In the TopOn dashboard, select [Network](https://app.toponad.com/m/network), click **"+ Network"**, select **“Custom Network”**

1. Fill in the Network Name, such as "Roulax"
2. Fill adapter class in the Adaper's Class Name > Android > Adapter

  RV **“com.rad.adapter.topon.RoulaxRewardVideoAdapter”**

  Interstitial **“com.rad.adapter.topon.RoulaxInterstitialAdapter”**

  Banner **“com.rad.adapter.topon.RoulaxBannerAdapter”**

  Native **“com.rad.adapter.topon.RoulaxNativeAdapter”**

  Splash **“com.rad.adapter.topon.RoulaxSplashAdapter”**

![](1.png)
### 2.Add the Ad Sources for Roulax Network
You can add the Ad Source of the **Roulax Network** on the **Mediation** pages of the TopOn, need to fill in the **Parameters**. 

The Parameters of the Ad Source must be the json format below:
<pre>
{
    "app_id":"your roulax appid",
    "unit_id":"your roulax ad unitid",
    "bid_floor":"unit bidfloor"
}
</pre>
![](2.png)
## Integrate in Android

### 1. Integrate TopOn in Android
Reference [TopOn Integration](https://docs.toponad.com/#/en-us/android/GetStarted/TopOn_Get_Started)

### 2. Download Roulax SDK

### 3. Download [Roulax-Adapter-TopOn](https://github.com/RoulaxTeam/Roulax-Android-SDK/releases/download/adapter_topon/rad_adapter_topon_0.0.3-release.aar)

## Check integrate successful

After integrate is successful，If request Interstitial ads, filter  `RSDK` in logcat will have logs like these:
<pre>
D/RSDK: Roulax Network-TopOn-Interstitial init success
D/RSDK: on SDK-Core init success
</pre>
