package com.example.newsmanagerproject.model;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newsmanagerproject.R;
import com.example.newsmanagerproject.database.ArticleDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class EconomyFragment extends Fragment {

    private List<Article> listRes;
    private ListView listView;
    private FloatingActionButton loginButon;
    private NewsAdapter economyAdapter;

    public View footerView;
    public Handler mhandler;
    public boolean isLoading = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_economy,container,false);

        ArticleDB.resetOffset();
        listRes = ArticleDB.loadArticles();


        // This part will show a list of articles
        listView = root.findViewById(R.id.list_economy);
        return root;
    }
}
