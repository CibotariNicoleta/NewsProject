package com.example.newsmanagerproject.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.newsmanagerproject.LoadArticlesTask;
import com.example.newsmanagerproject.Login;
import com.example.newsmanagerproject.MyArticleModel;
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

    private ArrayList<Article> arrayArticle;
    private List<Article> listRes;
    private MyArticleModel model;
    private Observer observer;
    public Handler mhandler;

    public View footerView;
    public ListView recyclerView;
    public NewsAdapter myAdapter;
    public boolean isLoading = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_all, container, false);

        //Load articles from Database
        ArticleDB.resetOffset();
        //Call loadArticleTask service
        listRes = new ArrayList<>(ArticleDB.getArticles());

        // This part will show a list of articles
        recyclerView = root.findViewById(R.id.list_all);

        //Set the footer
        LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerView = li.inflate(R.layout.footer_view, null);

        //Create handler
        mhandler = new MyHandler();

        myAdapter = new NewsAdapter(Objects.requireNonNull(getContext()),listRes);
        recyclerView.setAdapter(myAdapter);

        //This let us set every item clickable LUEGO DESCOMENTARTodo
        recyclerView.setClickable(true);

        recyclerView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view.getLastVisiblePosition() == totalItemCount - 1 && recyclerView.getCount() >= 10 && !isLoading) {
                    isLoading = true;
                    Thread thread = new ThreadGetMoreArticles();
                    thread.start();
                }
            }
        });

        return root;

    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    recyclerView.addFooterView(footerView);
                    break;
                case 1:
                    //Update data adapater and UI
                    myAdapter.addArticlesList((List<Article>) msg.obj);
                    //To remove footer View
                    recyclerView.removeFooterView(footerView);
                    isLoading = false;
                    break;
                default:
                    break;
            }
        }
    }

    public class ThreadGetMoreArticles extends Thread {
        @Override
        public void run() {
            //Add footer view
            mhandler.sendEmptyMessage(0);
            //Look for more data
            List<Article> getList = new ArrayList<Article>(ArticleDB.getArticles());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Sending the result
            Message msgRes = mhandler.obtainMessage(1, getList);
            mhandler.sendMessage(msgRes);

        }
    }
}
