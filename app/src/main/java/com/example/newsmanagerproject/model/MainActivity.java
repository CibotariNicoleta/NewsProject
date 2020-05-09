package com.example.newsmanagerproject.model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.newsmanagerproject.LoadArticlesTask;
import com.example.newsmanagerproject.Login;
import com.example.newsmanagerproject.R;
import com.example.newsmanagerproject.model.Article;
import com.example.newsmanagerproject.model.Image;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Article> myArticles = new ArrayList<>();
    private ListView recyclerView;
    private NewsAdapter myAdapter;
    private LoadArticlesTask loadArticlesTask;
    private FloatingActionButton loginButon;
    private NewsArticle newsArticle;
    private List<Article> listRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (ListView) findViewById(R.id.list);
        loadArticlesTask = new LoadArticlesTask(this);
        listRes = null;
        try {
            listRes = loadArticlesTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        myAdapter = new NewsAdapter(this, listRes);
        recyclerView.setAdapter(myAdapter);

        // This let us set every item clickable
        recyclerView.setClickable(true);
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view,"Element clicked -> "+ position,Snackbar.LENGTH_LONG).show();
                Log.i("Click", "click en el elemento " + position + " de mi ListView");
                goNewsArticle(view,position);
            }
        });

        loginButon= (FloatingActionButton) findViewById(R.id.loginButton);
        loginButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin(v);
            }
        });
    }


    public void goLogin(View view){

        Intent intent=new Intent(this, Login.class);
        startActivity(intent);

    }
    //Method that permit acces to the NewArticle class
    public void goNewsArticle(View view,int position){
        Intent intentNewsArticle=new Intent(this,NewsArticle.class);

        //Bundle bundle = new Bundle();
        //bundle.putSerializable("Article", listRes.get(position));

        intentNewsArticle.putExtra("Article",listRes.get(position));
        startActivity(intentNewsArticle);
    }


    public void getResult(List<Article> myList)
    {
        myArticles = (ArrayList<Article>) myList;
    }
}