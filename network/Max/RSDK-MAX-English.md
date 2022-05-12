# Integrate Roulax by Max custom Networks

[Chinese Document](RSDK-MAX-Chinese.md)

### Support ads

1. Interstitial

### Support platforms

1. Android

### AppLovin version

11.3.3

## Add Custom Network configurations in Max Dashboard

### 1. Add Network configurations

In the MAX Dashboard, select [MAX > Mediation > Manage > Networks](https://dash.applovin.com/o/mediation/networks/). Then click **"Click here to add a Custom Network"** at the bottom of the page. The Create Custom Network page appears. Add the information about your custom network:

![avatar](pic1.png)

- **Network Type**：Select `SDK`.
- **Name**: Input `Roulax`
- **Android Adapter Class Name**:
- Input `com.rad.max.adapter.RoulaxMaxMediationAdapter`

### 2. Enable the Custom SDK Network

Open [MAX > Mediation > Manage > Ad Units](https://dash.applovin.com/o/mediation/ad_units/) in the MAX dashboard and select an ad unit for which you want to add the custom SDK network that you created in the previous step.

![avatar](pic2.png)

- **App ID**：App Id of Roulax
- **Placement ID**： Unit ID of Roulax
- **Custom Pameters**：Empty
- **CPM Price**：CPM price of Roulax

## Integrate in Android

### 1. Integrate MAX in Android

Reference: [MAX Integration](https://dash.applovin.com/documentation/mediation/android/getting-started/integration)

### 2. Add dependency libraries

Put the aar file of Roulax SDK to the `libs` folder of your project, then add the following to your `build.gradle` file.

```
repositories {  
     // ... other repositories
       flatDir {
	   dirs  'libs'
	}
}
```

```
dependencies {  
    // ... other project dependencies
    implementation (name: "rad-adapter-max", ext: "aar")
}
```
