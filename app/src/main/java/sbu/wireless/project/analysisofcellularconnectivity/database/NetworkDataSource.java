package sbu.wireless.project.analysisofcellularconnectivity.database;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import sbu.wireless.project.analysisofcellularconnectivity.model.BO;

/**
 * Created by Mondrita on 17-11-2015.
 */
public class NetworkDataSource {
    // Database fields
    private SQLiteDatabase db;
    private MySQLiteOpenHelper dbHelper;
    private String[] allColumns = {MySQLiteOpenHelper.COLUMN_ID,
            MySQLiteOpenHelper.COLUMN_NET_OPERATOR, MySQLiteOpenHelper.COLUMN_NET_TYPE,
            MySQLiteOpenHelper.COLUMN_NET_STRENGTH, MySQLiteOpenHelper.COLUMN_CELL,
            MySQLiteOpenHelper.COLUMN_MNC, MySQLiteOpenHelper.COLUMN_MCC,
            MySQLiteOpenHelper.COLUMN_COUNTRY, MySQLiteOpenHelper.COLUMN_PHONE_TYPE,
            MySQLiteOpenHelper.COLUMN_DEVICE_ID, MySQLiteOpenHelper.COLUMN_LATITUDE,
            MySQLiteOpenHelper.COLUMN_LONGITUDE, MySQLiteOpenHelper.COLUMN_TIMESTAMP};

    public NetworkDataSource(Context context) {
        dbHelper = new MySQLiteOpenHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void delete() throws SQLException {
        if (db != null) {
            dbHelper.onDelete(db);
        }
    }

    public void createDataRow(BO rowObj) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteOpenHelper.COLUMN_NET_OPERATOR, rowObj.net_operator);
        values.put(MySQLiteOpenHelper.COLUMN_NET_TYPE, rowObj.net_type);
        values.put(MySQLiteOpenHelper.COLUMN_NET_STRENGTH, rowObj.net_strength);
        values.put(MySQLiteOpenHelper.COLUMN_CELL, rowObj.cell);
        values.put(MySQLiteOpenHelper.COLUMN_MNC, rowObj.mnc);
        values.put(MySQLiteOpenHelper.COLUMN_MCC, rowObj.mcc);
        values.put(MySQLiteOpenHelper.COLUMN_COUNTRY, rowObj.country);
        values.put(MySQLiteOpenHelper.COLUMN_PHONE_TYPE, rowObj.phone_type);
        values.put(MySQLiteOpenHelper.COLUMN_DEVICE_ID, rowObj.deviceid);
        values.put(MySQLiteOpenHelper.COLUMN_LATITUDE, rowObj.latitude);
        values.put(MySQLiteOpenHelper.COLUMN_LONGITUDE, rowObj.longitude);
        values.put(MySQLiteOpenHelper.COLUMN_TIMESTAMP, System.currentTimeMillis());

        long insertId = db.insert(MySQLiteOpenHelper.TABLE_NAME, null,
                values);
    }


    public ArrayList<String> getDataRow() {
        SQLiteDatabase dbnew = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + MySQLiteOpenHelper.TABLE_NAME;
        Cursor cursor = dbnew.query(MySQLiteOpenHelper.TABLE_NAME, allColumns, null, null, null, null, null);
        ArrayList<String> data = new ArrayList<>();
        int index = 0;

        try {
            if (cursor == null) {
                System.out.println("no data");
            }
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                data.add(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_NET_TYPE)));
            }

        } catch (Exception e) {
            System.out.println("-------------" + e.getStackTrace().toString());
            e.printStackTrace();
        }
        return data;
    }


    public StringBuilder getAllDataRow() {
        SQLiteDatabase dbnew = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + MySQLiteOpenHelper.TABLE_NAME;
        Cursor cursor = dbnew.query(MySQLiteOpenHelper.TABLE_NAME, allColumns, null, null, null, null, null);
        StringBuilder alldata = new StringBuilder();

        try {
            if (cursor == null) {
                System.out.println("no data");
            }
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                alldata.append(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_NET_OPERATOR)));
                alldata.append("       ");
                alldata.append(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_NET_TYPE)));
                alldata.append("       ");
                alldata.append(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_NET_STRENGTH)));
                alldata.append("       ");
                alldata.append(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_CELL)));
                alldata.append("       ");
                alldata.append(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_MNC)));
                alldata.append("       ");
                alldata.append(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_MCC)));
                alldata.append("       ");
                alldata.append(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_COUNTRY)));
                alldata.append("       ");
                alldata.append(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_PHONE_TYPE)));
                alldata.append("       ");
                alldata.append(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_DEVICE_ID)));
                alldata.append("       ");
                alldata.append(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_LATITUDE)));
                alldata.append("       ");
                alldata.append(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_LONGITUDE)));
                alldata.append("       ");
                alldata.append(cursor.getString(cursor.getColumnIndex(MySQLiteOpenHelper.COLUMN_TIMESTAMP)));
                alldata.append("\\n");
            }

        } catch (Exception e) {
            System.out.println("-------------" + e.getStackTrace().toString());
            e.printStackTrace();
        }
        return alldata;
    }


}

