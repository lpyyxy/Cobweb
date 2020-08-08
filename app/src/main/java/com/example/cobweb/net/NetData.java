package com.example.cobweb.net;

import com.example.cobweb.treat.user.Position;

import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class NetData {
    static DatagramSocket mainSocket;

    static Position mainPosition;

    static Position aidSendPosition;

    static Position aidAcceptPosition;

    private volatile static boolean isTypeChange=false;

    private volatile static boolean isTypeIntermediate=true;

    private static HashMap<Position,Integer> link=new HashMap<Position,Integer>();

    private static HashMap<Integer,Position> tLink=new HashMap<Integer,Position>();

    private static HashSet<Position> maintainTemporaryPosition=new HashSet<Position>();

    private static HashSet<Position> temporaryPosition =new HashSet<Position>();

    public static Position getMainPosition() {
        return mainPosition;
    }

    public static Position getAidSendPosition() {
        return aidSendPosition;
    }

    public static Position getAidAcceptPosition() {
        return aidAcceptPosition;
    }

    public static DatagramSocket getMainSocket() {
        return mainSocket;
    }

    public static boolean isTypeChange() {
        return isTypeChange;
    }

    public static void setIsTypeChange(boolean isTypeChange) {
        NetData.isTypeChange = isTypeChange;
    }

    public static boolean isTypeIntermediate() {
        return isTypeIntermediate;
    }

    public static void setIsTypeIntermediate(boolean isTypeIntermediate) {
        NetData.isTypeIntermediate = isTypeIntermediate;
    }

    public static HashSet<Position> getLinksSet() {
        return new HashSet<Position>(link.keySet());
    }
    public static int getLinks(Position position) {
        return link.get(position);
    }
    public static void saveLink(Position position, int index) {
        link.put(position,index);
    }
    public static void updataLink(Position position, int index) {
        link.put(position,index);
    }
    public static void removeLink(Position position) {
        link.remove(position);
    }
    public static void saveTLink(int tID,Position position) {
        tLink.put(tID,position);
    }
    public static Position getTLink(int tID) {
        Position position=tLink.get(tID);
        tLink.remove(tID);
        return position;
    }

    public static HashSet<Position> getMaintainTemporaryPosition() {
        return maintainTemporaryPosition;
    }
    public static void deleteMaintainTemporaryPosition() {
        maintainTemporaryPosition=new HashSet<Position>();
    }

    public static void saveMaintainTemporaryPosition(Position position) {
        maintainTemporaryPosition.add(position);
    }

    public static boolean removeTemporaryPosition(Position position) {
        return temporaryPosition.remove(position);
    }
    public static boolean containTemporaryPosition(Position position) {
        return temporaryPosition.contains(position);
    }
    public static void saveTemporaryPosition(Position position) {
        temporaryPosition.add(position);
    }
}
