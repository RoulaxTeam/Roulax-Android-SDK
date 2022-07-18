package com.rad.demo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rad.RXError;
import com.rad.out.RXAdInfo;
import com.rad.out.splash.RXSplashAd;
import com.rad.out.splash.RXSplashEventListener;

public class DemoSplashEventActivity extends AppCompatActivity {

   private RXSplashAd splashAd;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_demo_event_splash);
      String unitId = getIntent().getStringExtra("unit_id");
      splashAd = DemoSplashAdActivity.getUnitRXSplashAdByUnitId(unitId);
      if (splashAd != null) {
         splashAd.setEventListener(eventListener);
         View splashView = splashAd.getSplashView(this);
         if (splashView != null) {
            ViewGroup container = findViewById(R.id.splash_container);
            container.addView(splashView);
         } else {
            LogUtil.log("splash view isEmpty");
            LogUtil.toast(DemoSplashEventActivity.this, "splash view isEmpty");
         }
      }
   }

   private final RXSplashEventListener eventListener = new RXSplashEventListener() {

      @Override
      public void onShowSuccess(@NonNull RXAdInfo rxAdInfo) {
         LogUtil.log("splash onShowSuccess");
         LogUtil.toast(DemoSplashEventActivity.this, "splash onShowSuccess");
      }

      @Override
      public void onShowFailure(@NonNull RXAdInfo rxAdInfo, @NonNull RXError rxError) {
         LogUtil.log("splash onShowFailure, error: " + rxError);
         LogUtil.toast(DemoSplashEventActivity.this, "splash onShowFailure, error: " + rxError);
         finish();
      }

      @Override
      public void onDismiss(@NonNull RXAdInfo rxAdInfo) {
         LogUtil.log("splash onDismiss");
         LogUtil.toast(DemoSplashEventActivity.this, "splash onDismiss");
         finish();
      }

      @Override
      public void onClick(@NonNull RXAdInfo rxAdInfo) {
         LogUtil.log("splash onClick");
         LogUtil.toast(DemoSplashEventActivity.this, "splash onClick");
      }

   };

}
