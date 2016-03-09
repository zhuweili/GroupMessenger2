package edu.buffalo.cse.cse486586.groupmessenger2;

import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by user on 2/28/16.
 */
public class Alive_Node {
    static Map<String, Boolean> alive_socket_Map = new LinkedHashMap<String, Boolean>();

    public Boolean is_alive(String port_num) {
        if (!alive_socket_Map.containsKey(port_num))
            return false;
        else
            return alive_socket_Map.get(port_num);
    }

    public void active(String port_num) {

            alive_socket_Map.put(port_num, true);

    }

    public void kill(String port_num) {

        alive_socket_Map.put(port_num, false);

    }


}
