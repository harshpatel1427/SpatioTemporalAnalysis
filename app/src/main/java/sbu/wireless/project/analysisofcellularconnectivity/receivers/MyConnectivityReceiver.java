package sbu.wireless.project.analysisofcellularconnectivity.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import sbu.wireless.project.analysisofcellularconnectivity.services.ConnectivityService;

/**
 * Created by Mondrita on 12-12-2015.
 */
public class MyConnectivityReceiver extends BroadcastReceiver {

    public static boolean isServiceStarted = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        boolean isWiFi = false;
        if (activeNetwork != null)
            isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;

        Intent serviceIntent = new Intent(context, ConnectivityService.class);
        if (!isConnected) {
            //System.out.println("network not there");
            if (isServiceStarted) {
                try {
                    context.stopService(serviceIntent);
                    isServiceStarted = false;
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        } else if (isWiFi) {
            //System.out.println("connected to wifi");
            if (isServiceStarted) {
                context.stopService(serviceIntent);
                isServiceStarted = false;
            }
        } else {
            //System.out.println("active network");
            serviceIntent.putExtra("Context", "Value to be used by the service");
            if (!isServiceStarted) {
                context.startService(serviceIntent);
                isServiceStarted = true;
            }
        }
    }

}
