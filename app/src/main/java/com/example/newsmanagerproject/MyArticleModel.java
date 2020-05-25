package com.example.newsmanagerproject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.newsmanagerproject.model.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyArticleModel extends ViewModel {
    private MutableLiveData<List<Article>> articles;
    private Observer observer; //Ponerlo en la UI

    public LiveData<List<Article>> getArticles() {
        if (articles == null) {
            articles = new MutableLiveData<>();
            articles.setValue(loadArticles());
        }
        return articles;
    }

    private List<Article> loadArticles() {
        List<Article> res = new ArrayList<>();
        LoadArticlesTask loadArticlesTask = new LoadArticlesTask();
        try {
            res = loadArticlesTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }
}
