package com.example.loginpage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class BroadCastReceiverClass extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Checking", "OnReceive Called");

        try {
            if (isOnline(context)) {
                MyApplication.getInstance().setNetworkConnected(true);
            } else {
                MyApplication.getInstance().setNetworkConnected(false);
            }
        } catch (NullPointerException e) {
            MyApplication.getInstance().setNetworkConnected(false);
            e.printStackTrace();
        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
