package com.mahza;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import static com.mahza.MainActivity.dialog;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "Otomatik internet Kontrol¸";
    static boolean isConnected = false;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        isNetworkAvailable(context); //receiver çalıştığı zaman çağırılacak method

    }


    @SuppressLint("LongLogTag")
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE); //Sistem ağını dinliyor internet var mı yok mu

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                        if(!isConnected){ //internet varsa
                            isConnected = true;
                            Log.v(LOG_TAG, "internete Bağlandınız!");
                            dialog.cancel();

                            //Toast.makeText(context, "internete Bağlandınız!", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                }
            }
        }
        isConnected = false;
        //Toast.makeText(context, "İnternet Yok", Toast.LENGTH_LONG).show();
        dialog.show();
        Log.v(LOG_TAG, "İnternet Yok!");
        return false;
    }
}
