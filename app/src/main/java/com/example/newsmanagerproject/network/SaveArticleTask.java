package com.example.newsmanagerproject.network;

import android.content.Context;
import android.os.AsyncTask;

import com.example.newsmanagerproject.model.Article;
import com.example.newsmanagerproject.network.errors.ServerComnmunicationError;

public class SaveArticleTask extends AsyncTask<Void, Void, Void> {
 Context context;
 Article article;
    public SaveArticleTask(Context cont, Article article) {
        super();
        this.context = cont;
        this.article = article;

        // TODO Auto-generated constructor stub
    }



    @Override
    protected Void doInBackground(Void...voids) {

        try {
            ModelManager.saveArticle(article);
        } catch (ServerComnmunicationError serverComnmunicationError) {
            serverComnmunicationError.printStackTrace();
        }

        return null;
    }
}
