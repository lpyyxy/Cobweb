package com.example.cobweb.net.send;

import com.example.cobweb.net.NetData;
import com.example.cobweb.treat.Tools;
import com.example.cobweb.treat.user.Position;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class Send {
    public Send(Position position,byte[]... Byte){
        sendData(position.getDatagramPacket(Tools.byteArrayMerge(Byte)));
    }
    public Send(DatagramPacket position){
        sendData(position);
    }
    public Send(byte[]... Byte){
        new Send(NetData.getMainPosition(),Byte);
    }
    private void sendData(DatagramPacket position){
        try {
            //System.out.println(new StringBuffer("Client send:").append(" ").append(System.currentTimeMillis()).append(" ").append(new Position(position).toString()).append("  ").append(Arrays.toString(position.getData())).toString());
            NetData.getMainSocket().send(position);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}