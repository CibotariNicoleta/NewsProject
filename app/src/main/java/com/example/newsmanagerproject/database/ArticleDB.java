package com.example.newsmanagerproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.newsmanagerproject.model.Article;
import com.example.newsmanagerproject.model.Logger;
import com.example.newsmanagerproject.network.errors.ServerComnmunicationError;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleDB {

    private static ArticleDatabaseHelper helper;

    public ArticleDB(Context c){
        helper = new ArticleDatabaseHelper(c);
    }
    public static void saveNewMessage(Article m){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.DB_TABLE_FIELD_ARTICLE_ID, m.getId());
        values.put(DatabaseConstants.DB_TABLE_FIELD_ARTICLE_IDUSER,m.getIdUser());
        values.put(DatabaseConstants.DB_TABLE_FIELD_ARTICLE_TITLE,m.getTitleText());
        values.put(DatabaseConstants.DB_TABLE_FIELD_ARTICLE_CATEGORY,m.getCategory());
        values.put(DatabaseConstants.DB_TABLE_FIELD_ARTICLE_ABSTRACT,m.getAbstractText());
        values.put(DatabaseConstants.DB_TABLE_FIELD_ARTICLE_BODY,m.getBodyText());
        values.put(DatabaseConstants.DB_TABLE_FIELD_ARTICLE_SUBTITLE,m.getSubtitleText());

        try {
            values.put(DatabaseConstants.DB_TABLE_FIELD_ARTICLE_IMAGEDESCRIPTION,m.getImage().getDescription());
        } catch (ServerComnmunicationError serverComnmunicationError) {
            serverComnmunicationError.printStackTrace();
        }

        values.put(DatabaseConstants.DB_TABLE_FIELD_ARTICLE_THUMBNAIL,m.getThumbnail());
        values.put(DatabaseConstants.DB_TABLE_FIELD_ARTICLE_LASTUPDATE,m.getLastUpdate().getTime());
        try {
            values.put(DatabaseConstants.DB_TABLE_FIELD_ARTICLE_IMAGEDATA,m.getImage().getIdArticle());
        } catch (ServerComnmunicationError serverComnmunicationError) {
            serverComnmunicationError.printStackTrace();
        }
        long insertId = db.insert(DatabaseConstants.DB_TABLE_ARTICLE_NAME, null, values);
        Logger.log (Logger.INFO, insertId + " objects (Article) retrieved");


    }


    public static List<Article> loadAllMessages(){
        ArrayList<Article> result = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseConstants.DB_TABLE_ARTICLE_NAME,
                null,null,null,null,
                null,DatabaseConstants.DB_TABLE_FIELD_ARTICLE_LASTUPDATE);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Article article = new Article();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            int id = cursor.getInt(0);
            int idUser = cursor.getInt(1);
            String title = cursor.getString(2);
            String category = cursor.getString(3);
            String abst = cursor.getString(4);
            String body = cursor.getString(5);
            String subtitle = cursor.getString(6);
            String ImageDescription = cursor.getString(7);
            String thumbnail = cursor.getString(8);
            String lasupdate = cursor.getString(9);
            String imageData = cursor.getString(10);

            try {

              Date date = formatter.parse(lasupdate);
              article.setLastUpdate(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            article.setId(id);
            article.setIdUser(idUser);
            article.setTitleText(title);
            article.setCategory(category);
            article.setAbstractText(abst);
            article.setBodyText(body);
            article.setSubtitle(subtitle);
            article.setImageDescription(ImageDescription);
            article.setThumbnail(thumbnail);
            article.setImageData(imageData);
            result.add(article);
            cursor.moveToNext();
        }

        return result;
    }
}

