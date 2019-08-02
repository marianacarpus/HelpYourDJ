package com.music.helpyourdj;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.music.helpyourdj.ui.main.RecomendedCustomAdapterPub;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.List;

public class PubsActivity extends AppCompatActivity {
    DatabaseReference mpubs;
   // private DatabaseReference mpubs;
    private ChildEventListener mChildEventListener;
    FirebaseClientPub helper;
   List<Pubs> pubb ;
    RecyclerView listpub;
    CustomAdapterPub adapter;
    String recomend;
    TextView title;
    RecomendedCustomAdapterPub radapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pubs);
        listpub =(RecyclerView) findViewById(R.id.recycler);
        listpub.setLayoutManager(new LinearLayoutManager(this));
        title=(TextView) findViewById(R.id.txtsee);

        recomend=getIntent().getStringExtra("Recomended");
        //load data from firebase
        FirebaseDatabase.getInstance().getReference().child("pubs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pubb=new ArrayList<>();
                for(final DataSnapshot snapshot: dataSnapshot.getChildren()){
                 Pubs p=new Pubs();
                 p.setNamePub(snapshot.child("namePub").getValue().toString());
                 p.setAdress(snapshot.child("adress").getValue().toString());
                 p.setImagePub(snapshot.child("imagePub").getValue().toString());
                 p.setWebsite(snapshot.child("website").getValue().toString());
                 pubb.add(p);
                }
                if(recomend.equals("recomend")){
                    title.setText("First you need to choose a pub!");
                    radapter = new RecomendedCustomAdapterPub(pubb, PubsActivity.this);
                    listpub.setAdapter(radapter);
                }
                else {
                    adapter = new CustomAdapterPub(pubb, PubsActivity.this);
                    listpub.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PubsActivity.this, "Somethings went wrong",Toast.LENGTH_SHORT).show();

            }
        });

    }


}
