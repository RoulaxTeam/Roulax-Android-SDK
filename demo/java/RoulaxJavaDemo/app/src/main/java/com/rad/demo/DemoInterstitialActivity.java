package com.rad.demo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rad.RXError;
import com.rad.RXSDK;
import com.rad.out.RXAdInfo;
import com.rad.out.RXSdkAd;
import com.rad.out.interstitial.RXInterstitialAd;
import com.rad.out.interstitial.RXInterstitialEventListener;

public class DemoInterstitialActivity extends AppCompatActivity implements RXInterstitialEventListener {

   private final String unitId = "158";
   private final double bidFloor = 0.0;
   private RXInterstitialAd interAd;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_interstital);
      Button loadButton = findViewById(R.id.btn_inter_load);
      loadButton.setText("Load Inter");
      loadButton.setOnClickListener((View view) -> loadInter());
      Button showButton = findViewById(R.id.btn_inter_show);
      showButton.setText("Show Inter");
      showButton.setOnClickListener((View view) -> showInter());
   }

   private void loadInter() {
      RXSDK.INSTANCE.createRXSdkAd().loadInterstitial(unitId, bidFloor, new RXSdkAd.RXInterstitialAdListener() {
         @Override
         public void success(@NonNull RXAdInfo rxAdInfo, @NonNull RXInterstitialAd rxInterstitialAd) {
            LogUtil.log("inter on load success");
            LogUtil.toast(DemoInterstitialActivity.this, "inter on load success");
            setEventListener(rxInterstitialAd);
            interAd = rxInterstitialAd;
         }

         @Override
         public void failure(@NonNull RXAdInfo rxAdInfo, @NonNull RXError rxError) {
            LogUtil.log("inter on load failure " + rxAdInfo + ", error: " + rxError);
            LogUtil.toast(DemoInterstitialActivity.this, "onLoadFailure, error: " + rxError);
         }
      });
   }

   private void setEventListener(RXInterstitialAd interAd) {
      interAd.setInterstitialEventListener(this);
   }

   private void showInter() {
      if (interAd != null) {
         if (interAd.isReady()) {
            interAd.show();
         } else {
            LogUtil.toast(DemoInterstitialActivity.this, "ad is not ready, please wait or load again");
         }
      } else {
         LogUtil.toast(DemoInterstitialActivity.this, "ad is has not been loaded yet, please load first");
      }
   }

   @Override
   public void onAdClick(@NonNull RXAdInfo rxAdInfo) {
      LogUtil.log("inter onAdClick " + rxAdInfo);
      LogUtil.toast(DemoInterstitialActivity.this, "inter onAdClick " + rxAdInfo);
   }

   @Override
   public void onAdClose(@NonNull RXAdInfo rxAdInfo) {
      LogUtil.log("inter onAdClose " + rxAdInfo);
      LogUtil.toast(DemoInterstitialActivity.this, "inter onAdClose " + rxAdInfo);
   }

   @Override
   public void onAdShow(@NonNull RXAdInfo rxAdInfo) {
      LogUtil.log("inter onAdShow " + rxAdInfo);
      LogUtil.toast(DemoInterstitialActivity.this, "inter onAdShow " + rxAdInfo);
   }

   @Override
   public void onAdShowFail(@NonNull RXAdInfo rxAdInfo, @NonNull RXError rxError) {
      LogUtil.log("inter onAdShowFail " + rxAdInfo + " error: " + rxError);
      LogUtil.toast(DemoInterstitialActivity.this, "inter onAdShowFail " + rxAdInfo + " error: " + rxError);
   }
}
