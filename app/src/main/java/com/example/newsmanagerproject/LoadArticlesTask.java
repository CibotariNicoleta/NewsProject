package com.example.newsmanagerproject;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.content.SharedPreferences;
import androidx.annotation.RequiresApi;

import com.example.newsmanagerproject.model.Article;
import com.example.newsmanagerproject.network.ModelManager;
import com.example.newsmanagerproject.network.RESTConnection;
import com.example.newsmanagerproject.network.errors.AuthenticationError;
import com.example.newsmanagerproject.network.errors.ServerComnmunicationError;

import java.util.List;

public class LoadArticlesTask  extends AsyncTask<Void, Void, List<Article>> {

    private static final String TAG = "LoadArticlesTask";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Article> doInBackground(Void... voids) {
        List<Article> res = null;
        //ModelManager uses singleton pattern, connecting once per app execution in enough
        if (!ModelManager.isConnected()){
            // if it is the first login

            String strIdUser, strApiKey, strIdAuthUser;
            if (strIdUser == null || strIdUser.equals("")) {
                try {
                    ModelManager.login("ws_user", "ws_password");
                } catch (AuthenticationError e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            // if we have saved user credentials from previous connections
            else{
                ModelManager.stayloggedin(strIdUser,strApiKey,strIdAuthUser);
            }
        }
        //If connection has been successful
        if (ModelManager.isConnected()) {
            try {
                // obtain 6 articles from offset 0
                res = ModelManager.getArticles(6, 0);
                for (Article article : res) {
                    // We print articles in Log
                    Log.i(TAG, String.valueOf(article));
                }
            } catch (ServerComnmunicationError e) {
                Log.e(TAG,e.getMessage());
            }
        }
        return res;
    }
}

