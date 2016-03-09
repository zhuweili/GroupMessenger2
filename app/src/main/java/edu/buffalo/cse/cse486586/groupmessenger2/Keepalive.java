package edu.buffalo.cse.cse486586.groupmessenger2;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by user on 2/26/16.
 */
public class Keepalive implements Runnable {
    public String d_name;



    public void run() {



        while (true){
            new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, d_name, "11112");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public  Keepalive(String s) {
        d_name=s;
    }
}