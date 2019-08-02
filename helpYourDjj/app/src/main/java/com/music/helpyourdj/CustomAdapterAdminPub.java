package com.music.helpyourdj;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ornach.nobobutton.NoboButton;

import java.util.List;

public class CustomAdapterAdminPub extends RecyclerView.Adapter<CustomAdapterAdminPub.Myrow> {
    List<Pubs> pubs;
    Context context;

    public CustomAdapterAdminPub(List<Pubs> pubs, Context context) {
        this.pubs = pubs;
        this.context = context;
    }

    @NonNull
    @Override
    public Myrow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.rowpubadmin, parent,false);
        Myrow vr=new Myrow(view);
        return vr;
    }

    @Override
    public void onBindViewHolder(@NonNull Myrow holder, int position) {
        holder.pubname.setText(pubs.get(position).getNamePub());
        Glide.with(context).load(pubs.get(position).getImagePub()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pubs.get(position).getWebsite()));
                context.startActivity(browserIntent);
                // Toast.makeText(context,  pubs.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnchart.setOnClickListener(new View.OnClickListener() {
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
               Intent intent = new Intent(context, ChartMusicActivity.class);
                intent.putExtra("DBCHILD",nameValue);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pubs.size();
    }

    public class Myrow extends RecyclerView.ViewHolder {
        TextView pubname;
        NoboButton btnchart;
        ImageView image;
        public Myrow(View itemView) {
            super(itemView);
            pubname=(TextView)itemView.findViewById(R.id.txtNamePubAdmin);
            image=(ImageView)itemView.findViewById(R.id.imagepubadmin);
            btnchart=(NoboButton)itemView.findViewById(R.id.btnchart);
        }
    }
}
