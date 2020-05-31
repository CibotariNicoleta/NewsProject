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
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MyArticleModel {
    private static List<Article> articles;

    /**
     * getArticles(): This method initialize list articles data to
     * visualize in the view components
     */

    public List<Article> getArticles() {
        if (articles == null) {
            articles = new ArrayList<Article>();
            loadArticles();
        }
        return articles;
    }

    /**
     * loadArticles(): This method calls getArticles of DB to
     * get articles in 2 ways. From the article server or from article DB.
     */
    private void loadArticles() {
        List<Article> res = new ArrayList<Article>();
        List<Article> aux = new ArrayList<Article>(ArticleDB.getArticles());
        Iterator<Article> iterator = aux.iterator();
        while (iterator.hasNext()) {
            res.add((Article) iterator.next());
        }
        articles.addAll(res);
    }

    /**
     * getMoreArticles(): This method load more articles from DB or
     * article server and add those elements to articles "atribute"
     */
    public void getMoreArticles() {
        List<Article> aux = new ArrayList<Article>(ArticleDB.getArticles());
        List<Article> res = new ArrayList<>(Objects.requireNonNull(articles));
        res.addAll(aux);
        articles.addAll(res);

    }
}
