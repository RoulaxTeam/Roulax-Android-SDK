package com.rad.demo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.rad.RXError;
import com.rad.RXSDK;
import com.rad.out.RXAdInfo;
import com.rad.out.RXSdkAd;
import com.rad.out.nativeicon.RXNativeIconAd;
import com.rad.out.nativeicon.RXNativeIconEventListener;

public class DemoNativeIconActivity extends AppCompatActivity {
   
   private final String unitId = "172";
   private ImageView iconImageView;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_demo_native_icon);
      iconImageView = findViewById(R.id.container_native_icon);
      Button loadButton = findViewById(R.id.btn_native_icon_load);
      loadButton.setOnClickListener((View view) -> loadNativeIcon());
   }

   private void loadNativeIcon() {
      RXSDK.INSTANCE.createRXSdkAd().loadNativeIcon(unitId, new RXSdkAd.RXNativeIconAdListener() {
         @Override
         public void success(@NonNull RXAdInfo rxAdInfo, @NonNull RXNativeIconAd rxNativeIconAd) {
            LogUtil.toast(DemoNativeIconActivity.this, "native icon on load success");
            LogUtil.log("native icon on load success");
            rxNativeIconAd.setRXNativeIconListener(new RXNativeIconEventListener() {
               @Override
               public void onAdShowSuccess(@NonNull RXAdInfo rxAdInfo) {
                  LogUtil.toast(DemoNativeIconActivity.this, "native icon on show success");
                  LogUtil.log("native icon on show success");
               }

               @Override
               public void onRewarded(@NonNull RXAdInfo rxAdInfo) {
                  LogUtil.toast(DemoNativeIconActivity.this, "native icon on reward");
                  LogUtil.log("native icon on reward");
               }

               @Override
               public void onRefresh(@NonNull RXAdInfo rxAdInfo, @NonNull String imgUrl) {
                  LogUtil.toast(DemoNativeIconActivity.this, "native icon on refresh");
                  LogUtil.log("native icon on refresh");

                  Glide.with(iconImageView).load(imgUrl).into(iconImageView);
               }

               @Override
               public void onClosed(@NonNull RXAdInfo rxAdInfo) {
                  LogUtil.toast(DemoNativeIconActivity.this, "native icon on close");
                  LogUtil.log("native icon on close");
               }

               @Override
               public void onAdShowFailure(@NonNull RXAdInfo rxAdInfo, @NonNull RXError rxError) {
                  LogUtil.toast(DemoNativeIconActivity.this, "native icon on show failure, error" + rxError);
                  LogUtil.log("native icon on show failure, error" + rxError);
               }
            });
            Glide.with(iconImageView).load(rxNativeIconAd.getIconResource()).into(iconImageView);
            iconImageView.setOnClickListener((View view) -> rxNativeIconAd.click());
         }

         @Override
         public void failure(@NonNull RXAdInfo rxAdInfo, @NonNull RXError rxError) {
            LogUtil.toast(DemoNativeIconActivity.this, "native icon on load fail, error " + rxError);
            LogUtil.log("native icon on load fail, error " + rxError);
         }
      });
   }

}
