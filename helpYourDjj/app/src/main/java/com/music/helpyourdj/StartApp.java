package com.music.helpyourdj;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class StartApp extends AppCompatActivity {
    private Button btn_register;
    private Button btn;
public void init(){
    btn_register = (Button)findViewById(R.id.btn_register);

    btn_register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //startActivity(new Intent(StartApp.this, Register.class));
            Intent reg = new Intent(StartApp.this, Register.class);
            startActivity(reg);
        }
    });
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);

        btn = (Button)findViewById(R.id.btn_login);
        init();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(StartApp.this, LoginActivity.class));
                Intent activity2Intent = new Intent(getApplicationContext(), Login.class);
                startActivity(activity2Intent);
            }
        });

    }


}
