package com.example.newsmanagerproject.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newsmanagerproject.R;
import com.example.newsmanagerproject.network.errors.ServerComnmunicationError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.example.newsmanagerproject.model.MainActivity.isLogged;

public class NewsAdapter extends ArrayAdapter<Article>{


    private Context mContext;
    private List<Article> articles = new ArrayList<>();
    private FrameLayout frameLayout;
    private FloatingActionButton deleteButton;
    private FloatingActionButton modifyButton;

    public NewsAdapter(@NonNull Context context,  List<Article> list) {
        super(context, 0 , list);
        this.mContext = context;
        articles = list;
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent,false);



        deleteButton= listItem.findViewById(R.id.deleteButton);
        modifyButton= listItem.findViewById(R.id.modifyButton);

//        if(!isLogged()){
 //           deleteButton.setVisibility(View.GONE);
 //           modifyButton.setVisibility(View.GONE);
//        }

        final Article article = articles.get(position);

        ImageView image = listItem.findViewById(R.id.image);

        //Array of bytes
        byte[] decodedString = new byte[0];

        //Decode array of character to store on byte format
        try {
            decodedString = Base64.decode(article.getImage().getImage(), Base64.DEFAULT);
        } catch (ServerComnmunicationError serverComnmunicationError) {
            serverComnmunicationError.printStackTrace();
        }

        //Use bitmap object to show the images for every article
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        image.setImageBitmap(decodedByte);

        TextView category = (TextView) listItem.findViewById(R.id.newsCategory);
        category.setText(article.getCategory());

        TextView title = (TextView) listItem.findViewById(R.id.newsTitle);
        title.setText(article.getTitleText());

        TextView Abstract = (TextView) listItem.findViewById(R.id.newsAbstract);
        Abstract.setText(article.getAbstractText());

        //onClick method


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"DeleteButton",Snackbar.LENGTH_SHORT);
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"ModifyButton",Snackbar.LENGTH_SHORT);
            }
        });
        return listItem;
    }

    private boolean isLogged(){
        return isLogged;
    }



}
