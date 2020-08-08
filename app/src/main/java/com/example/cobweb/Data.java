package com.example.cobweb;

import android.graphics.Bitmap;

import com.example.cobweb.treat.user.Position;

public class Data {
    static Position mainPosition;
    static Position aidSendPosition;
    static Position aidAcceptPosition;
    static int netA;
    static int netB;
    static int netC;
    static int netD;
    public static Position getMainPosition(){
        return mainPosition;
    }
    public static Position getAidSendPosition(){
        return aidSendPosition;
    }
    public static Position getAidAcceptPosition(){
        return aidAcceptPosition;
    }
    public static int getNetA(){
       return netA;
    }
    public static int getNetB(){
        return netB;
    }
    public static int getNetC(){
        return netC;
    }
    public static int getNetD(){
        return netD;
    }
}
