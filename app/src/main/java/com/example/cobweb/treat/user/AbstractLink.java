package com.example.cobweb.treat.user;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.cobweb.R;
import com.example.cobweb.net.NetData;
import com.example.cobweb.treat.LinkList;
import com.example.cobweb.ui.connection.ConnectionFragment;


public abstract class AbstractLink {
    protected View dialogView;
    protected int index;
    protected boolean start;
    protected String title;
    protected int string=-1;
    protected int message=-1;
    protected int drawable=-1;
    public AbstractLink(View root,String TID,int string){
        index = new LinkList().getArrayListSize();
        try {
            start(root,new StringBuffer(" ").append(TID).append("(").append(root.getResources().getString(string)).append(")").toString());
        }catch (Exception e){
            start=false;
            this.title=TID;
            this.string=string;
        }
    }

    public AbstractLink(View root,String title){
        index = new LinkList().getArrayListSize();
        try {
            start(root,title);
        }catch (Exception e){
            start=false;
            this.title=title;
        }
    }
    private void start(View root,String title){
        this.title=title;
        dialogView = View.inflate(root.getContext(), R.layout.fragment_link, null);
        ((TextView) dialogView.findViewById(R.id.link_name)).setText(title);
        dialogView.findViewById(R.id.linkCut).setOnClickListener((view) -> {
            close();
        });
        start=true;
    }

    public void loading(View root){
        if(!start){
            if(string!=-1){
                title=new StringBuffer(" ").append(title).append("(").append(root.getResources().getString(string)).append(")").toString();
            }
            start(root,title);
            start=true;
        }
        ((LinearLayout)root.findViewById(R.id.linkList)).addView(dialogView);
        if(message!=-1){
            ((TextView)dialogView.findViewById(R.id.statusText)).setText(root.getResources().getText(message));
        }if(drawable!=-1){
            ((ImageView)dialogView.findViewById(R.id.statusImage)).setImageDrawable(root.getResources().getDrawable(drawable,null));
        }
    }
    public void updata(int index){
        if(this.index>index){
            this.index--;
        }
    }
    public int getIndex(){
        return index;
    }
    public void updataStatusDisplay(int message, int drawable){
        this.message=message;
        this.drawable=drawable;
    }
    public boolean isClose(){
        return dialogView==null;
    }
    public void close(){
        dialogView = null;
        new LinkList().removeArrayList(index);
        ConnectionFragment.LinksRefresh();
    }
    public String getTitle(){
        return title;
    }
}
