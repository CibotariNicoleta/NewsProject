package com.example.newsmanagerproject.model;

import android.content.Context;
import android.graphics.Movie;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.newsmanagerproject.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<Article>{

    private Context mContext;
    private List<Article> articles = new ArrayList<>();

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

        Article article = articles.get(position);

      //  ImageView image = (ImageView)listItem.findViewById(R.id.image);
      //  image.setImageResource(article.getImage().getId());

        ImageView image = (ImageView)listItem.findViewById(R.id.image);
        image.setImageResource(article.getImage().getId());

        TextView category = (TextView) listItem.findViewById(R.id.newsCategory);
        category.setText(article.getCategory());

        TextView title = (TextView) listItem.findViewById(R.id.newsTitle);
        title.setText(article.getTitle());

        TextView Abstract = (TextView) listItem.findViewById(R.id.newsAbstract);
        Abstract.setText(article.getAbstract());

        return listItem;
    }


}
