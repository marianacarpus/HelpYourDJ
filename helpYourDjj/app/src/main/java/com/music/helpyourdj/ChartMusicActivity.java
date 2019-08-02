package com.music.helpyourdj;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChartMusicActivity extends AppCompatActivity {
   List<MusicVoted> musicList ;
    public String dbChild;

    MusicVoted[] music=new MusicVoted[31];
    int [] myColor={};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_music);
        PieChart pieChart = findViewById(R.id.piechart);
        dbChild= getIntent().getStringExtra("DBCHILD");
        musicList= new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child(dbChild).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                musicList.clear();

                for(final DataSnapshot snapshot: dataSnapshot.getChildren()) {

                    MusicVoted mus = new MusicVoted();
                  mus.setArtist(snapshot.child("artist").getValue().toString());
                  mus.setMelodyName(snapshot.child("melodyName").getValue().toString());
                 mus.setUrl(snapshot.child("url").getValue().toString());
                   mus.setVotes(Integer.parseInt(snapshot.child("votes").getValue().toString()));
                    mus.setKey(snapshot.getKey().toString());

                    musicList.add(mus);
                }

              Collections.sort(musicList);
                ArrayList<Integer> colors=new ArrayList<Integer>();
                for(int c:ColorTemplate.VORDIPLOM_COLORS)
                    colors.add(c);
                for(int c:ColorTemplate.JOYFUL_COLORS)
                    colors.add(c);
                for (int c:ColorTemplate.MATERIAL_COLORS)
                    colors.add(c);
                Log.d("data",""+musicList.get(0));
                ArrayList votes=new ArrayList();
                ArrayList<String> details=new ArrayList();
                for (int i=0;i<10;i++){
                    votes.add(new PieEntry(musicList.get(i).votes,"%"));
                    details.add("   "+musicList.get(i).artist+" - "+musicList.get(i).melodyName);

                }
                Log.d("DATA",""+votes);

                PieDataSet dataSet= new PieDataSet(votes,"Number of Votes");
                dataSet.setSliceSpace(3);
                dataSet.getSelectionShift();

                PieData data=new PieData(dataSet);


                pieChart.setHoleColor(Color.LTGRAY);

                int[] colorlist=new int[colors.size()];
                List<LegendEntry> entries = new ArrayList<>();
                String[] legendString=new  String[details.size()];
                for (int i=0;i<legendString.length;i++) {
                    legendString[i] = details.get(i);
                    colorlist[i]=colors.get(i);
                    entries.add(new LegendEntry(details.get(i),Legend.LegendForm.SQUARE,10f,4f,null,colors.get(i)));
                }

                pieChart.setData(data);
                pieChart.getLegend().setWordWrapEnabled(true);
                Legend legend=pieChart.getLegend();
                legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
                legend.setForm(Legend.LegendForm.CIRCLE);
                legend.setTextSize(9f);

                legend.setCustom(entries);
                legend.setTextColor(Color.WHITE);
                legend.setOrientation(Legend.LegendOrientation.VERTICAL);
                legend.setEnabled(true);
                legend.setDrawInside(false);

                dataSet.setValueFormatter(new PercentFormatter());
                dataSet.setColors(colors);
                pieChart.animateXY(5000, 5000);
                pieChart.setCenterTextSize(15f);
              pieChart.setUsePercentValues(true);


                pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                       Log.d("PIECHART",String.valueOf(h.getX()));//get position
                      // Toast.makeText(this,"",t)
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(musicList.get((int) h.getX()).getUrl()));
                       startActivity(browserIntent);

                    }

                    @Override
                    public void onNothingSelected() {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
              //  Toast.makeText(Music.this, "Somethings went wrong",Toast.LENGTH_SHORT).show();

            }
        });

    }
}
