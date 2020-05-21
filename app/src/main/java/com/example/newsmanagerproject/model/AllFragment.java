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

import com.example.newsmanagerproject.LoadArticlesTask;
import com.example.newsmanagerproject.Login;
import com.example.newsmanagerproject.R;
import com.example.newsmanagerproject.database.ArticleDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class AllFragment extends Fragment {

    private ArrayList<Article>arrayArticle;
    private List<Article> listRes;

    public AllFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root=inflater.inflate(R.layout.fragment_all,container,false);

        //Call loadArticleTask service
        LoadArticlesTask loadArticlesTask = new LoadArticlesTask(getContext());
        try {
            //add in db
            listRes= loadArticlesTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // This part will show a list of articles
        ListView recyclerView = root.findViewById(R.id.list_all);

        NewsAdapter myAdapter = new NewsAdapter(Objects.requireNonNull(getContext()), listRes);
        recyclerView.setAdapter(myAdapter);

         //This let us set every item clickable LUEGO DESCOMENTARTodo
        recyclerView.setClickable(true);


        FloatingActionButton loginButon = (FloatingActionButton) root.findViewById(R.id.loginButton);
        Log.i("LoginButton","Antes del loginButton");
        loginButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LoginButton","Antes de llamar al login");
                Intent intent = new Intent(getContext(), Login.class);
                startActivity(intent);
            }
        });

        return root;

    }

}
