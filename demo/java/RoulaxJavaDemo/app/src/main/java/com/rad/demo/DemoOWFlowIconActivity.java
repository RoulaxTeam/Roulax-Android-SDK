package com.rad.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rad.RXError;
import com.rad.RXSDK;
import com.rad.out.RXAdInfo;
import com.rad.out.RXSdkAd;
import com.rad.out.flowicon.FlowConfig;
import com.rad.out.flowicon.RXFlowIconAd;
import com.rad.out.flowicon.RXFlowIconEventListener;
import com.rad.out.ow.flowicon.OWFlowConfig;
import com.rad.out.ow.flowicon.RXOWFlowIconAd;
import com.rad.out.ow.flowicon.RXOWFlowIconEventListener;

public class DemoOWFlowIconActivity extends AppCompatActivity {

   private final String unitId = "437";
   private RXOWFlowIconAd flowIconAd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_ad_flowicon);
        Button loadButton = findViewById(R.id.btn_load);
        loadButton.setOnClickListener((View view) -> {
            RXSDK.INSTANCE.createRXSdkAd()
                    .loadOWFlowIcon(unitId, new RXSdkAd.RXOWFlowIconAdListener() {
                        @Override
                        public void success(@NonNull RXAdInfo rxAdInfo, @NonNull RXOWFlowIconAd rxFlowIconAd) {
                            LogUtil.log("flowIcon load success");
                            LogUtil.toast(DemoOWFlowIconActivity.this, "flowIcon load success");
                            flowIconAd = rxFlowIconAd;
                            flowIconAd.setOWFlowConfig(
                                    new OWFlowConfig.Builder()
                                            .setImmersionStatusBar(false)
                                            .setOWFlowEventListener( new RXOWFlowIconEventListener() {

                                                @Override
                                                public void onShowFailure(@NonNull RXAdInfo rxAdInfo, @NonNull RXError rxError) {
                                                    LogUtil.log("flowIcon onShowFailure, error: " + rxError);
                                                    LogUtil.toast(DemoOWFlowIconActivity.this, "flowIcon onShowFailure, error: " + rxError);
                                                }

                                                @Override
                                                public void onShow(@NonNull RXAdInfo rxAdInfo) {
                                                    LogUtil.log("flowIcon onShow");
                                                    LogUtil.toast(DemoOWFlowIconActivity.this, "flowIcon onShow");
                                                }

                                                @Override
                                                public void onHide(@NonNull RXAdInfo rxAdInfo) {
                                                    LogUtil.log("flowIcon onHide");
                                                    LogUtil.toast(DemoOWFlowIconActivity.this, "flowIcon onHide");
                                                }

                                                @Override
                                                public void onDismiss(@NonNull RXAdInfo rxAdInfo) {
                                                    LogUtil.log("flowIcon onDismiss");
                                                    LogUtil.toast(DemoOWFlowIconActivity.this, "flowIcon onDismiss");
                                                }

                                                @Override
                                                public void onCreated(@NonNull RXAdInfo rxAdInfo) {
                                                    LogUtil.log("flowIcon onCreated");
                                                    LogUtil.toast(DemoOWFlowIconActivity.this, "flowIcon onCreated");
                                                }

                                                @Override
                                                public void onCreateError(@NonNull RXAdInfo rxAdInfo, @NonNull RXError rxError) {
                                                    LogUtil.log("flowIcon onCreateError, error: " + rxError);
                                                    LogUtil.toast(DemoOWFlowIconActivity.this, "flowIcon onCreateError, error: " + rxError);
                                                }

                                                @Override
                                                public void onClick(@NonNull RXAdInfo rxAdInfo) {
                                                    LogUtil.log("flowIcon onClick");
                                                    LogUtil.toast(DemoOWFlowIconActivity.this, "flowIcon onClick");
                                                }
                                            })
                                            .build());
                            flowIconAd.show(DemoOWFlowIconActivity.this);
                        }

                        @Override
                        public void failure(@NonNull RXAdInfo rxAdInfo, @NonNull RXError rxError) {
                            LogUtil.log("flowIcon load fail: error " + rxError);
                            LogUtil.toast(DemoOWFlowIconActivity.this, "flowIcon load fail: error " + rxError);
                        }
                    });
        });
        Button hideButton = findViewById(R.id.btn_hide);
        hideButton.setOnClickListener((View view) -> {
            if (flowIconAd != null) {
                flowIconAd.hide();
            }
        });
        Button showButton = findViewById(R.id.btn_show);
        showButton.setOnClickListener((View view) -> {
            if (flowIconAd != null) {
                flowIconAd.show(DemoOWFlowIconActivity.this);
            }
        });
        Button dismissButton = findViewById(R.id.btn_dismiss);
        dismissButton.setOnClickListener((View view) -> {
            if (flowIconAd != null) {
                flowIconAd.dismiss();
            }
        });
    }
}
