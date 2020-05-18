package com.example.newsmanagerproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

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


        ContentValues values = new ContentValues();
        SQLiteDatabase dbd = helper.getReadableDatabase();
        Cursor cursor = dbd.query(DatabaseConstants.DB_TABLE_ARTICLE_NAME,
                null,null,null,null,
                null,DatabaseConstants.DB_TABLE_FIELD_ARTICLE_LASTUPDATE);
        cursor.moveToFirst();
        int id_test=0;
        while (!cursor.isAfterLast())
        {
            int id = cursor.getInt(0);
            if((id == m.getId()) && (cursor.getInt(1) == m.getIdUser()) )
                id_test = 1;

            cursor.moveToNext();
        }
        if(id_test == 0){
            Logger.log (Logger.INFO, "uuu" + " --------- >>>>>>>(Article) retrieved");
            SQLiteDatabase db = helper.getWritableDatabase();
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

        Logger.log (Logger.INFO, "saveeee" + " --------- >>>>>>>(Article) retrieved"); }

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
            Double lasupdate = cursor.getDouble(9);
            String imageData = cursor.getString(10);


            long myLong = System.currentTimeMillis() + ((long) (lasupdate * 1000));
            Date itemDate = new Date(myLong);
            article.setLastUpdate(itemDate);
       /*     try {

              Date date = formatter.parse(lasupdate);
              article.setLastUpdate(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }*/

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
            Logger.log (Logger.INFO, id + " --------- >>>>>>>(Article) retrieved");

        }

        return result;
    }
    public static int getLength(){
        int res;
        SQLiteDatabase dbd = helper.getReadableDatabase();
        res=(int) DatabaseUtils.queryNumEntries(dbd,"Article_DB");
        return res;
    }
}

