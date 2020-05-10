package com.example.newsmanagerproject.model;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsmanagerproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class creatArticle extends AppCompatActivity  {
    private Spinner spinner;
    private FloatingActionButton saveButton;
    private EditText text_title;
    private EditText text_abstract;
    private EditText text_subtitle;
    private EditText text_body;
    private String categoryST;

    private String titleST,abstractST,subtitleST,bodyST;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //Code to get the data of the spinner
        spinner =findViewById(R.id.spinner_categories);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryST = spinner.getSelectedItem().toString();
                Snackbar.make(view,"Item selected -> "+categoryST,Snackbar.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //Get all data

        text_title=findViewById(R.id.text_create_title);
        titleST=text_title.getText().toString();
        text_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                titleST=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                titleST=s.toString();
            }
        });


        text_abstract=findViewById(R.id.text_create_abstract);
        abstractST=text_abstract.getText().toString();
        text_abstract.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                abstractST=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                abstractST=s.toString();
            }
        });


        text_subtitle=findViewById(R.id.text_create_subtitle);
        subtitleST=text_subtitle.getText().toString();
        text_subtitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                subtitleST=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                subtitleST=s.toString();
            }
        });


        text_body=findViewById(R.id.text_create_body);
        bodyST=text_body.getText().toString();
        text_body.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bodyST=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                bodyST=s.toString();
            }
        });

        saveButton = findViewById(R.id.save_article_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSave(v);
            }
        });
    }
    private void goSave(View v){
        isValidated();
    }

    private boolean isValidated(){

        if(!titleST.matches("") && !abstractST.matches("") && !subtitleST.matches("") && !bodyST.matches("")){
            Log.i("TaG", "Validates:  FULL");
            return true;
        }
        Log.i("TaG", "EMPTY");
        return false;
    }


}
