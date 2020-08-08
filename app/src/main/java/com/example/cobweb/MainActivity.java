package com.example.cobweb;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Handler;

import com.example.cobweb.net.Net;
import com.example.cobweb.net.NetData;
import com.example.cobweb.net.UserData;
import com.example.cobweb.treat.LinkList;
import com.example.cobweb.treat.Tools;
import com.example.cobweb.treat.user.AbstractLink;
import com.example.cobweb.treat.user.Position;
import com.example.cobweb.ui.connection.ConnectionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataStart();
        net();
        Navigation();
        updata();
    }
    private void updata(){
        new Thread(()->{
            for(;;) {
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(Position position:NetData.getLinksSet()){
                    if(!NetData.getMaintainTemporaryPosition().contains(position)){
                        AbstractLink link=new LinkList().getArrayList(NetData.getLinks(position));
                        link.updataStatusDisplay(R.string.message_link_failure,R.drawable.ic_link_failure);
                        ConnectionFragment.LinksRefresh();
                        NetData.removeLink(position);
                    }
                }
                NetData.deleteMaintainTemporaryPosition();
            }
        });
    }
    private void Navigation(){
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_features, R.id.navigation_connection, R.id.navigation_user)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

    }
    private void dataStart(){
        try {
            Data.mainPosition=new Position(getResources().getString(R.string.position_main));
            Data.aidSendPosition=new Position(getResources().getString(R.string.position_aid_send));
            Data.aidAcceptPosition=new Position(getResources().getString(R.string.position_aid_accept));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Data.netA=ContextCompat.getColor(this,R.color.net_A)|0xff000000;
        Data.netB=ContextCompat.getColor(this,R.color.net_B)|0xff000000;
        Data.netC=ContextCompat.getColor(this,R.color.net_C)|0xff000000;
        Data.netD=ContextCompat.getColor(this,R.color.net_D)|0xff000000;
        ApplicationInfo appInfo = getApplicationInfo();
    }
    private void net(){
        new Net().start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(Tools.byteArrayToInt(UserData.temporaryID)==0){
            System.exit(0);
        }
    }
}