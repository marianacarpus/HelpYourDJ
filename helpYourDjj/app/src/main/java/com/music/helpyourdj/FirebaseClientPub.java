package com.music.helpyourdj;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class FirebaseClientPub {

    DatabaseReference db;
    Boolean saved=null;
    List<Pubs> pubb;

    public FirebaseClientPub(DatabaseReference db) {
        this.db = db;
    }

    //WRITE
    public Boolean save(Pubs pub)
    {
        if(pub==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.child("Pubs").push().setValue(pub);
                saved=true;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }

    //READ
    public List<Pubs> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return pubb;
    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        pubb.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {

            String name=ds.getValue(Pubs.class).getNamePub();
            String adress= ds.getValue(Pubs.class).getAdress();
            String imagePub= ds.getValue(Pubs.class).getImagePub();
            String website= ds.getValue(Pubs.class).getWebsite();
            Pubs pp=new Pubs(website,name,imagePub,adress);

            pubb.add(pp);
        }
    }
}
