package com.example.newsmanagerproject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.newsmanagerproject.model.Article;
import com.example.newsmanagerproject.model.MainActivity;
import com.example.newsmanagerproject.network.ModelManager;
import com.example.newsmanagerproject.network.errors.AuthenticationError;
import com.example.newsmanagerproject.network.errors.ServerComnmunicationError;

import java.util.List;
import java.util.Properties;

import static com.example.newsmanagerproject.network.RESTConnection.ATTR_SERVICE_URL;

public class LoadArticlesTask  extends AsyncTask<Void, Void, List<Article>> {

    private static final String TAG = "LoadArticlesTask";
    Context context;
    public LoadArticlesTask(Context cont) {
        super();
        this.context = cont;
        // TODO Auto-generated constructor stub
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Article> doInBackground(Void... voids) {
        List<Article> res = null;
        //added here
        Properties p = new Properties();
        p.put(ATTR_SERVICE_URL, "https://sanger.dia.fi.upm.es/pmd-task/");
        ModelManager.configureConnection(p);
       String strIdUser = ModelManager.getIdUser(), strApiKey = ModelManager.getLoggedApiKey(), strIdAuthUser = ModelManager.getLoggedAuthType();
        //ModelManager uses singleton pattern, connecting once per app execution in enough
        if (!ModelManager.isConnected()){
            // if it is the first login
            if (ModelManager.getIdUser() == null || ModelManager.getIdUser().equals("")) {
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
        // added here
        ((MainActivity)context).getResult(res);
        Toast.makeText(context, res.toString(), Toast.LENGTH_SHORT).show(); // res is null
        return res;
    }

}

