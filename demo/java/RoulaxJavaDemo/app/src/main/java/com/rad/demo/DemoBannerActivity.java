package com.rad.demo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rad.RXError;
import com.rad.RXSDK;
import com.rad.out.RXAdInfo;
import com.rad.out.RXSdkAd;
import com.rad.out.banner.BannerType;
import com.rad.out.banner.RXBannerAd;
import com.rad.out.banner.RXBannerEventListener;

public class DemoBannerActivity extends AppCompatActivity {

   private final String unitId = "147";
   private final double bidFloor = 0.0;
   private RXBannerAd bannerAd;
   private int bannerType = BannerType.SMALL;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_demo_banner);
      findViewById(R.id.btn_banner_load).setOnClickListener((View view) -> loadBanner());
      findViewById(R.id.btn_banner_render_small).setOnClickListener((View view) -> {
         bannerType = BannerType.SMALL;
         renderBanner();
      });
      findViewById(R.id.btn_banner_render_medium).setOnClickListener((View view) -> {
         bannerType = BannerType.MEDIUM;
         renderBanner();
      });
      findViewById(R.id.btn_banner_render_large).setOnClickListener((View view) -> {
         bannerType = BannerType.LARGE;
         renderBanner();
      });
   }

   private void loadBanner() {
      RXSDK.INSTANCE.createRXSdkAd().loadBanner(this, unitId, bidFloor, new RXSdkAd.RXBannerAdListener() {
         @Override
         public void success(@NonNull RXAdInfo rxAdInfo, @NonNull RXBannerAd rxBannerAd) {
            LogUtil.toast(DemoBannerActivity.this, "banner on load success");
            LogUtil.log("banner on load success");
            rxBannerAd.setRXBannerListener(new RXBannerEventListener() {
               @Override
               public void onAdClick(@NonNull RXAdInfo rxAdInfo) {
                  LogUtil.log("banner on ad click");
                  LogUtil.toast(DemoBannerActivity.this, "banner on click");
               }

               @Override
               public void onAdClose(@NonNull RXAdInfo rxAdInfo) {
                  LogUtil.log("banner on ad close");
                  LogUtil.toast(DemoBannerActivity.this, "banner on close");
               }

               @Override
               public void onAdShow(@NonNull RXAdInfo rxAdInfo) {
                  LogUtil.log("banner on show");
                  LogUtil.toast(DemoBannerActivity.this, "banner on show");
               }

               @Override
               public void onRenderFail(@NonNull RXAdInfo rxAdInfo, @NonNull RXError rxError) {
                  LogUtil.log("banner on render fail " + rxError);
                  LogUtil.toast(DemoBannerActivity.this, "banner on render fail " + rxError);
               }

               @Override
               public void onRenderSuccess(@NonNull View view) {
                  LogUtil.toast(DemoBannerActivity.this, "banner on load success");
                  LogUtil.log("banner on ad render success");
                  ViewGroup container;
                  switch (bannerType) {
                     case BannerType.SMALL: container = findViewById(R.id.container_banner_small); break;
                     case BannerType.MEDIUM: container = findViewById(R.id.container_banner_medium); break;
                     case BannerType.LARGE: container = findViewById(R.id.container_banner_large); break;
                     default: container = findViewById(R.id.container_banner_small);
                  }
                  container.addView(view);
               }
            });
            bannerAd = rxBannerAd;
         }

         @Override
         public void failure(@NonNull RXAdInfo rxAdInfo, @NonNull RXError rxError) {
            LogUtil.toast(DemoBannerActivity.this, "banner on load fail " + rxError);
            LogUtil.log("banner on load fail: " + rxError);
         }
      });
   }

   private void renderBanner() {
      if (bannerAd == null) {
         LogUtil.toast(DemoBannerActivity.this, "banner has not been load yet, please load first!");
      }else {
         bannerAd.render(bannerType);
      }
   }
   
}
