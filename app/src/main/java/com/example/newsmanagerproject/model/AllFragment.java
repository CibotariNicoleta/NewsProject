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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class AllFragment extends Fragment {

    private ListView recyclerView;
    private NewsAdapter myAdapter;
    private ArrayList<Article>arrayArticle;
    private List<Article> listRes;
    private FloatingActionButton loginButon;
    private LoadArticlesTask loadArticlesTask;
    public AllFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root=inflater.inflate(R.layout.fragment_all,container,false);
        Intent articles= Objects.requireNonNull(getActivity()).getIntent();

        //Tenemos que realizar llamadas al servicio loadTask
        loadArticlesTask = new LoadArticlesTask(getContext());
        try {
            //add in db
            listRes=loadArticlesTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // This part will show a list of articles
        recyclerView = (ListView) root.findViewById(R.id.list_all);

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

        loginButon = (FloatingActionButton) root.findViewById(R.id.loginButton);
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
