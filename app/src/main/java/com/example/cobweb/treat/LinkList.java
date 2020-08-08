package com.example.cobweb.treat;

import com.example.cobweb.treat.user.AbstractLink;

import java.util.ArrayList;

public class LinkList {
    private static ArrayList<AbstractLink> linkList=new ArrayList<AbstractLink>();
    public AbstractLink getArrayList(int index) {
        return linkList.get(index);
    }
    public ArrayList<AbstractLink> getArrayList() {
        return linkList;
    }
    public void saveArrayList(AbstractLink link) {
        linkList .add(link);
    }
    public int getArrayListSize() {
        return linkList.size();
    }
    public void removeArrayList(int index) {
        linkList.remove(index);
        for(AbstractLink link:linkList){
            link.updata(index);
        }
    }
    public CharSequence[] getLinklist() {
        ArrayList<String> l=new ArrayList<String>();
        for(AbstractLink link:linkList){
            l.add(link.getTitle());
        }
        return (CharSequence[])l.toArray();
    }
}
