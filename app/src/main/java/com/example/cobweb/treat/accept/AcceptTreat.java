package com.example.cobweb.treat.accept;

import com.example.cobweb.R;
import com.example.cobweb.net.UserData;
import com.example.cobweb.net.NetData;
import com.example.cobweb.net.send.Send;
import com.example.cobweb.treat.LinkList;
import com.example.cobweb.treat.NetTreat;
import com.example.cobweb.treat.Tools;
import com.example.cobweb.treat.send.MaintainSend;
import com.example.cobweb.treat.send.SendTreat;
import com.example.cobweb.treat.user.Position;
import com.example.cobweb.treat.user.TOneLink;
import com.example.cobweb.ui.connection.ConnectionFragment;

import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class AcceptTreat extends Thread{

    DatagramPacket packet=null;
    byte[] data=null;
    public AcceptTreat(DatagramPacket packet,byte[] data){
        this.packet=packet;
        this.data=data;
    }
    public void run(){
        //System.out.println(new StringBuffer("Client accept:").append(" ").append(System.currentTimeMillis()).append(" ").append(new Position(packet).toString()).append("  ").append(Arrays.toString(Tools.byteArrayCutLength(16, data))).toString());
        switch(data[0]){
            case 1:
                aloneConnection();
                break;
            case 0:
                enter();
                break;
            case -1:
                maintain();
                break;
            case -2:
                status();
                break;
            case -3:
                refreshType();
                break;
        }

    }
    private void aloneConnection(){
        Position position= null;
        try {
            position = new Position(Tools.byteArrayCutSubscript(2,data));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if(data[1]==1){
            if(UserData.checkCode[0]==data[12]&&UserData.checkCode[1]==data[13]){
                new Send(new byte[]{1},UserData.temporaryID,new byte[]{-2},Tools.byteArrayCut(8,4,data));
                TOneLink link=new TOneLink(null,Tools.byteArrayToTID(Tools.byteArrayCut(8,4,data)), R.string.text_link_tuser);
                new LinkList().saveArrayList(link);
                ConnectionFragment.LinksRefresh();
                NetTreat.connectionTOneLink(link,position,2000);
            }
        }if(data[1]==2){
            NetData.saveTLink(Tools.byteArrayToInt(Tools.byteArrayCutSubscript(8,data)),position);
        }if(data[1]==-2){
            NetData.removeTemporaryPosition(new Position(packet));
        }
    }
    private void enter(){
        UserData.temporaryID=Tools.byteArrayCut(1,4,data);
        UserData.checkCode =Tools.shortToByteArray((short)Tools.random(65535));
        NetTreat.status();
        new MaintainSend().start();
    }
    private void maintain(){
        if(!new Position(packet).equals(NetData.getMainPosition())){
            NetData.saveMaintainTemporaryPosition(new Position(packet));
        }
    }
    private void status(){
        if(new Position(packet).equals(NetData.getAidSendPosition())){
            if(NetData.isTypeIntermediate()) {
                NetData.setIsTypeChange(true);
                UserData.positionType = 0;
            }else{
                NetData.setIsTypeIntermediate(true);
            }
        }if(new Position(packet).equals(NetData.getMainPosition())){
            NetData.setIsTypeChange(true);
            UserData.positionType=data[1];
        }
    }
    private void refreshType(){
        NetTreat.status();
    }
}
