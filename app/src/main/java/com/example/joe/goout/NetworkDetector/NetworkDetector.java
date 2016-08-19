package com.example.joe.goout.NetworkDetector;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by joe on 8/18/16.
 */
public class NetworkDetector {

    Context context;
    public NetworkDetector(Context context){
        this.context = context;
    }

    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Service.CONNECTIVITY_SERVICE);

            if (connectivityManager != null){
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();

                if (info != null){
                    if (info.getState() == NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        return false;
    }
}
