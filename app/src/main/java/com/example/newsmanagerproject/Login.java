package com.example.newsmanagerproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newsmanagerproject.model.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Login extends AppCompatActivity {

    FloatingActionButton login;
    CheckBox remember_me;
    EditText userName, pwd;
    int counter = 3;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    public static boolean isLogged;
    public static boolean ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        remember_me = findViewById(R.id.checkBox);
        login = findViewById(R.id.loginButton);
        userName = (EditText) findViewById(R.id.userName);
        pwd = (EditText) findViewById(R.id.pwd);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        ok = loginPreferences.getBoolean("saveLogin", false);
        if (ok == true) {
            userName.setText(loginPreferences.getString("username", ""));
            pwd.setText(loginPreferences.getString("password", ""));
            remember_me.setChecked(true);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == login) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(userName.getWindowToken(), 0);

                    if (remember_me.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", userName.getText().toString());
                        loginPrefsEditor.putString("password", pwd.getText().toString());
                        loginPrefsEditor.commit();

                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }

                    doSomethingElse();

                }
            }
        });
    }

    public void doSomethingElse() {
        if (userName.getText().toString().equals("DEV_TEAM_09") &&
                pwd.getText().toString().equals("65424")) {
            Toast.makeText(getApplicationContext(), "Conecting...", Toast.LENGTH_SHORT).show();

            Intent intentGoLogging = new Intent(getApplicationContext(), MainActivity.class);
            //To send article to NewsArticle
            startActivity(intentGoLogging);
        } else {
            Toast.makeText(getApplicationContext(), "Wrong" +
                    "Credentials", Toast.LENGTH_SHORT).show();
            counter--;
            if (counter == 0) {
                login.setEnabled(false);
            }
        }


    }
}
