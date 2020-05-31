package com.example.newsmanagerproject.model;

import android.os.AsyncTask;

import com.example.newsmanagerproject.network.ModelManager;
import com.example.newsmanagerproject.network.errors.ServerComnmunicationError;

public class RetrieveArticleTask extends AsyncTask<Void, Void, Void> {
    private int param;
    public RetrieveArticleTask(int param){
        this.param = param;
    }
    @Override
    public Void doInBackground(Void...voids) {
        try {
            ModelManager.deleteArticle(param);
        } catch (ServerComnmunicationError serverComnmunicationError) {
            serverComnmunicationError.printStackTrace();
        }
        return null;
    }
}

