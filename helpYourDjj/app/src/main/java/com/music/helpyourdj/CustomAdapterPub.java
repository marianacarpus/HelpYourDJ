package com.music.helpyourdj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.ornach.nobobutton.NoboButton;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

   public class CustomAdapterPub extends RecyclerView.Adapter<CustomAdapterPub.MyRowPubb> {

    List<Pubs> pubs;
    Context context;

       public CustomAdapterPub(List<Pubs> pubs, Context context) {
           this.pubs = pubs;
           this.context = context;
       }

       @NonNull
       @Override
       public MyRowPubb onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowpub, parent , false);

           MyRowPubb vh = new MyRowPubb( view );
           return vh;
       }

       @Override
       public void onBindViewHolder(@NonNull MyRowPubb holder, int position) {
        holder.namePub.setText(pubs.get(position).getNamePub());
        holder.adress.setText(pubs.get(position).getAdress());
        holder.website.setText(pubs.get(position).getWebsite());
           Glide.with(context).load(pubs.get(position).getImagePub()).into(holder.image);

           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pubs.get(position).getWebsite()));
                   context.startActivity(browserIntent);
                  // Toast.makeText(context,  pubs.get(position).toString(), Toast.LENGTH_SHORT).show();
               }
           });

           holder.btnVote.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View view) {

                   String nameValue ="";
                   if (pubs.get(position).getNamePub().equals("La Fierarie")){
                       nameValue="musicFierarie";
                   } else  if (pubs.get(position).getNamePub().equals("31 Motor Pub")){
                       nameValue="music31Motor";
                   }else  if (pubs.get(position).getNamePub().equals("Versus")){
                       nameValue="musicVersus";
                   } else  if (pubs.get(position).getNamePub().equals("Escu Pub")){
                       nameValue="musicEscu";
                   } else  if ( pubs.get(position).getNamePub().equals("Lounge")){
                       nameValue="musicLounge";
                   } else  if ( pubs.get(position).getNamePub().equals("Oscar Wilde")){
                       nameValue="musicOscar";
                   }else  if (pubs.get(position).getNamePub().equals("Padrino")){
                       nameValue="musicPadrino";
                   }else  if ( pubs.get(position).getNamePub().equals("Crystal's Club")){
                       nameValue="musicCrystal";
                   }
                   Log.d("TAG",String.valueOf(position));
                  Intent intent = new Intent(context, Music.class);
                 intent.putExtra("DBCHILD",nameValue);
                   intent.putExtra("Recomended","nn");
                  context.startActivity(intent);

               }
           });
       }

       @Override
       public int getItemCount() {
           return pubs.size();
       }


      public class MyRowPubb extends RecyclerView.ViewHolder {

            TextView namePub;
            TextView adress;
            TextView website;
            ImageView image;
           NoboButton btnVote;

            public MyRowPubb(View pubView){
                super(pubView);
                namePub=(TextView)pubView.findViewById(R.id.txtNamePub);
                adress=(TextView)pubView.findViewById(R.id.txtAdressPub);
                website=(TextView)pubView.findViewById(R.id.txtwebsitePub);
                image=(ImageView)pubView.findViewById(R.id.imagepub);
               btnVote=(NoboButton)pubView.findViewById(R.id.btnmusiclist);
            }
    }


   }
