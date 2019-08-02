package com.music.helpyourdj;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class AboutAppActivity extends AppCompatActivity {
ImageView image;
private Context context=AboutAppActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        image=(ImageView)findViewById(R.id.imagegif);
        Glide.with(context)
                .load("https://media.giphy.com/media/l41lXuA00dtqAafCw/giphy.gif")
                .into(image);


    }
}
