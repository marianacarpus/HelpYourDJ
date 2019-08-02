package com.music.helpyourdj;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.ornach.nobobutton.NoboButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Register extends AppCompatActivity {
  private  EditText editName;
    private EditText editEmail;
    private EditText editPassword;
    private  EditText editConfirmPass, birthDate;
    private DatabaseReference referenceDb;
    private FirebaseAuth auth;
    private Users user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editName = (EditText) findViewById(R.id.editName);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword=(EditText)findViewById(R.id.pass);
        editConfirmPass=(EditText)findViewById(R.id.confPass);
        birthDate=(EditText)findViewById(R.id.txtBDate);
        NoboButton btnRegister = (NoboButton)findViewById(R.id.btnRegister);
        auth = FirebaseAuth.getInstance();
        referenceDb = FirebaseDatabase.getInstance().getReference().child("Users");
        user = new Users();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                 String name=editName.getText().toString();
                if(!isValidName(name)){
                    editName.setError("Invalid Name");
                    editName.setText("");
                }
                 String email = editEmail.getText().toString();
                if (!isValidEmail(email)) {
                    editEmail.setError("Invalid Email");
                }

                 String pass = editPassword.getText().toString().trim();
                if (!isValidPassword(pass)) {
                    editPassword.setError("Invalid Password");
                    editPassword.setText("");
                }
                 String confPass=editConfirmPass.getText().toString().trim();
                if(!isValidConfirmPassword(pass,confPass)){
                    editConfirmPass.setError("Password doesn't match");
                    editConfirmPass.setText("");
                }
                if(!isValidDate(String.valueOf(birthDate.getText()))){
                    birthDate.setError("Invalid Birth Date!");
                    birthDate.setText("");
                }
                if(isValidName(name) && isValidEmail(email) && isValidPassword(pass) && isValidConfirmPassword(pass,confPass) && isValidDate(String.valueOf(birthDate.getText()))){
                    //new user
                    user.setName(editName.getText().toString().trim());
                    try {
                        user.setPassword(hashPass.encrypt(editPassword.getText().toString().trim()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    user.setBirthDate( birthDate.getText().toString().trim());
                    user.setEmail(editEmail.getText().toString().trim());

                    referenceDb.push().setValue(user);
                    Toast.makeText(Register.this,"user recorded", Toast.LENGTH_LONG).show();


                    try {
                        auth.createUserWithEmailAndPassword(email, hashPass.encrypt(pass))
                                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        //Toast.makeText(Register.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(Register.this, "Authentication failed." + task.getException(),
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Intent activity2Intent = new Intent(getApplicationContext(), Login.class);
                                            startActivity(activity2Intent);
                                           finish();
                                        }
                                    }
                                });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private boolean isValidName(String name) {
        if(name != null && name.length() >=3) {
            return true;
        }
        return false;
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
    private boolean isValidDate(String date) {
        if(date != null && date.length() >=4) {
            return true;
        }
        return false;
    }
    private boolean isValidConfirmPassword(String pass,String confPass) {
        if(confPass != null && pass.equals(confPass)) {
            return true;
        }
        return false;
    }
}
