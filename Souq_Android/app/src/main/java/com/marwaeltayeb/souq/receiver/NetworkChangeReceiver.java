package com.marwaeltayeb.souq.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.marwaeltayeb.souq.utils.OnNetworkListener;

import static com.marwaeltayeb.souq.utils.InternetUtils.isNetworkConnected;

public class NetworkChangeReceiver extends BroadcastReceiver {

    OnNetworkListener onNetworkListener;

    public void setOnNetworkListener(OnNetworkListener onNetworkListener) {
        this.onNetworkListener = onNetworkListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isNetworkConnected(context)) {
            onNetworkListener.onNetworkDisconnected();
        } else {
            onNetworkListener.onNetworkConnected();
        }
    }


}
