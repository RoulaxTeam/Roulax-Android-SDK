package com.rad.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rad.RXError;
import com.rad.RXSDK;
import com.rad.out.RXAdInfo;
import com.rad.out.RXSdkAd;
import com.rad.out.splash.RXSplashAd;

import java.util.HashMap;

public class DemoSplashAdActivity extends AppCompatActivity {

   private final String unitId = "144";
   private final int timeout = 5;
   private final double bidFloor = 0.0;
   private static final HashMap<String, RXSplashAd> RXSPLASH_MANAGER = new HashMap();

   public static RXSplashAd getUnitRXSplashAdByUnitId(String unitId) {
      return RXSPLASH_MANAGER.get(unitId);
   }

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_demo_ad_splash);
      findViewById(R.id.btn_load).setOnClickListener((View view) -> load());
      findViewById(R.id.btn_is_ready).setOnClickListener((View view) -> isReady());
      findViewById(R.id.btn_show).setOnClickListener((View view) -> show());
   }

   private void load() {
      RXSDK.INSTANCE.createRXSdkAd().loadSplash(unitId, timeout, bidFloor, new RXSdkAd.RXSplashAdListener() {
                 @Override
                 public void success(@NonNull RXAdInfo rxAdInfo, @NonNull RXSplashAd rxSplashAd) {
                    LogUtil.log( "splash load success");
                    LogUtil.toast(DemoSplashAdActivity.this, "splash load success");
                    RXSPLASH_MANAGER.put(rxAdInfo.getUnitId(), rxSplashAd);
                 }

                 @Override
                 public void failure(@NonNull RXAdInfo rxAdInfo, @NonNull RXError rxError) {
                    LogUtil.log( "splash load failure, error " + rxError);
                    LogUtil.toast(DemoSplashAdActivity.this, "splash load failure, error " + rxError);
                    RXSPLASH_MANAGER.remove(rxAdInfo.getUnitId());
                 }

                 @Override
                 public void timeout(@NonNull RXAdInfo rxAdInfo) {
                    LogUtil.log( "splash load timeout");
                    LogUtil.toast(DemoSplashAdActivity.this, "splash load timeout");
                    RXSPLASH_MANAGER.remove(rxAdInfo.getUnitId());
                 }
              });
   }

   private boolean isReady() {
      RXSplashAd ad = RXSPLASH_MANAGER.get(unitId);
      if (ad != null) {
         if (ad.isReady()) {
            LogUtil.log( "splash isReady true");
            LogUtil.toast(DemoSplashAdActivity.this, "splash isReady true");
         } else {
            LogUtil.log( "splash isReady false");
            LogUtil.toast(DemoSplashAdActivity.this, "splash isReady false");
         }
         return ad.isReady();
      } else {
         LogUtil.log( "splash isReady false");
         LogUtil.toast(DemoSplashAdActivity.this, "splash isReady false");
         return false;
      }
   }

   private void show() {
      RXSplashAd ad = RXSPLASH_MANAGER.get(unitId);
      if (ad != null) {
         if (ad.isReady()) {
            Intent intent = new Intent(this, DemoSplashEventActivity.class);
            intent.putExtra("unit_id", unitId);
            startActivity(intent);
         } else {
            LogUtil.log( "splash isReady false");
            LogUtil.toast(DemoSplashAdActivity.this, "splash isReady false");
         }
      } else {
         LogUtil.log( "splash has not been load yet");
         LogUtil.toast(DemoSplashAdActivity.this, "splash has not been load yet");
      }
   }

}
