package sbu.wireless.project.analysisofcellularconnectivity.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import sbu.wireless.project.analysisofcellularconnectivity.R;

public class SFTPConnectionTask extends AsyncTask<Void, Void, Void> {


    private static String TAG = SFTPConnectionTask.class.getSimpleName();
    private final Context mContext;

    SFTPConnectionTask(Context context) {
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        boolean isConnected = false;
        Session session;
        Channel channel;
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");

        try {
            JSch ssh = new JSch();
            String privateKey = getKey();
            //ssh.addIdentity(privateKey);
            session = ssh.getSession("ubuntu", "52.91.104.106", 10022);
            //session.setConfig(config);
            session.connect();
            isConnected = session.isConnected();
            Log.i(TAG, "Session Connection " + isConnected);
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            sftp.put("test.txt", "/");
        } catch (JSchException e) {
            Log.i(TAG, "Session" + isConnected);
            e.printStackTrace();
        } catch (SftpException e) {
            Log.i(TAG, "Session" + isConnected);
            e.printStackTrace();
        }
        return null;
    }

    private String getKey() {
        StringBuilder mKeyString = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(mContext.getResources().openRawResource(R.raw.amazon_ec2)));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                mKeyString.append(mLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mKeyString.toString();
    }
}