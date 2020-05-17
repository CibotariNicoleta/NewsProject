package com.example.newsmanagerproject.model;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsmanagerproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class creatArticle extends AppCompatActivity  {
    private Spinner spinner;
    private FloatingActionButton saveButton,cancelButton;
    private EditText text_title,text_abstract,text_subtitle,text_body;
    private String categoryST;
    private Article articleCreated;
    private Dialog myDialog;
    private String titleST,abstractST,subtitleST,bodyST;
    private TextView category_text, title_text, abstract_text, subtitle_text, body_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //For PopUp
        myDialog= new Dialog(this);
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

        //update article
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
           // String article = extras.getString("article_update");
            //The key argument here must match that used in the other activity
        }


        Article ex = (Article) getIntent().getSerializableExtra("Article");
        if(ex != null){
            articleCreated = ex;
        }

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

        //Buttons Action
        saveButton = findViewById(R.id.save_article_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSave(v);
            }
        });

        cancelButton=findViewById(R.id.cancel_article_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Cancelling Creating Article Operation",Snackbar.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                    }
                }, 1000);
            }
        });


        //here we get value

    }
    private void goSave(View v){
        String user="12";
        if(isValidated()){
            articleCreated=new Article(categoryST,titleST,abstractST,bodyST,subtitleST,user);
            ShowPopUp(v);
//            Intent intentShow= new Intent(getApplicationContext(),PopActivity.class);
//            //To send Article to PopUp Class
//            intentShow.putExtra("ArticleCreated", articleCreated);
//            startActivity(intentShow);
        }
        else{
            Snackbar.make(v,"Please, complete all the fields",Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean isValidated(){

        if(!titleST.matches("") && !abstractST.matches("") && !subtitleST.matches("") && !bodyST.matches("")){
            Log.i("TaG", "Validates:  FULL");
            return true;
        }
        Log.i("TaG", "EMPTY");
        return false;
    }

    public void ShowPopUp(View v){

        myDialog.setContentView(R.layout.activity_popup);
        FloatingActionButton buttonPopUpArticle= myDialog.findViewById(R.id.checkButton);
        category_text = myDialog.findViewById(R.id.category_created);
        category_text.setText(articleCreated.getCategory());

        title_text = myDialog.findViewById(R.id.title_created);
        title_text.setText(articleCreated.getTitleText());

        abstract_text = myDialog.findViewById(R.id.abstract_created);
        abstract_text.setText(articleCreated.getAbstractText());

        subtitle_text = myDialog.findViewById(R.id.subtitle_created);
        subtitle_text.setText(articleCreated.getSubtitleText());

        body_text = myDialog.findViewById(R.id.body_created);
        body_text.setText(articleCreated.getBodyText());

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

        buttonPopUpArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We have to send create Request To API

                //And go to Main Activity
                Intent intentMainAct= new Intent(getApplicationContext(),MainActivity.class);
//            //To send Article to PopUp Class
                startActivity(intentMainAct);
            }
        });
    }


}
