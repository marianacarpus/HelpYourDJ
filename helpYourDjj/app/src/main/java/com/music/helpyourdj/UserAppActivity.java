package com.music.helpyourdj;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.music.helpyourdj.ui.main.RecomendedCustomAdapterPub;

import java.util.ArrayList;
import java.util.List;

public class UserAppActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {
    private TextView txtEmail, txtUser;
    private FirebaseAuth auth;
    List<Pubs> pubb;
    RecyclerView listpub;
    CustomAdapterPub adapter;
    RecomendedCustomAdapterPub radapter;
    TextView textTitle;
    String nameUser = " ";
    //LOCATION variables
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    Double lattitude, longitude;
    Location locattion;
    String lat;
    String provider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_app);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        textTitle = (TextView) findViewById(R.id.txtsetPage);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //settings for first page-> pubs close to user
        //  ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        // getLocationManager();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


        //Navigation user : settings for detail user
        View header = navigationView.getHeaderView(0);
        txtEmail= (TextView)  header.findViewById(R.id.txtEmailUser);
        txtUser=  (TextView)header.findViewById(R.id.txtNameUser);
        FirebaseUser user = auth.getCurrentUser();
        Toast.makeText(UserAppActivity.this,"connected"+ user.getEmail(), Toast.LENGTH_LONG).show();
        String email = user.getEmail();
        txtEmail.setText(user.getEmail());
        getNameUser(user.getEmail());


    }



    public void getNameUser(String emaill){
        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               // user=new ArrayList<>();
                for(final DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Users us=new Users();
                    us.setEmail(snapshot.child("email").getValue().toString());
                    us.setName(snapshot.child("name").getValue().toString());
                    if(emaill.trim().equals(us.getEmail())){
                        nameUser= us.getName();
                       txtUser.setText(nameUser);

                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserAppActivity.this, "Somethings went wrong",Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void setInitialView(){
        textTitle.setText(" Pubs close to you ");


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            //about application
            Intent intent = new Intent(UserAppActivity.this, AboutAppActivity.class);

            startActivity(intent);
        } else if (id == R.id.nav_recomend) {
            Intent intent = new Intent(UserAppActivity.this, PubsActivity.class);
            intent.putExtra("Recomended","recomend");
            startActivity(intent);




        } else if (id == R.id.nav_clubs) {
            Intent intent = new Intent(UserAppActivity.this, PubsActivity.class);
            intent.putExtra("Recomended","normal");
            startActivity(intent);
            //finish();
            //view all clubs

        } else if (id == R.id.nav_tools) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(UserAppActivity.this, StartApp.class);
                            startActivity(intent);
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(UserAppActivity.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
            //logout user

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
     Log.d("LOCATION","Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        textTitle.setText(" Pubs close to you ");
        listpub =(RecyclerView) findViewById(R.id.recyclerhome);
        listpub.setLayoutManager(new LinearLayoutManager(this));

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
                    p.setLatitude(snapshot.child("latitude").getValue().toString());
                    p.setLongitude(snapshot.child("longitude").getValue().toString());
                    if(distance(location.getLatitude(),location.getLongitude(),Double.valueOf(p.latitude),Double.valueOf(p.longitude)) < 1.01){
                        pubb.add(p);
                    }
                }
                adapter= new CustomAdapterPub(pubb,UserAppActivity.this );
                listpub.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserAppActivity.this, "Something went wrong",Toast.LENGTH_SHORT).show();

            }
        });


    }

    public static double distance(double lat1, double lon1, double lat2,
                                  double lon2) {

       // final int R = 6371; // Radius of the earth
        double dist;

        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
             dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            //transform in kilometres
            dist = dist * 1.609344;
        }




        return dist;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude", "status");

    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude","disable");
    }
}
