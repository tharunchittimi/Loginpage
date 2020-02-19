package com.example.loginpage;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;

public class MyApplication extends Application {

    private static MyApplication myApplication;
    private boolean isNetworkConnected = false;
    private BroadCastReceiverClass  mNetworkReceiver = new BroadCastReceiverClass();

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        registerNetworkBroadcastForNougat();
    }
   public static MyApplication getInstance(){
        return myApplication;
    }

    public void setNetworkConnected(boolean networkConnected) {
        isNetworkConnected = networkConnected;
    }
    public boolean getNetworkConnected(){
        return isNetworkConnected;
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        Log.e("Checking","onTrimMemory Called");
        unregisterNetworkChanges();
        super.onTrimMemory(level);

    }
}
