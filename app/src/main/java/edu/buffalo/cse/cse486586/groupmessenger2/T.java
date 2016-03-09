package edu.buffalo.cse.cse486586.groupmessenger2;

import android.content.ContentResolver;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by user on 2/29/16.
 */
public  class T implements Runnable {

    static final String TAG = GroupMessengerActivity.class.getSimpleName();

    public Handler handler;
    public String portname=null;
    private ContentResolver cr;
    boolean alive=true;
    Alive_Node alive_map=new Alive_Node();
    static Map<String, Integer> Received_map = new LinkedHashMap<String, Integer>();

    public void run() {
        System.out.println("A NEW THREAD!!!!!!!!!!!!!!!!!!!!");



        try {
            System.out.println(socket.toString());
            socket.setKeepAlive(true);
            socket.setSoTimeout(500);
            String _pattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat format = new SimpleDateFormat(_pattern);

            String srt2;
            String srt;


            while (alive) {

                try {

                    InputStream ips = socket.getInputStream();
                    //ByteArrayOutputStream bops = new ByteArrayOutputStream();
                    byte[] b = new byte[256];


                    if(ips.read(b)==-1){
                        System.out.println(socket.getPort());
                        System.out.println("dead");
                        Message msg=new Message();
                        msg.obj=portname+" is dead";
                        handler.sendMessage(msg);

//                                if (alive_map.is_alive(portname)){
//                                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//                                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//                                }

                        alive_map.kill(portname);

//                                if (!alive_map.is_alive(portname)){
//                                    System.out.println("_--___-__-_--——-——-————----——_____________________________________-----");
//                                    System.out.println("_--___-__-_--——-——-————----——_____________________________________-----");
//                                    System.out.println("_--___-__-_--——-——-————----——_____________________________________-----");
//                                }

                        socket.close();
                        alive_map.kill(portname);
                        alive=false;
                    }
                    else {
                        srt2=new String(b,"utf-8");
                        srt=srt2.trim();
                        if (!srt.equals("alive") && !srt.equals(portname)) {

                            System.out.println(srt);
                            if (!Received_map.containsKey(srt)) {
                                new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, srt, "11112");
                                Received_map.put(srt, 1);
                                Message msg = new Message();
                                msg.obj = srt;
                                handler.sendMessage(msg);
                            }

                            if (srt.equals("11108") || srt.equals("11112") || srt.equals("11116") || srt.equals("11120") || srt.equals("11124")) {
                                if (portname == null) {
                                    portname = srt;
                                    alive_map.active(portname);

                                }
                            }
                            else{
                                new Message_bucket(cr).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, srt, "11112");
                                if (!Received_map.containsKey(srt)) {
                                    Received_map.put(srt, 1);
                                }
                                //Message_bucket msg_bucket=new Message_bucket(cr);
                            }



                        }


                    }
                    //System.out.println(ips.read(b));


                    //System.out.println(Arrays.toString(bops.toByteArray()));
                }catch(SocketTimeoutException e){
                    //Log.e(TAG, " connect failed " + socket.getPort());
//                        Message msg=new Message();
//                        msg.obj=portname+" is dead";
//                        handler.sendMessage(msg);
//
//                        socket.close();
//                        alive=false;
                    //e.printStackTrace();

                }catch(SocketException e){
                    Log.e(TAG, " connect failed 2222");
                    //onProgressUpdate("dead");
                    e.printStackTrace();
                } catch (Exception e) {
                    Log.e(TAG, " connect failed  3333");
                    //onProgressUpdate("dead");
                    e.printStackTrace();
                }




                //                  Thread.sleep(100);
//                    System.out.println(socket.isBound()); // 是否邦定
//                     System.out.println(socket.isClosed()); // 是否关闭
//                    System.out.println(socket.isConnected()); // 是否连接
//                      System.out.println(socket.getPort());
//                      System.out.println(socket.isInputShutdown()); // 是否关闭输入流
//                    System.out.println(socket.isOutputShutdown()); // 是否关闭输出流
//                    System.out.println("结束：" + format.format(new Date()));

                //socket= new Socket("10.0.2.2", 11112);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Socket socket = null;
    public T(Socket socket, Handler handler, ContentResolver _cr) {
        this.socket = socket;
        this.handler=handler;
        cr=_cr;

    }
    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
