package com.example.cobweb.treat.send;

import android.view.View;

import com.example.cobweb.R;
import com.example.cobweb.net.UserData;
import com.example.cobweb.net.NetData;
import com.example.cobweb.net.send.Send;
import com.example.cobweb.treat.user.Position;
import com.example.cobweb.treat.user.TOneLink;
import com.google.android.material.snackbar.Snackbar;

public class SendTreat {
    public boolean start(){
        new Send(new byte[]{0});
        return true;
    }

}
