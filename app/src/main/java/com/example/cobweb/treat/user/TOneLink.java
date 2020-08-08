package com.example.cobweb.treat.user;

import android.view.View;

import com.example.cobweb.R;
import com.example.cobweb.net.NetData;
import com.example.cobweb.treat.Tools;

public class TOneLink extends AbstractLink{
    private Position position=null;

    public TOneLink(View root, String TID, int string) {
        super(root, TID, string);
    }


    public void setPosition(Position position){
        this.position=position;
    }
    public Position getPosition(){
        return position;
    }
    public void updata(int index){
        updata(index);
        if(position!=null){
            NetData.updataLink(position,index);
        }
    }
    public void close(){
        NetData.removeLink(position);
    }
}
