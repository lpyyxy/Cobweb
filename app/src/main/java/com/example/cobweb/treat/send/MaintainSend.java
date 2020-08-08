package com.example.cobweb.treat.send;

import com.example.cobweb.net.UserData;
import com.example.cobweb.net.NetData;
import com.example.cobweb.net.send.Send;
import com.example.cobweb.treat.Tools;
import com.example.cobweb.treat.user.Position;

public class MaintainSend extends Thread{
    public void run(){
        for(;;) {
            new Send(NetData.getMainPosition(), Tools.byteArrayMerge(new byte[]{-1}, UserData.temporaryID));
            for(Position position:NetData.getLinksSet()){
                new Send(position, Tools.byteArrayMerge(new byte[]{-1}));
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
