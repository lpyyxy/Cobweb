package com.example.cobweb.net;


import com.example.cobweb.Data;
import com.example.cobweb.net.accept.Accept;
import com.example.cobweb.treat.send.SendTreat;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Net extends Thread {
    public void run() {
        try {
            NetData.mainSocket=new DatagramSocket(5205);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        NetData.mainPosition= Data.getMainPosition();
        NetData.aidAcceptPosition=Data.getAidAcceptPosition();
        NetData.aidSendPosition=Data.getAidSendPosition();

        new Accept().start();
        new SendTreat().start();
    }
}
