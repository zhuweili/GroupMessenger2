package edu.buffalo.cse.cse486586.groupmessenger2;

import android.os.AsyncTask;

/**
 * Created by user on 3/1/16.
 */
public class Send_msg_list {
    public Send_msg_list(String msg){
        new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, msg, "11112");
    }
}
