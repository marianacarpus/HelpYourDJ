package com.music.helpyourdj;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Music extends AppCompatActivity {

    public String dbChild, recomend=" ";
    TextView txt;
    DatabaseReference mpubs;
    private ChildEventListener mChildEventListener;
    FirebaseClientPub helper;
    List<MusicVoted> musicList ;
    TextView txtlist;
    String genres=" ";
    RecyclerView listmusic;
    CustomAdapterMusic adapter;
    DatabaseReference mDatabaseRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        txtlist=(TextView)findViewById(R.id.txtlistnull);
        listmusic=(RecyclerView) findViewById(R.id.recyclerMusic);
        listmusic.setLayoutManager(new GridLayoutManager(this,2));
        //get child name to get data from database
        dbChild= getIntent().getStringExtra("DBCHILD");
        recomend=getIntent().getStringExtra("Recomended");
        musicList=new ArrayList<>();
        adapter= new CustomAdapterMusic(Music.this ,musicList,dbChild);
        listmusic.setAdapter(adapter);
        //add objects in list from firebase
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(recomend.trim().equals("ok")) {
            FirebaseDatabase.getInstance().getReference().child("recommended").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if(snapshot.hasChild(user.getUid())) {
                        genres = snapshot.child(user.getUid()).child("genres").getValue().toString();
                    }else{
                        genres=" ";
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });
        }

        FirebaseDatabase.getInstance().getReference().child(dbChild).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                musicList.clear();
                for(final DataSnapshot snapshot: dataSnapshot.getChildren()){
                    MusicVoted mus= new MusicVoted();
                    mus.setArtist(snapshot.child("artist").getValue().toString());
                    mus.setGenre(snapshot.child("genre").getValue().toString());
                    mus.setImage(snapshot.child("image").getValue().toString());
                    mus.setMelodyName(snapshot.child("melodyName").getValue().toString());
                    mus.setUrl(snapshot.child("url").getValue().toString());
                    mus.setVotes(Integer.parseInt(snapshot.child("votes").getValue().toString()));
                    mus.setKey(snapshot.getKey().toString());
                    if(recomend.trim().equals("ok")){
                        if(genres.contains(mus.genre)){
                            musicList.add(mus);
                        }
                    }
                    else{
                            musicList.add(mus);}
                }
                if(musicList.size()==0){
                    txtlist.setText("Sorry,\n    we don't have enough data about you \n \n" +
                            "You can visit Pubs and vote there");
                }
                adapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Music.this, "Somethings went wrong",Toast.LENGTH_SHORT).show();

            }
        });



    }
    public boolean checkGenres(String gen){

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        FirebaseDatabase.getInstance().getReference().child("recommended").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                genres=snapshot.child("genres").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
        if(genres.contains(gen)) {
            return true;
        }

        return false;
    }

}
