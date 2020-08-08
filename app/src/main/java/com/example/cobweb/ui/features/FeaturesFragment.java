package com.example.cobweb.ui.features;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.cobweb.R;
import com.example.cobweb.treat.LinkList;
import com.example.cobweb.treat.user.TOneLink;

public class FeaturesFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_features, container, false);
        root.findViewById(R.id.buttonSendFile).setOnClickListener((view)->{

            int hasWriteStoragePermission = ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
                if(new LinkList().getArrayListSize()==0){
                    new AlertDialog.Builder(getActivity()).setItems(new LinkList().getLinklist(),(dialog, id)->{
                        if(((TOneLink)new LinkList().getArrayList(id)).getPosition()!=null){
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("*/*");
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            startActivityForResult(intent, 1);
                        }
                    });
                }
            }else{
                requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        });
        return root;
    }
}