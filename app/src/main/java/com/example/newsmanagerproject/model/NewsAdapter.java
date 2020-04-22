package com.example.newsmanagerproject.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newsmanagerproject.R;
import com.example.newsmanagerproject.network.errors.ServerComnmunicationError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<Article>{

    private Context mContext;
    private List<Article> articles = new ArrayList<>();
    private FrameLayout frameLayout;

    public NewsAdapter(@NonNull Context context,  ArrayList<Article> list) {
        super(context, 0 , list);
        mContext = context;
        articles = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent,false);

        final Article article = articles.get(position);

      //  ImageView image = (ImageView)listItem.findViewById(R.id.image);
      //  image.setImageResource(article.getImage().getId());

        ImageView image = (ImageView)listItem.findViewById(R.id.image);
        try {
            image.setImageResource(article.getImage().getId());
        } catch (ServerComnmunicationError serverComnmunicationError) {
            serverComnmunicationError.printStackTrace();
        }

        TextView category = (TextView) listItem.findViewById(R.id.newsCategory);
        category.setText(article.getCategory());

        TextView title = (TextView) listItem.findViewById(R.id.newsTitle);
        title.setText(article.getTitleText());

        TextView Abstract = (TextView) listItem.findViewById(R.id.newsAbstract);
        Abstract.setText(article.getAbstractText());


//        frameLayout=(FrameLayout) listItem.findViewById(R.id.frameLayout);
//        frameLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent selectIntent= new Intent(getContext(),NewsArticle.class);
//                selectIntent.putExtra("sample",article);
//                getContext().startActivity(selectIntent);
//
//            }
//        });

        return listItem;
    }



}
