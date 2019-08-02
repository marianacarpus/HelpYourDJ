package com.music.helpyourdj;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ornach.nobobutton.NoboButton;

public class Login extends AppCompatActivity {
 private EditText email;
 private  EditText password;
  private NoboButton btnLogin;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        //FirebaseApp.initializeApp(Login)

        auth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.pass);
        btnLogin=(NoboButton)findViewById(R.id.login);
        ImageView image=findViewById(R.id.imagelogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String emaill = email.getText().toString();
                String pass = password.getText().toString().trim();

                if (!isValidPassword(pass)) {
                    password.setError("Invalid Password");
                    password.setText("");
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                }
                if (emaill.trim().equals("admin") && pass.trim().equals("admin")) {
                    Intent intent = new Intent(Login.this, AdminPubsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (!isValidEmail(emaill)) {
                        email.setError("Invalid Email");
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        email.setText("");
                    }
                    if (isValidEmail(emaill) && isValidPassword(pass)) {
                        try {

                            auth.signInWithEmailAndPassword(emaill, hashPass.encrypt(pass))
                                    //auth.signInWithEmailAndPassword(emaill,pass)
                                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            // If sign in fails, display a message to the user. If sign in succeeds
                                            // the auth state listener will be notified and logic to handle the
                                            // signed in user can be handled in the listener.

                                            if (!task.isSuccessful()) {
                                                Toast.makeText(Login.this, "Authentication failed." +"\n Try again",
                                                        Toast.LENGTH_SHORT).show();
                                            } else {
                                                FirebaseUser user = auth.getCurrentUser();

                                                Toast.makeText(Login.this, "connected" , Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(Login.this, UserAppActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }

                                        }
                                    });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean isValidPassword(String pass) {
        if(pass != null && pass.length() >=4) {
            return true;
        }
        return false;
    }
}