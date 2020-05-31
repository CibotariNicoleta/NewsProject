package com.example.newsmanagerproject.network;

import android.content.Context;
import android.os.AsyncTask;

import com.example.newsmanagerproject.model.Article;
import com.example.newsmanagerproject.network.errors.ServerComnmunicationError;

public class UpdateArticleTask extends AsyncTask<Void, Void, Void> {
   private Context context;
   private Article article;
   private int id;
    public UpdateArticleTask(Context cont, Article article, int id) {
        super();
        this.context = cont;
        this.article = article;
        this.id = id;

        // TODO Auto-generated constructor stub
    }



    @Override
    protected Void doInBackground(Void...voids) {

        try {
            ModelManager.updateArticle(article, id);
        } catch (ServerComnmunicationError serverComnmunicationError) {
            serverComnmunicationError.printStackTrace();
        }

        return null;
    }
}
