package com.music.helpyourdj;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyRowPub extends RecyclerView.ViewHolder{

    TextView namePub;
    TextView adress;
    TextView website;
    ImageView image;
    public MyRowPub(View pubView){
        super(pubView);
        namePub=(TextView)pubView.findViewById(R.id.txtNamePub);
        adress=(TextView)pubView.findViewById(R.id.txtAdressPub);
        website=(TextView)pubView.findViewById(R.id.txtwebsitePub);
        image=(ImageView)pubView.findViewById(R.id.imagepub);
    }
}
