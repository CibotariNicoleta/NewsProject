package com.example.newsmanagerproject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.newsmanagerproject.database.ArticleDB;
import com.example.newsmanagerproject.model.Article;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyArticleModel extends ViewModel {
    private MutableLiveData<List<Article>> articles = new MutableLiveData<>();

    public LiveData<List<Article>> getArticles() {
        loadArticles();
        return articles;
    }

    private void loadArticles() {
        List<Article> res = new ArrayList<Article>();
        List<Article> aux = new ArrayList<Article>(ArticleDB.getArticles());
        Iterator<Article> iterator = aux.iterator();
        while (iterator.hasNext()) {
            res.add((Article) iterator.next());
        }
        articles.setValue(res);
    }
}
