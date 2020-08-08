package com.example.cobweb.treat;

import com.example.cobweb.R;
import com.example.cobweb.net.NetData;
import com.example.cobweb.net.UserData;
import com.example.cobweb.net.send.Send;
import com.example.cobweb.treat.user.Position;
import com.example.cobweb.treat.user.TOneLink;
import com.example.cobweb.ui.connection.ConnectionFragment;
import com.google.android.material.snackbar.Snackbar;

public class NetTreat {
    public static void status(){
        byte[] tID= UserData.temporaryID;
        NetData.setIsTypeChange(false);
        NetData.setIsTypeIntermediate(true);
        new Send(new byte[]{-2},tID,new byte[]{0});
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!NetData.isTypeChange()){
            new Send(NetData.getAidAcceptPosition(),new byte[]{-2},tID);
            NetData.setIsTypeIntermediate(false);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!NetData.isTypeChange()){
                if(NetData.isTypeIntermediate()){
                    new Send(new byte[]{-2},tID,new byte[]{1,1});
                    UserData.positionType=1;
                }
                new Send(new byte[]{-2},tID,new byte[]{1,2});
                UserData.positionType=2;
            }
        }else{
            new Send(new byte[]{-2},tID,new byte[]{1,0});
            UserData.positionType=0;
        }
    }
    public static void connectionTOneLink(TOneLink link, Position position, int sleep){
        NetData.saveTemporaryPosition(position);
        new Thread(()->{
            for(int j=0;j<sleep/100;j++){
                new Send(position,new byte[]{1,-2});
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(link.isClose()){
            NetData.removeTemporaryPosition(position);
            return;
        }
        if(!NetData.removeTemporaryPosition(position)){
            NetData.saveLink(position,link.getIndex());
            link.setPosition(position);
            link.updataStatusDisplay(R.string.message_link_success, R.drawable.ic_link_success);
            if (!link.isClose()) {
                new ConnectionFragment().confirm(R.string.message_link_success, Snackbar.LENGTH_LONG);
                ConnectionFragment.LinksRefresh();
            }
            return;
        }
        link.updataStatusDisplay(R.string.message_link_timeout,R.drawable.ic_link_failure);
        if (!link.isClose()) {
            new ConnectionFragment().alert(R.string.message_link_failure, Snackbar.LENGTH_LONG);
            ConnectionFragment.LinksRefresh();
        }
    }
}
