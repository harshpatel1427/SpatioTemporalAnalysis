package sbu.wireless.project.analysisofcellularconnectivity.listeners;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

import java.util.ArrayList;

import sbu.wireless.project.analysisofcellularconnectivity.MainActivity;
import sbu.wireless.project.analysisofcellularconnectivity.database.DbHandler;
import sbu.wireless.project.analysisofcellularconnectivity.model.BO;
import sbu.wireless.project.analysisofcellularconnectivity.util.Utilities;

/**
 * Created by Mondrita on 12-12-2015.
 */
public class MyPhoneStateListener extends PhoneStateListener {
    int signalStrengthValue;
    Context context;
    DbHandler dbHandler;
    BO obj;

    public MyPhoneStateListener(Context context, DbHandler handler) {
        this.context = context;
        this.dbHandler = handler;

    }

    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        obj = new BO();
        String backup_cell = "";

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);


        String networkOperatorName = telephonyManager.getNetworkOperatorName();
        obj.net_operator = networkOperatorName;

        obj.net_type = Utilities.getNetworkType(telephonyManager.getNetworkType());


        if (signalStrength.isGsm()) {
            if (signalStrength.getGsmSignalStrength() != 99) {
                signalStrengthValue = signalStrength.getGsmSignalStrength() * 2 - 113;
            } else {
                signalStrengthValue = signalStrength.getGsmSignalStrength();
            }
        } else {
            signalStrengthValue = signalStrength.getCdmaDbm();
        }
        obj.net_strength = signalStrengthValue + " dbm";

        if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
            GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
            obj.cell = "CID: " + cellLocation.getCid() + "\nLAC: " + cellLocation.getLac();
            backup_cell = "CID: " + cellLocation.getCid() + "  LAC: " + cellLocation.getLac();
        } else if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
            CdmaCellLocation cellLocation = (CdmaCellLocation) telephonyManager.getCellLocation();
            obj.cell = "BID: " + cellLocation.getBaseStationId() + "\nSID: " + cellLocation.getSystemId() + "\nNID: " + cellLocation.getNetworkId();
            backup_cell = "BID: " + cellLocation.getBaseStationId() + "  SID: " + cellLocation.getSystemId() + "  NID: " + cellLocation.getNetworkId();
        } else {

        }

        String networkOperator = telephonyManager.getNetworkOperator();
        obj.mcc = networkOperator.substring(0, 3);
        obj.mnc = networkOperator.substring(3);


        obj.country = telephonyManager.getNetworkCountryIso().toUpperCase();

        obj.phone_type = setPhoneType(telephonyManager);

        String device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        obj.deviceid = telephonyManager.getDeviceId();
        getLocation(obj);

        if (MainActivity.txtNetworkOperator != null) {

            MainActivity.txtNetworkOperator.setText(obj.net_operator);
            MainActivity.txtNetworkType.setText(obj.net_type);
            MainActivity.txtNetworkStrength.setText(obj.net_strength);
            MainActivity.txtCell.setText(obj.cell);
            MainActivity.txtMcc.setText(obj.mcc);
            MainActivity.txtMnc.setText(obj.mnc);
            MainActivity.txtCountry.setText(obj.country);
            MainActivity.txtPhoneType.setText(obj.phone_type);
            MainActivity.txtDeviceID.setText(obj.deviceid);
            MainActivity.txtLatitude.setText(obj.latitude);
            MainActivity.txtLongitude.setText(obj.longitude);
        }

        obj.cell = backup_cell;
        storeBO(obj);

    }


    String setPhoneType(TelephonyManager telephonyManager) {
        int phoneType = telephonyManager.getPhoneType();
        String type = "";
        switch (phoneType) {
            case TelephonyManager.PHONE_TYPE_NONE:
                type = "NONE";
                break;
            case TelephonyManager.PHONE_TYPE_GSM:
                type = "GSM";
                break;
            case TelephonyManager.PHONE_TYPE_CDMA:
                type = "CDMA";
                break;
            default:
                type = "";
        }
        return type;

    }


    void getLocation(BO obj) {
        LocationManager mlocManager = null;
        LocationListener mlocListener;
        mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener();
        obj.latitude = "GPS Not Connected";
        obj.longitude = "GPS Not Connected";
        try {
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);

            if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (MyLocationListener.latitude > 0) {
                    obj.latitude = "" + MyLocationListener.latitude;
                    obj.longitude = "" + MyLocationListener.longitude;
                }
            } else {
                obj.latitude = "GPS Not Connected";
                obj.longitude = "GPS Not Connected";
            }
        } catch (Exception ex) {
            System.out.println("exception");
        }
    }


    public void storeBO(BO obj) {
        dbHandler.createRow(obj);
        ArrayList<String> networkTypeData = dbHandler.getNetworkTypeData();
    }

}