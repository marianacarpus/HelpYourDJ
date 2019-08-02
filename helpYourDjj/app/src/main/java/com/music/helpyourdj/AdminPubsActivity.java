package com.music.helpyourdj;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.music.helpyourdj.ui.main.RecomendedCustomAdapterPub;

import java.util.ArrayList;
import java.util.List;

public class AdminPubsActivity extends AppCompatActivity {
    List<Pubs> pubb ;
    RecyclerView listpub;
    CustomAdapterAdminPub adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pubs);
        listpub = (RecyclerView) findViewById(R.id.recyclerAdminPub);
        listpub.setLayoutManager(new LinearLayoutManager(this));
        pubb=new ArrayList<>();



        //load data from firebase
        FirebaseDatabase.getInstance().getReference().child("pubs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pubb=new ArrayList<>();
                for(final DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Pubs p=new Pubs();
                    p.setNamePub(snapshot.child("namePub").getValue().toString());
                    p.setImagePub(snapshot.child("imagePub").getValue().toString());
                    p.setWebsite(snapshot.child("website").getValue().toString());

                    pubb.add(p);
                }
;
                adapter = new CustomAdapterAdminPub(pubb, AdminPubsActivity.this);
                listpub.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdminPubsActivity.this, "Somethings went wrong",Toast.LENGTH_SHORT).show();
                Log.d("error", databaseError.getMessage());

            }
        });

    }
}
