package edu.buffalo.cse.cse486586.groupmessenger2;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by user on 2/27/16.
 */
public class ClientTask extends AsyncTask<String, Void, Void> {
    static final String TAG = GroupMessengerActivity.class.getSimpleName();
    static Map<Integer, Socket> socketMap = new LinkedHashMap<Integer, Socket>();

    @Override
    protected Void doInBackground(String... msgs) {
//        Integer[] PORT={11108,11112,11116,11120,11124};
//        for (int i=0;i<5;i++) {
//            try {
//
//                String msgToSend = msgs[0];
//
//                 if (socketMap.containsKey(PORT[i])) {
//                     socketMap.get(PORT[i]).setKeepAlive(true);
//                     OutputStream Send_out=socketMap.get(PORT[i]).getOutputStream();
//                     byte[] b = new byte[128];
//                     char[] temp=new char[128];
//                     int len=msgToSend.length();
//
//                     for (int j=1;j<=256-len;j++) {
//                         msgToSend+=" ";
//                     }
//                     b=msgToSend.getBytes("utf-8");
//                     Send_out.write(b);
//                 }
//
//                else{
//                     Socket socket = new Socket("10.0.2.2", PORT[i]);
//                     socketMap.put(PORT[i],socket);
//                     socketMap.get(PORT[i]).setKeepAlive(true);
//                     OutputStream Send_out=socketMap.get(PORT[i]).getOutputStream();
//                     byte[] b = new byte[128];
//                     int len=msgToSend.length();
//
//                     for (int j=1;j<=256-len;j++) {
//                         msgToSend+=" ";
//                     }
//                     b=msgToSend.getBytes("utf-8");
//                     Send_out.write(b);
//                 }
//
//
//
//            } catch (UnknownHostException e) {
//                Log.e(TAG, "ClientTask UnknownHostException");
//            } catch (IOException e) {
//                //Log.e(TAG, "ClientTask socket IOException");
//                socketMap.remove(PORT[i]);
//            }
//        }
//
//        return null;

        Integer[] PORT={11108,11112,11116,11120,11124};
        for (int i=0;i<PORT.length;i++) {
            try {


                Socket socket = new Socket("10.0.2.2", PORT[i]);

                String msgToSend = msgs[0];

//                byte[] b = new byte[128];
//                   char[] temp=new char[128];
//                     int len=msgToSend.length();
//
//                     for (int j=1;j<=256-len;j++) {
//                         msgToSend+=" ";
//                     }
//                     b=msgToSend.getBytes("utf-8");

                OutputStream Send_out = socket.getOutputStream();
                Send_out.write(msgToSend.getBytes("utf-8"));
                Send_out.close();

                socket.close();
                System.out.println("message send");

            } catch (UnknownHostException e) {
                Log.e(TAG, "ClientTask UnknownHostException");
            } catch (IOException e) {
                Log.e(TAG, "ClientTask socket IOException");
            }
        }

        return null;


    }
}
