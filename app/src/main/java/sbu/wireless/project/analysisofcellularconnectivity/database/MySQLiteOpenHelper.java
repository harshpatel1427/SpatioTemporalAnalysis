package sbu.wireless.project.analysisofcellularconnectivity.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mondrita on 17-11-2015.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "netTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NET_OPERATOR = "net_operator";
    public static final String COLUMN_NET_TYPE = "net_type";
    public static final String COLUMN_NET_STRENGTH = "net_strength";
    public static final String COLUMN_CELL = "cell";
    public static final String COLUMN_MNC = "mnc";
    public static final String COLUMN_MCC = "mcc";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_PHONE_TYPE = "phone_type";
    public static final String COLUMN_DEVICE_ID = "deviceid";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String DATABASE_NAME = "wirelessnetworkdetails.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NET_OPERATOR
            + " text not null, " + COLUMN_NET_TYPE + " text not null, "
            + COLUMN_NET_STRENGTH + " text not null, " + COLUMN_CELL
            + " text not null, " + COLUMN_MNC + " text not null, "
            + COLUMN_MCC + " text not null, " + COLUMN_COUNTRY
            + " text not null, " + COLUMN_PHONE_TYPE + " text not null, "
            + COLUMN_DEVICE_ID + " text not null, " + COLUMN_LATITUDE + " text not null, "
            + COLUMN_LONGITUDE + " text not null, "
            + COLUMN_TIMESTAMP + " text not null);";

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public void onDelete(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("" + MySQLiteOpenHelper.class.getName() +
                "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
