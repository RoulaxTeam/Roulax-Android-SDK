package com.rad.demo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rad.RXError;
import com.rad.RXSDK;
import com.rad.out.RXAdInfo;
import com.rad.out.RXSdkAd;
import com.rad.out.nativead.RXNativeAd;
import com.rad.out.nativead.RXNativeEventListener;

import java.util.List;

public class DemoNativeActivity extends AppCompatActivity {

   private final String unitId = "145";
   private final double bidFloor = 0.0;
   private final int requestNum = 3;
   private RXNativeAd nativeAd;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_demo_native);
      findViewById(R.id.btn_native_load).setOnClickListener((View view) -> loadNative());
      findViewById(R.id.btn_native_render).setOnClickListener((View view) -> renderNative());
   }

   private void loadNative() {
      RXSDK.INSTANCE.createRXSdkAd().loadNative(this, unitId, requestNum, bidFloor, new RXSdkAd.RXNativeAdListener() {
         @Override
         public void success(@NonNull RXAdInfo rxAdInfo, @NonNull List<? extends RXNativeAd> list) {
            LogUtil.toast(DemoNativeActivity.this, "native on load success");
            LogUtil.log("native on load success");
            if (list.size() > 0) {
               RXNativeAd ad = list.get(0);
               ad.setRXNativeListener(new RXNativeEventListener() {
                  @Override
                  public void onAdClick(@NonNull RXAdInfo rxAdInfo) {
                     LogUtil.toast(DemoNativeActivity.this, "native on click");
                     LogUtil.log("native on ad click");
                  }

                  @Override
                  public void onAdClose(@NonNull RXAdInfo rxAdInfo) {
                     LogUtil.toast(DemoNativeActivity.this, "native on close");
                     LogUtil.log("native on close");
                  }

                  @Override
                  public void onAdShow(@NonNull RXAdInfo rxAdInfo) {
                     LogUtil.toast(DemoNativeActivity.this, "native on show");
                     LogUtil.log("native on show");
                  }

                  @Override
                  public void onRenderFail(@NonNull RXAdInfo rxAdInfo, @NonNull RXError rxError) {
                     LogUtil.toast(DemoNativeActivity.this, "native on render fail " + rxError);
                     LogUtil.log("native on ad render fail " + rxError);
                  }

                  @Override
                  public void onRenderSuccess(@NonNull View view) {
                     LogUtil.toast(DemoNativeActivity.this, "native on render success");
                     LogUtil.log("native on ad render success");
                     ViewGroup container = findViewById(R.id.container_native);
                     container.addView(view);
                  }
               });
               nativeAd = ad;
            }
         }

         @Override
         public void failure(@NonNull RXAdInfo rxAdInfo, @NonNull List<RXError> list) {
            LogUtil.toast(DemoNativeActivity.this, "native on load fail");
            for (RXError error: list) {
               LogUtil.log("native on load fail: " + error);
            }
         }
      });
   }

   private void renderNative() {
      if (nativeAd != null) {
         nativeAd.render();
      } else {
         LogUtil.toast(DemoNativeActivity.this, "native has not been load yet, please load first!");
      }
   }
   
}
