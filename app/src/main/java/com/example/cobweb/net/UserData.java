package com.example.cobweb.net;

public class UserData {
    public volatile static String name="未登录";
    public volatile static String ID="";
    public volatile static byte[] temporaryID=new byte[4];
    public volatile static byte[] checkCode=new byte[2];
    public volatile static int positionType=3;
}
