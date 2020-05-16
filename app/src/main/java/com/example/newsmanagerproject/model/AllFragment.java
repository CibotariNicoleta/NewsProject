package com.example.newsmanagerproject.model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newsmanagerproject.R;
import com.example.newsmanagerproject.database.ArticleDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AllFragment extends Fragment {

    private ListView recyclerView;
    private NewsAdapter myAdapter;
    private ArrayList<Article>arrayArticle;
    private List<Article> listRes;
    public AllFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root=inflater.inflate(R.layout.fragment_all,container,false);
        Intent articles= Objects.requireNonNull(getActivity()).getIntent();

        arrayArticle=(ArrayList<Article>)articles.getSerializableExtra("listArticle");

        Log.i("Article Recived", arrayArticle.get(0).getCategory());

        // This part will show a list of articles
          recyclerView = (ListView) root.findViewById(R.id.list);

        // Convert ArrayList to List of Articles
        listRes=arrayArticle.subList(0,arrayArticle.size()-1);

        myAdapter = new NewsAdapter(getContext(), listRes);
        recyclerView.setAdapter(myAdapter);

         //This let us set every item clickable LUEGO DESCOMENTARTodo
        recyclerView.setClickable(true);

        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //When we clicked any  item of the list view. This action will ocurre
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Snackbar.make(view, "Element clicked -> " + position, Snackbar.LENGTH_LONG).show();
//                Log.i("Click", "click en el elemento " + position + " de mi ListView");
                //goNewsArticle(view, position);
            }
        });
        return root;

    }


    public void addInDb(List<Article> art)
    {
        for(Article r:art)
            ArticleDB.saveNewMessage(r);
    }

}
