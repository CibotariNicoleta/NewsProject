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

        TextView title = (TextView) listItem.findViewById(R.id.newsTitle);
        title.setText(article.getTitle());

        TextView subtitle = (TextView) listItem.findViewById(R.id.newsSubtitle);
        subtitle.setText(article.getSubtitle());

        TextView Abstract = (TextView) listItem.findViewById(R.id.newsSubtitle);
        subtitle.setText(article.getAbstract());

        return listItem;
    }

  /*  private int layoutResourceId;

    private static final String LOG_TAG = "Adapter";

    public Adapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        layoutResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            Article item = getItem(position);
            View v = null;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                v = inflater.inflate(layoutResourceId, null);

            } else {
                v = convertView;
            }

            TextView title = (TextView) v.findViewById(R.id.newsTitle);
            TextView subtitle = (TextView) v.findViewById(R.id.newsSubtitle);
            TextView Abstract = (TextView) v.findViewById(R.id.newsAbstract);
            ImageView img = (ImageView) v.findViewById(R.id.image);

            title.setText(item.getTitle());
            subtitle.setText(item.getSubtitle());
            Abstract.setText(item.getAbstract());
            img.setImageResource(item.getImage().getId());


            return v;
        } catch (Exception ex) {
            Log.e(LOG_TAG, "error", ex);
            return null;
        }
    }*/
}
