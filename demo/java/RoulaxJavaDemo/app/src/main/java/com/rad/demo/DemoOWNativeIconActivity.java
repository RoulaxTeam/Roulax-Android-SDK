package com.rad.demo;

import android.os.Bundle;
import android.os.Handler;
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
import com.rad.out.ow.nativeicon.RXOWNativeIcon;
import com.rad.out.ow.nativeicon.RXOWNativeIconEventListener;

public class DemoOWNativeIconActivity extends AppCompatActivity {
   
   private final String unitId = "435";
   private ImageView iconImageView;
   private Handler mHandler;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_demo_native_icon);
      iconImageView = findViewById(R.id.container_native_icon);
      Button loadButton = findViewById(R.id.btn_native_icon_load);
      loadButton.setOnClickListener((View view) -> loadNativeIcon());
      mHandler = new Handler(getMainLooper());
   }

   private void loadNativeIcon() {
      RXSDK.INSTANCE.createRXSdkAd().loadOWNativeIcon(unitId, new RXSdkAd.RXOWNativeIconAdListener() {
         @Override
         public void success(@NonNull RXAdInfo rxAdInfo, @NonNull RXOWNativeIcon rxNativeIconAd) {
            LogUtil.toast(DemoOWNativeIconActivity.this, "native icon on load success");
            LogUtil.log("native icon on load success");
            rxNativeIconAd.setRXOWNativeIconListener(new RXOWNativeIconEventListener() {
               @Override
               public void onAdShowSuccess(@NonNull RXAdInfo rxAdInfo) {
                  LogUtil.toast(DemoOWNativeIconActivity.this, "native icon on show success");
                  LogUtil.log("native icon on show success");
               }
            });
            mHandler.post(() -> {
               Glide.with(iconImageView).load(rxNativeIconAd.getIconResource()).into(iconImageView);
               iconImageView.setOnClickListener((View view) -> rxNativeIconAd.click());
            });
         }

         @Override
         public void failure(@NonNull RXAdInfo rxAdInfo, @NonNull RXError rxError) {
            LogUtil.toast(DemoOWNativeIconActivity.this, "native icon on load fail, error " + rxError);
            LogUtil.log("native icon on load fail, error " + rxError);
         }
      });
   }

}
