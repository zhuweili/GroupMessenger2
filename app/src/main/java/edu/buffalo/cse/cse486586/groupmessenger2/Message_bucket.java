package edu.buffalo.cse.cse486586.groupmessenger2;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.AsyncTask;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by user on 2/29/16.
 */
public class Message_bucket extends AsyncTask<String, Void, Void> {
    static Map<String, message_list> bucket_Map = new LinkedHashMap<String, message_list>();
    static List<String> bucket_list=new ArrayList<String>();
    static Uri mUri=Uri.parse("content://edu.buffalo.cse.cse486586.groupmessenger2.provider");
    public ContentResolver mContentResolver;

    public Message_bucket(ContentResolver _cr){
        mContentResolver=_cr;
    }

    @Override
    protected Void doInBackground(String... msgs) {

        String[] temp=msgs[0].split("--");
        String port_num=temp[0];

        if (!bucket_Map.containsKey(port_num)) {
            bucket_Map.put(port_num, new message_list());
            bucket_list.add(port_num);
            Collections.sort(bucket_list);
        }

        message_list input=bucket_Map.get(port_num);
        input.insert(msgs[0]);
        bucket_Map.put(port_num,input);

        int n=0;

        for (int i=0;i<bucket_list.size();i++){
            String portnum=bucket_list.get(i);
            message_list output_List=bucket_Map.get(portnum);
            List<String> output_message=output_List.getDeliverable();

            for (int j=0;j<output_message.size();j++){
                String[] strReceived=output_message.get(j).split("--");
                //ContentResolver mContentResolver=
                ContentValues cv = new ContentValues();
                cv.put("key", Integer.toString(n));
                cv.put("value", strReceived[2]);
                mContentResolver.insert(mUri, cv);
                n++;
                //System.out.println("inseeeeeeeeeeeeeeeeeeeeeeeeert once!");
                //System.out.println(Integer.toString(n));
                //System.out.println(strReceived);
            }
        }




        return null;
    }





    public class message_list{
        private List<String> deliverable=new ArrayList<String>();
        private Map<Integer, String> message_hashmap = new LinkedHashMap<Integer, String>();
        private Queue<Integer> message_sequence_num = new PriorityQueue<Integer>();
        private int end_num;

        public  message_list(){
            message_sequence_num.add(-1);
            end_num=-1;
        }

        void insert(String message) {
            String[] temp=message.split("--");
            Integer sequence_num=Integer.valueOf(temp[1]);
            if (!message_hashmap.containsKey(sequence_num)){
                message_hashmap.put(sequence_num, message);
                message_sequence_num.add(sequence_num);



                    while(!message_sequence_num.isEmpty() ){
                         if (message_sequence_num.peek()<=end_num){
                             System.out.println(message_sequence_num.peek());
                             System.out.println(end_num);
                             System.out.println("failed 11111");
                             message_sequence_num.poll();
                             continue; }

                         if (message_sequence_num.peek()==end_num+1) {
                             deliverable.add(message_hashmap.get(end_num+1));
                             System.out.println(deliverable.size());
                             message_sequence_num.poll();
                             end_num++;
                             continue;
                    }

                        if (message_sequence_num.peek()>end_num+1)
                               break;
                }

            }
        }

        public List<String> getDeliverable(){
            return deliverable;
        }

    }// end of message_list
}
