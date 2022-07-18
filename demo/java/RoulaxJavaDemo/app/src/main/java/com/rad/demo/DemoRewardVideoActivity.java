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
import com.rad.out.reward.RXRewardVideoAd;
import com.rad.out.reward.RXRewardVideoEventListener;

public class DemoRewardVideoActivity extends AppCompatActivity implements RXRewardVideoEventListener {

   private final String unitId = "154";
   private final double bidFloor = 0.0;
   private RXRewardVideoAd rewardVideoAd;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_reward_video);
      Button loadButton = findViewById(R.id.btn_load);
      loadButton.setText("Load RewardVideo");
      loadButton.setOnClickListener((View view) -> loadRewardVideo());
      Button showButton = findViewById(R.id.btn_show);
      showButton.setText("Show RewardVideo");
      showButton.setOnClickListener((View view) -> showRewardVideo());
   }

   private void loadRewardVideo() {
      RXSDK.INSTANCE.createRXSdkAd().loadRewardVideo(unitId, bidFloor, new RXSdkAd.RXRewardVideoAdListener() {
         @Override
         public void success(@NonNull RXAdInfo rxAdInfo, @NonNull RXRewardVideoAd rxRewardVideoAd) {
            LogUtil.log("reward video on load success");
            LogUtil.toast(DemoRewardVideoActivity.this, "onLoadSuccess");
            setRVEventListener(rxRewardVideoAd);
            rewardVideoAd = rxRewardVideoAd;
         }

         @Override
         public void failure(@NonNull RXAdInfo rxAdInfo, @NonNull RXError rxError) {
            LogUtil.log("reward video on load failure " + rxAdInfo + ", error: " + rxError);
            LogUtil.toast(DemoRewardVideoActivity.this, "onLoadFailure, error: " + rxError);
         }
      });
   }

   private void setRVEventListener(RXRewardVideoAd rewardVideoAd) {
      rewardVideoAd.setRewardVideoEventListener(this);
   }

   private void showRewardVideo() {
      if (rewardVideoAd != null) {
         if (rewardVideoAd.isReady()) {
            rewardVideoAd.show();
         } else {
            LogUtil.toast(DemoRewardVideoActivity.this, "ad is not ready, please wait or load again");
         }
      } else {
         LogUtil.toast(DemoRewardVideoActivity.this, "ad is has not been loaded yet, please load first") ;
      }
   }

   @Override
   public void onAdClick(@NonNull RXAdInfo rxAdInfo) {
      LogUtil.log("reward video onAdClick $adInfo");
      LogUtil.toast(DemoRewardVideoActivity.this, "onAdClick");
   }

   @Override
   public void onAdClose(@NonNull RXAdInfo rxAdInfo) {
      LogUtil.log("reward video onAdClose " + rxAdInfo);
      LogUtil.toast(DemoRewardVideoActivity.this, "onAdClose");
   }

   @Override
   public void onAdShow(@NonNull RXAdInfo rxAdInfo) {
      LogUtil.log("reward video onAdShow " + rxAdInfo);
      LogUtil.toast(DemoRewardVideoActivity.this, "onAdShow");
   }

   @Override
   public void onAdShowFail(@NonNull RXAdInfo rxAdInfo, @NonNull RXError rxError) {
      LogUtil.log("reward video onAdShow " + rxAdInfo);
      LogUtil.toast(DemoRewardVideoActivity.this, "onAdShow");
   }

   @Override
   public void onRewarded(@NonNull RXAdInfo rxAdInfo) {
      LogUtil.log("reward video onRewarded " + rxAdInfo);
      LogUtil.toast(DemoRewardVideoActivity.this, "onRewarded");
   }
}
