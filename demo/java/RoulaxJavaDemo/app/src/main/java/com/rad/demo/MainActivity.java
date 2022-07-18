package com.rad.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rad.RXError;
import com.rad.RXSDK;
import com.rad.rcommonlib.utils.RXLogUtil;

public class MainActivity extends AppCompatActivity {

    private final String appId = "60";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_witch).setOnClickListener((View view) -> switchOrientation());
        Button initButton = findViewById(R.id.button_init);
        initButton.setText("SDK初始化-AppID: " + appId);
        initButton.setOnClickListener((View view) -> initRXSDK());
        findViewById(R.id.button_rv).setOnClickListener((View view) -> startRvActivity());
        findViewById(R.id.button_splash).setOnClickListener((View view) -> startSplashActivity());
        findViewById(R.id.button_banner).setOnClickListener((View view) -> startBannerActivity());
        findViewById(R.id.button_flowicon).setOnClickListener((View view) -> startFlowIconActivity());
        findViewById(R.id.button_native).setOnClickListener((View view) -> startNativeActivity());
        findViewById(R.id.button_inter).setOnClickListener((View view) -> startInterActivity());
        findViewById(R.id.button_native_icon).setOnClickListener((View view) ->  startNativeIconActivity());
    }

    private void switchOrientation() {
        int orientation = getRequestedOrientation();
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        } else {
            orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        }
        setRequestedOrientation(orientation);
    }

    private void initRXSDK() {
        RXSDK.INSTANCE.init(appId, new RXSDK.RXSDKInitListener() {
            @Override
            public void onSDKInitSuccess() {
                LogUtil.log("onSDKInitSuccess");
                LogUtil.toast(MainActivity.this, "onSDKInitSuccess");
            }

            @Override
            public void onSDKInitFailure(RXError rxError) {
                LogUtil.log("onSDKInitFailure "+ rxError);
                LogUtil.toast(MainActivity.this, "onSDKInitFailure " + rxError);
            }
        });
    }

    private void startRvActivity() {
        Intent intent = new Intent(this, DemoRewardVideoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startSplashActivity() {
        Intent intent = new Intent(this, DemoSplashAdActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startBannerActivity() {
        Intent intent = new Intent(this, DemoBannerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startNativeActivity() {
        Intent intent = new Intent(this, DemoNativeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startInterActivity() {
        Intent intent = new Intent(this, DemoInterstitialActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startNativeIconActivity() {
        Intent intent = new Intent(this, DemoNativeIconActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startFlowIconActivity() {
        Intent intent = new Intent(this, DemoFlowIconActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}