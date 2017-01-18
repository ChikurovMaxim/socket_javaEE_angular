package com.server.socket.proc;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Maksym on 14.12.2016.
 */
public class SocketCustom {

    private ServerSocket ss;

    private int port;

    public SocketCustom(int port) {
        this.port = port;
    }

    public String start(){
        try {
            ss = new ServerSocket(port);
            Socket socket = ss.accept();

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            String simData = null;
            while (true) {
                simData = in.readUTF();
                return simData;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void stop() {
        try { if (ss != null) ss.close();}
        catch (IOException e) {e.printStackTrace();}
    }
}

