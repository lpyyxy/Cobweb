package com.example.cobweb.ui.connection;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.cobweb.R;
import com.example.cobweb.net.NetData;
import com.example.cobweb.net.UserData;
import com.example.cobweb.net.send.Send;
import com.example.cobweb.treat.LinkList;
import com.example.cobweb.treat.NetTreat;
import com.example.cobweb.treat.Tools;
import com.example.cobweb.treat.user.AbstractLink;
import com.example.cobweb.treat.user.Position;
import com.example.cobweb.treat.user.TOneLink;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ConnectionFragment extends Fragment {

    private FloatingActionButton fab;
    private boolean isCreateUnfold=true;
    private PopupMenu popup;
    private AlertDialog.Builder builder;
    private static View root;
    private static Handler handler;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_connection, container, false);
        fabStart();
        popupStart();
        oneDialogStart();
        LinksStart();
        handler=new Handler();
        return root;
    }


    private void one(byte[] id,byte[] checkCode, final View view){
        TOneLink link=new TOneLink(view,Tools.byteArrayToTID(id),R.string.text_link_tuser);
        new LinkList().saveArrayList(link);
        LinksStart();
        new Thread(()->{
            info(R.string.message_link_connecting, Snackbar.LENGTH_LONG);
            new Send(new byte[]{1}, UserData.temporaryID,new byte[]{0},id,checkCode);
            Position position;
            for(int i=0;i<3;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if((position=NetData.getTLink(Tools.byteArrayToInt(id)))!=null){
                    NetTreat.connectionTOneLink(link,position,1000);
                    return;
                }
            }
            if(!link.isClose()){
                link.updataStatusDisplay(R.string.message_link_timeout,R.drawable.ic_link_failure);
                LinksRefresh();
                alert(R.string.message_link_timeout, Snackbar.LENGTH_LONG);
            }
        }).start();
    }

    private void addClose(){
        addClick(new RotateAnimation(45,0,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f));
        isCreateUnfold=true;
    }
    private void addOpen(){
        addClick(new RotateAnimation(0,45,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f));
        isCreateUnfold=false;
        popup.show();
    }
    private void addClick(Animation rotateAnimation){
        rotateAnimation.setDuration(300);
        rotateAnimation.setFillAfter(true);
        fab.startAnimation(rotateAnimation);
    }
    private void popupStart(){
        popup = new PopupMenu(this.getContext(), fab);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    assert menuPopupHelper != null;
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.bottom_connection_add, popup.getMenu());
        popup.setOnMenuItemClickListener((item)->{
            switch (item.getItemId()) {
                case R.id.one:
                    oneDialogStart();
                    builder.show();
                    return true;
                case R.id.group:
                    return true;
                }
                return false;
        });
        popup.setOnDismissListener((popupMenu)-> addClose());
    }
    private void fabStart(){
        fab=root.findViewById(R.id.floatingActionButton);

        fab.setOnClickListener((view) ->{
            if(isCreateUnfold){
                addOpen();
            }else{
                addClose();
            }
        });
    }
    private void oneDialogStart(){
        builder = new AlertDialog.Builder(getActivity());
        final View dialogView= View.inflate(root.getContext(),R.layout.dialog_one_add,null);
        builder.setTitle(R.string.dialog_one_title)
                .setIcon(R.drawable.ic_connection_person_add)
                .setView(dialogView)
                .setPositiveButton(R.string.dialog_one_create,(dialog, ID) ->{
                    String id=((EditText) dialogView.findViewById(R.id.ID)).getText().toString();
                    String checkCode=((EditText) dialogView.findViewById(R.id.checkCode)).getText().toString();
                    if(id.length()==0|checkCode.length()==0){
                        alert( R.string.error_A0410, Snackbar.LENGTH_LONG);
                        return;
                    }if(id.length()!=10&checkCode.length()!=5){
                        alert(R.string.error_A0421, Snackbar.LENGTH_LONG);
                    }else {
                        try {
                            one(Tools.tIDTobyteArray(id),Tools.checkCodeTobyteArray(checkCode),root);
                        }catch (NumberFormatException e){
                            alert(R.string.error_A0421, Snackbar.LENGTH_LONG);
                        }
                    }
                })
                .setNegativeButton(R.string.dialog_one_cancel,(dialog,id) ->{});
    }
    public synchronized static void LinksStart(){
        if(root!=null) {
            ((LinearLayout)root.findViewById(R.id.linkList)).removeAllViews();
            for(AbstractLink link : new LinkList().getArrayList()) {
                link.loading(root);
            }
        }
    }
    public static void LinksRefresh(){
        if(handler!=null){
            handler.post(()->LinksStart());
        }
    }

    public void info(int message,int duration){
        snackbar(message,duration,R.color.snackbar_info);
    }
    public void confirm(int message,int duration){
        snackbar(message,duration,R.color.snackbar_confirm);
    }
    public void warning(int message,int duration){
        snackbar(message,duration,R.color.snackbar_warning);
    }
    public void alert(int message,int duration){
        snackbar(message,duration,R.color.snackbar_alert);
    }
    private void snackbar(int message,int duration,int colorR){
        if(root!=null){
            Snackbar snackbar=Snackbar.make(root,root.getResources().getString(message),duration);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(root.getContext(),colorR)|0xff000000);
            snackbar.show();
        }
    }
}