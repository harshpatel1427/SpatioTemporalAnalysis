package sbu.wireless.project.analysisofcellularconnectivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import sbu.wireless.project.analysisofcellularconnectivity.receivers.MyConnectivityReceiver;
import sbu.wireless.project.analysisofcellularconnectivity.services.ConnectivityService;


public class MainActivity extends Activity {
    Context context;
    public static TextView txtDeviceID;
    public static TextView txtNetworkOperator;
    public static TextView txtNetworkType;
    public static TextView txtNetworkStrength;
    public static float signalStrength;
    public static TextView txtCell;
    public static TextView txtMnc;
    public static TextView txtMcc;
    public static TextView txtCountry;
    public static TextView txtPhoneType;
    public static TextView txtLatitude;
    public static TextView txtLongitude;
    private View mChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onResume() {
        super.onResume();

        //new SFTPConnectionTask(this).execute();

        startServiceBasedOnNetworkConnentivity();

        txtDeviceID = (TextView) findViewById(R.id.deviceID);
        txtNetworkOperator = (TextView) findViewById(R.id.networkOperator);
        txtNetworkType = (TextView) findViewById(R.id.networkType);
        txtNetworkStrength = (TextView) findViewById(R.id.networkStrength);
        txtCell = (TextView) findViewById(R.id.cell);
        txtMnc = (TextView) findViewById(R.id.mnc);
        txtMcc = (TextView) findViewById(R.id.mcc);
        txtCountry = (TextView) findViewById(R.id.country);
        txtPhoneType = (TextView) findViewById(R.id.phoneType);
        txtLatitude = (TextView) findViewById(R.id.latitude);
        txtLongitude = (TextView) findViewById(R.id.longitude);
    }


    public void startServiceBasedOnNetworkConnentivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        boolean isWiFi = false;
        if (activeNetwork != null)
            isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;

        Intent serviceIntent = new Intent(context, ConnectivityService.class);
        if (!isConnected) {
            if (MyConnectivityReceiver.isServiceStarted) {
                try {
                    context.stopService(serviceIntent);
                    MyConnectivityReceiver.isServiceStarted = false;
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        } else if (isWiFi) {
            if (MyConnectivityReceiver.isServiceStarted) {
                context.stopService(serviceIntent);
                MyConnectivityReceiver.isServiceStarted = false;
            }
        } else {
            serviceIntent.putExtra("KEY1", "Value to be used by the service");
            if (!MyConnectivityReceiver.isServiceStarted) {
                context.startService(serviceIntent);
                MyConnectivityReceiver.isServiceStarted = true;
            }
        }
    }


    public static void clearData() {
        if (txtDeviceID != null) {
            txtDeviceID.setText("Not Connected");
            txtNetworkOperator.setText("Not Connected");
            txtNetworkType.setText("Not Connected");
            txtNetworkStrength.setText("Not Connected");
            txtCell.setText("Not Connected");
            txtMnc.setText("Not Connected");
            txtMcc.setText("Not Connected");
            txtCountry.setText("Not Connected");
            txtPhoneType.setText("Not Connected");
            txtLatitude.setText("GPS Not Connected");
            txtLongitude.setText("GPS Not Connected");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_chart) {
            Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
            startActivity(intent);
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


