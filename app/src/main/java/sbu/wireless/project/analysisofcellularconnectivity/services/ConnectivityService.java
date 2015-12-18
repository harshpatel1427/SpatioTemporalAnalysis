package sbu.wireless.project.analysisofcellularconnectivity.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import sbu.wireless.project.analysisofcellularconnectivity.MainActivity;
import sbu.wireless.project.analysisofcellularconnectivity.receivers.WirelessReceiver;
import sbu.wireless.project.analysisofcellularconnectivity.database.DbHandler;
import sbu.wireless.project.analysisofcellularconnectivity.listeners.MyPhoneStateListener;

/**
 * Created by Mondrita on 12-12-2015.
 */
public class ConnectivityService extends Service {

    WirelessReceiver reciever;

    MyPhoneStateListener mylistener;
    TelephonyManager telephonyManager;
    DbHandler dbHandler;


    public ConnectivityService() {
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        registerBroadcastReceiver();
        return Service.START_STICKY;
    }

    public void registerBroadcastReceiver() {

        dbHandler = new DbHandler(this);
        reciever = new WirelessReceiver(dbHandler);

        mylistener = new MyPhoneStateListener(this, dbHandler);
        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(mylistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.TIME_TICK");
        this.registerReceiver(reciever, intentFilter);
    }


    public void unregisterBroadcastReceiver() {

        this.unregisterReceiver(reciever);
        if (telephonyManager != null) {
            telephonyManager.listen(mylistener, PhoneStateListener.LISTEN_NONE);
        }
        dbHandler = null;
        MainActivity.clearData();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        unregisterBroadcastReceiver();
        super.onDestroy();
    }
}