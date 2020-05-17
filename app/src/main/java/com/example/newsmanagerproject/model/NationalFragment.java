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

import com.example.newsmanagerproject.Login;
import com.example.newsmanagerproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NationalFragment extends Fragment {

    private ArrayList<Article> arrayArticle;
    private ListView recyclerView;
    private List<Article> listRes;
    private NewsAdapter myAdapter;
    private FloatingActionButton loginButon;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_national,container,false);

        Intent articles= Objects.requireNonNull(getActivity()).getIntent();
        arrayArticle=(ArrayList<Article>)articles.getSerializableExtra("listArticle");

        Log.i("Article Recived", arrayArticle.get(0).getCategory());

        // This part will show a list of articles
        recyclerView = root.findViewById(R.id.list_national);

        // Convert ArrayList to List of Articles
        listRes=arrayArticle.subList(0,arrayArticle.size()-1);

        myAdapter = new NewsAdapter(getContext(), getListFilter(listRes));
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

    private List<Article> getListFilter(List<Article> pre){
        List<Article> res=new ArrayList<Article>();
        for (int i=0; i<pre.size();i++){
            Article addArticle=pre.get(i);
            if(addArticle.getCategory().equals("National")){
                res.add(addArticle);
            }
        }
        return res;
    }

}
