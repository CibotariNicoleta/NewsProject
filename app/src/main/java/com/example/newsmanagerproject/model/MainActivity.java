package com.example.newsmanagerproject.model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.newsmanagerproject.R;
import com.example.newsmanagerproject.model.Article;
import com.example.newsmanagerproject.model.Image;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Article> myArticles = new ArrayList<>();
    private ListView recyclerView;
    private NewsAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (ListView) findViewById(R.id.list);
           Image imga = new Image(R.drawable.a);
            Article a = new Article("1" , "The impact of nature", "blablabla", "bblablabla", imga);
            myArticles.add(a);
        Image imgb = new Image(R.drawable.b);
        Article b = new Article(" 2" , "The impact of nature", "blablabla", "bblablabla", imgb);
        myArticles.add(b);

        Image imgc = new Image(R.drawable.c);
        Article c = new Article(" 3" , "The impact of nature", "blablabla", "bblablabla", imgc);
        myArticles.add(c);

        Image imgd = new Image(R.drawable.d);
        Article d = new Article("4" , "The impact of nature", "blablabla", "bblablabla", imgd);
        myArticles.add(d);

        Image imge = new Image(R.drawable.e);
        Article e = new Article("5" , "The impact of nature", "blablabla", "bblablabla", imge);
        myArticles.add(e);

        Image imgf = new Image(R.drawable.f);
        Article f = new Article("6" , "The impact of nature", "blablabla", "bblablabla", imgf);
        myArticles.add(f);
        //myAdapter = new ArrayAdapter<Adapter>(this, android.R.layout.simple_list_item_1);
        myAdapter = new NewsAdapter(this, myArticles);
        recyclerView.setAdapter(myAdapter);



    }


}