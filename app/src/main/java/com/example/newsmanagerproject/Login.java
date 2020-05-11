package com.example.newsmanagerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Login extends AppCompatActivity {

    FloatingActionButton login;
    EditText userName, pwd;
    int counter =3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login= findViewById(R.id.loginButton);
        userName=(EditText)findViewById(R.id.userName);
        pwd=(EditText)findViewById(R.id.pwd);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().equals("DEV_TEAM_09") &&
                        pwd.getText().toString().equals("65424")) {
                    Toast.makeText(getApplicationContext(),
                            "Conecting...",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong"+
                            "Credentials",Toast.LENGTH_SHORT).show();
                    counter--;
                    if (counter == 0) {
                        login.setEnabled(false);
                    }
                }
            }
        });
    }

}
