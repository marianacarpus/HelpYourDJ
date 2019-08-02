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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ornach.nobobutton.NoboButton;

import java.util.List;

public class CustomAdapterMusic extends RecyclerView.Adapter<CustomAdapterMusic.MyMusicHolder> {

    Context context;
    List<MusicVoted> listmusic;
    private FirebaseAuth auth;
    String dbRefchild;

    String genres=" ";
    int key;
    public CustomAdapterMusic(Context context, List<MusicVoted> listmusic,String dbRefchild) {
        this.context = context;
        this.listmusic = listmusic;
        this.dbRefchild=dbRefchild;
    }

    @NonNull
    @Override
    public MyMusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowmusic, parent , false);

        MyMusicHolder vh = new MyMusicHolder( view );
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyMusicHolder holder, int position) {
       // Log.d("POSITION", "" + position);
        holder.artist.setText(listmusic.get(position).getArtist());
        holder.title.setText(listmusic.get(position).getMelodyName());
        Glide.with(context).load(listmusic.get(position).getImage()).into(holder.imagemus);
       //holder.setPosition(position);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        FirebaseDatabase.getInstance().getReference().child("recommended").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.hasChild(user.getUid())) {
                    genres = snapshot.child(user.getUid()).child("genres").getValue().toString();
                } else {
                    FirebaseDatabase.getInstance().getReference("recommended").child(user.getUid()).child("genres").setValue(" ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listmusic.get(position).getUrl()));
                context.startActivity(browserIntent);

                // Toast.makeText(context,  pubs.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnVote.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.d("POSITIONS item ", ""+listmusic.get(position).getKey());
                key=Integer.parseInt(listmusic.get(position).getKey());
                int votes=0;
                votes=listmusic.get(key).getVotes();

                if(holder.btnVote.getText().trim().equals("Vote")) {
                   votes++;
                   if(!genres.contains(listmusic.get(key).getGenre())) {
                       genres = genres.trim() + "*" + listmusic.get(key).getGenre();
                   }
                    holder.check="ok";
                    holder.btnVote.setText("Unvote");
                    holder.btnVote.setFontIcon("\uf165");


                } else {
                   votes--;
                    holder.check="unlike";
                    holder.btnVote.setText("Vote");
                    holder.btnVote.setFontIcon("\uf164");
                    holder.btnVote.setEnabled(false);
                }
                FirebaseDatabase.getInstance().getReference("recommended").child(user.getUid()).child("genres").setValue(genres);
                FirebaseDatabase.getInstance().getReference(dbRefchild).child(String.valueOf(key)).child("votes").setValue(String.valueOf(votes));
            }
        });

    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return listmusic.size();
    }

    class MyMusicHolder extends RecyclerView.ViewHolder{
        ImageView imagemus;
        TextView artist;
        TextView title;
        NoboButton btnVote;
        int position;
        int votes=0;
        String check=" ";

        public void setPosition(int position) {
            this.position = position;
        }

        public MyMusicHolder(View musicView) {

            super(musicView);
            imagemus= (ImageView) musicView.findViewById(R.id.imagemusic);
            artist=(TextView) musicView.findViewById(R.id.txtNameArtist);
            title=(TextView) musicView.findViewById(R.id.txtmusicTitle);
            btnVote=(NoboButton) musicView.findViewById(R.id.btnvote);
            votes=listmusic.get(position).getVotes();
        }
    }
}
