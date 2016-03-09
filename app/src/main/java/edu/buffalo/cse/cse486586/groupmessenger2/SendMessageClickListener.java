package edu.buffalo.cse.cse486586.groupmessenger2;

/**
 * Created by user on 2/26/16.
 */
import android.content.ContentResolver;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by user on 2/11/16.
 */
public class SendMessageClickListener implements View.OnClickListener {
    static final String TAG = GroupMessengerActivity.class.getSimpleName();
    private final EditText myeditText;
    private final TextView mTextView;
    static Integer seq_num=0;
    private String myPort;



    public SendMessageClickListener(EditText _et, TextView _tv, String myPort ) {
        myeditText = _et;
        mTextView=_tv;
        this.myPort=myPort;
//        mContentResolver = _cr;
//        mUri = buildUri("content", "edu.buffalo.cse.cse486586.groupmessenger1.provider");
//        mContentValues = initTestValues();
    }

    @Override
    public void onClick(View v) {
        //mTextView.append("click once\n");

        mTextView.append("click once\n");
        String msg = myeditText.getText().toString() + "\n";
        //String seq= seq_num.toString();
        msg=myPort+"--"+seq_num.toString()+"--"+msg;
        myeditText.setText("");
        new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, msg, "11112");

        seq_num++;

    }


    //private class ClientTask
}

