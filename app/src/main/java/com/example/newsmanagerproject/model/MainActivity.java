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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Article> myArticles = new ArrayList<>();
    private ListView recyclerView;
    private NewsAdapter myAdapter;
    private LoadArticlesTask loadArticlesTask;
    private Button loginButon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (ListView) findViewById(R.id.list);
        loadArticlesTask = new LoadArticlesTask(this);
      /*     Image imga = new Image(R.drawable.a);
            Article a = new Article("1" , "The impact of nature", "blablabla",  "bblablabla", "nature",imga);
            myArticles.add(a);
        Image imgb = new Image(R.drawable.b);
        Article b = new Article(" 2" , "The impact of nature", "blablabla",  "bblablabla","nature", imgb);
        myArticles.add(b);

        Image imgc = new Image(R.drawable.c);
        Article c = new Article(" 3" , "The impact of nature", "blablabla", "bblablabla", "nature", imgc);
        myArticles.add(c);

        Image imgd = new Image(R.drawable.d);
        Article d = new Article("4" , "The impact of nature", "blablabla", "bblablabla", "nature", imgd);
        myArticles.add(d);

        Image imge = new Image(R.drawable.e);
        Article e = new Article("5" , "The impact of nature", "blablabla", "bblablabla", "nature",  imge);
        myArticles.add(e);

        Image imgf = new Image(R.drawable.f);
        Article f = new Article("6" , "The impact of nature", "blablabla", "bblablabla", "nature", imgf);
        myArticles.add(f); */
        //myAdapter = new ArrayAdapter<Adapter>(this, android.R.layout.simple_list_item_1);

     loadArticlesTask.execute();

        myAdapter = new NewsAdapter(this, myArticles);
        recyclerView.setAdapter(myAdapter);



        loginButon= (Button) findViewById(R.id.button2);
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

public void getResult(List<Article> myList)
{
    myArticles = (ArrayList<Article>) myList;
}
}