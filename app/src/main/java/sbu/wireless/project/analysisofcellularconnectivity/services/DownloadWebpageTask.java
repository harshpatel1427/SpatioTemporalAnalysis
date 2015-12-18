package sbu.wireless.project.analysisofcellularconnectivity.services;

import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by Mondrita on 12-12-2015.
 */
class DownloadWebpageTask extends AsyncTask<String, Void, String> {

    String downloadUrl="ftpec2-52-91-104-106.compute-1.amazonaws.com";

    @Override
    protected String doInBackground(String... urls) {

        try {
            return downloadUrl;
        } catch (Exception e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }


    @Override
    protected void onPostExecute(String result) {
        System.out.println("post done");
        System.out.println("post done");
    }
}
