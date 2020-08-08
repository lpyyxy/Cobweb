package com.example.cobweb.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.cobweb.Data;
import com.example.cobweb.net.UserData;
import com.example.cobweb.R;
import com.example.cobweb.treat.Tools;

public class UserFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        TextView ID = root.findViewById(R.id.IDTextView1);
        TextView temporaryID = root.findViewById(R.id.temporaryIDTextView1);
        TextView name = root.findViewById(R.id.nameTextView1);
        TextView type = root.findViewById(R.id.typeTextView1);
        TextView checkCode = root.findViewById(R.id.checkCodeTextView1);
        updataNetType(type);
        name.setText(UserData.name);
        temporaryID.setText(Tools.byteArrayToTID(UserData.temporaryID));
        ID.setText(UserData.ID);
        checkCode.setText(Tools.byteArrayToCheckCode(UserData.checkCode));
        type.setOnClickListener((view)-> {
            updataNetType((TextView)view);
            Toast.makeText(view.getContext(),getResources().getString(R.string.message_refresh_success),Toast.LENGTH_SHORT).show();
        });
        checkCode.setOnClickListener((view)-> {
            UserData.checkCode =Tools.shortToByteArray((short)Tools.random(65535));
            ((TextView)view).setText(Tools.byteArrayToCheckCode(UserData.checkCode));
            Toast.makeText(view.getContext(),getResources().getString(R.string.message_refresh_success),Toast.LENGTH_SHORT).show();
        });
        return root;
    }
    private void updataNetType(TextView type){
        switch(UserData.positionType){
            case 0:
                type.setText("A");
                type.setTextColor(Data.getNetA());
                break;
            case 1:
                type.setText("B");
                type.setTextColor(Data.getNetB());
                break;
            case 2:
                type.setText("C");
                type.setTextColor(Data.getNetC());
                break;
            case 3:
                type.setText("D");
                type.setTextColor(Data.getNetD());
                break;
        }
    }
}