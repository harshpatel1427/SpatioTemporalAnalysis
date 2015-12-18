package sbu.wireless.project.analysisofcellularconnectivity.database;

import android.content.Context;

import java.util.ArrayList;

import sbu.wireless.project.analysisofcellularconnectivity.model.BO;

/**
 * Created by Mondrita on 13-12-2015.
 */
public class DbHandler {

    NetworkDataSource datasource;

    Context context;

    public DbHandler(Context context) {
        this.context = context;
        datasource = new NetworkDataSource(context);
        datasource.open();
    }

    public void createRow(BO obj) {
        datasource.createDataRow(obj);
    }

    public ArrayList<String> getNetworkTypeData() {

        return datasource.getDataRow();
    }

    public StringBuilder getData() {
        return datasource.getAllDataRow();
    }

}
