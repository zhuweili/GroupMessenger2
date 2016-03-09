package edu.buffalo.cse.cse486586.groupmessenger2;

/**
 * Created by user on 2/26/16.
 */
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * Created by user on 2/12/16.
 */
public class ReceiveMessage extends AsyncTask<ServerSocket, String, Void> {
    static final String TAG = GroupMessengerActivity.class.getSimpleName();
    private final TextView mTextView;
    private final ContentResolver mContentResolver;
    private final Uri mUri;
    private int num = 0;
    private static Map<String, Integer> Received_map = new LinkedHashMap<String, Integer>();
    Handler handler;
    new_mes_bucket bucket;



    public ReceiveMessage(TextView _tv, ContentResolver _cr, Handler handler) {

        mTextView = _tv;
        mContentResolver = _cr;
        mUri = Uri.parse("content://edu.buffalo.cse.cse486586.groupmessenger2.provider");
        this.handler=handler;
        bucket=new new_mes_bucket(mContentResolver);

//        mContentResolver = _cr;
//        mUri = buildUri("content", "edu.buffalo.cse.cse486586.groupmessenger1.provider");
//        mContentValues = initTestValues();
    }

    @Override
    protected Void doInBackground(ServerSocket... sockets) {
        ServerSocket serverSocket = sockets[0];


        String Rec_msg = "";
        Socket Rec_in = null;

//        try {
//            Rec_in.setSoTimeout(10000);
//        } catch (SocketException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
//        }
        BufferedReader Bu_read = null;
        boolean isListening = true;
        boolean atlist = false;

        while (isListening ) {

            try {

                        /*
                        http://developer.android.com/reference/java/net/ServerSocket.html
                         */
                Rec_in = serverSocket.accept();


                Bu_read=new BufferedReader(new InputStreamReader(Rec_in.getInputStream(),"utf8"));

                Rec_msg = Bu_read.readLine();
                Bu_read.close();
                Rec_in.close();


                //if (!Received_map.containsKey(Rec_msg)) {

                    bucket.update(Rec_msg);
                    //System.out.println(kk);
                    publishProgress(Rec_msg);
                    Received_map.put(Rec_msg, 1);

                    //Send_msg_list ext=new Send_msg_list(Rec_msg);


//                    Integer[] PORT={11108,11112,11116,11120,11124};
//                    for (int i=0;i<PORT.length;i++) {
//                        try {
//
//
//                            Socket socket = new Socket("10.0.2.2", PORT[i]);
//
//                            String msgToSend =Rec_msg;
//
//
//                            OutputStream Send_out = socket.getOutputStream();
//                            Send_out.write(msgToSend.getBytes("utf-8"));
//                            Send_out.close();
//
//                            socket.close();
//                            System.out.println("message send");
//
//                        } catch (UnknownHostException e) {
//                            Log.e(TAG, "ClientTask UnknownHostException");
//                        } catch (IOException e) {
//                            Log.e(TAG, "ClientTask socket IOException");
//                        }
//                    }




                    //new Thread(new T(Rec_in,handler,mContentResolver)).start();

                //}// end of if


            } catch (IOException e) {

                Log.e(TAG, " connect failed");
            }

        }


        return null;

    }


    protected void onProgressUpdate(String... strings) {
            /*
             * The following code displays what is received in doInBackground().
             */
        String strReceived = strings[0].trim();
        mTextView.append(strReceived + "\n");



            /*
             * The following code creates a file in the AVD's internal storage and stores a file.
             *
             * For more information on file I/O on Android, please take a look at
             * http://developer.android.com/training/basics/data-storage/files.html
             */

//        ContentValues cv = new ContentValues();
//        cv.put("key", Integer.toString(num));
//        cv.put("value", strReceived);
//        mContentResolver.insert(mUri, cv);
//        num++;

        return;
    }






}

