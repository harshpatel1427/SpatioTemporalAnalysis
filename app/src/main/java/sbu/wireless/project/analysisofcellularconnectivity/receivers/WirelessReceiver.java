package sbu.wireless.project.analysisofcellularconnectivity.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sbu.wireless.project.analysisofcellularconnectivity.database.DbHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import sbu.wireless.project.analysisofcellularconnectivity.database.MySQLiteOpenHelper;

/**
 * Created by Mondrita on 13-12-2015.
 */
public class WirelessReceiver extends BroadcastReceiver {

    DbHandler dbHandler;
    ArrayList<String> networkTypeData;
    static int counter = 1;

    public WirelessReceiver(DbHandler dbHandler) {
        this.dbHandler = dbHandler;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("my broadcast receiver");
        //backup();
        getValueToStore();
    }

    public void getValueToStore() {
        StringBuilder data = dbHandler.getData();
        System.getProperty("line.separator");
        System.out.println("data is---------------------\\n" + data.toString());
        generateNoteOnSD(data.toString());
    }


    public void generateNoteOnSD(String sBody) {
        String sFileName = "dbfile";
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "WirelessDatabase");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            System.out.println("saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backup() {

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//sbu.wireless.project.analysisofcellularconnectivity//" +
                        "//" + MySQLiteOpenHelper.DATABASE_NAME;
                String backupDBPath = "DATABASE" + counter;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
            counter++;
            System.out.println("done");
        } catch (Exception e) {
        }
    }
}
