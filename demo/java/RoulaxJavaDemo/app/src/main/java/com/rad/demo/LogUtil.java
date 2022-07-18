package com.rad.demo;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

class LogUtil {

   private final static String TAG = "RoulaxDemo";

   public static void log(String msg) {
      Log.i(TAG, msg);
   }

   public static void toast(Activity activity, String msg) {
      activity.runOnUiThread(() -> Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show());
   }

}
