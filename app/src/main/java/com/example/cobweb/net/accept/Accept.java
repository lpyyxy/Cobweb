package com.example.cobweb.net.accept;

import com.example.cobweb.net.NetData;
import com.example.cobweb.treat.accept.AcceptTreat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Accept extends Thread{
    public void run(){
        DatagramSocket socket=new NetData().getMainSocket();
        byte[] receiveData;
        DatagramPacket packet;
        while(true) {
            receiveData=new byte[512];
            packet=new DatagramPacket(receiveData,512);
            try {
                socket.receive(packet);
            } catch (IOException e) {

            }
            new AcceptTreat(packet,receiveData).start();
        }
    }
}
