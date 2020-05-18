package com.example.newsmanagerproject.model;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import com.example.newsmanagerproject.LoadArticlesTask;
import com.example.newsmanagerproject.Login;
import com.example.newsmanagerproject.R;
import com.example.newsmanagerproject.database.ArticleDB;
import com.example.newsmanagerproject.database.ArticleDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ListView recyclerView;
    private NewsAdapter myAdapter;
    private LoadArticlesTask loadArticlesTask;
    private FloatingActionButton loginButon;
    private List<Article> listRes=null;

    ArticleDatabaseHelper dbHelper=new ArticleDatabaseHelper(getBaseContext());

    private ArticleDB dbArticle;
    //Variables for sideBar
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    public static boolean isLogged = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //db conection
        dbArticle= new ArticleDB(this);

        //SideBar
        Toolbar toolbar = findViewById(R.id.toolbarPerfect);
        setSupportActionBar(toolbar);

        // DrawerLayOut get the xml object
        drawerLayout = findViewById(R.id.drawer_layout);

        //To set the menu in the sidebar
        navigationView = findViewById(R.id.nav_controller_view_tag);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Call the function to get the Article from server
        loadArticlesTask = new LoadArticlesTask(this);
        try {
            //add in db
            addInDb(loadArticlesTask.execute().get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Voy a ver la base de datos


        //Create adapater to display data in the user screen
        listRes = ArticleDB.loadAllMessages();
        //Get articles from DB
        //Convert list in serialize object
        ArrayList<Article> listSerialize = new ArrayList<>(listRes);
        //Send information to AllFragment
        getIntent().putExtra("listArticle",listSerialize);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new AllFragment()).commit();
            navigationView.setCheckedItem(R.id.category_all);
        }

        if (!isLogged) {
            Log.i("Tag", "No está logueado");
        }

        //BUTTONS Action
        Log.i("LoginButton","DESPUES del loginButton");
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.i("onNaviSelec","Antes");
        Fragment f= null;
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intentHome = new Intent(this, MainActivity.class);
                startActivity(intentHome);
                break;
            case R.id.nav_create:
                Intent intentAddArticle = new Intent(this, creatArticle.class);
                startActivity(intentAddArticle);
                break;
            case R.id.category_national:
                f=new NationalFragment();
                break;
            case R.id.category_economy:
                f=new EconomyFragment();
                break;
            case R.id.category_sports:
                f=new SportsFragment();
                break;
            case R.id.category_technology:
                f=new TechnologyFragment();
                break;
            case R.id.category_all:
                f=new AllFragment();
                break;
            case R.id.nav_logout:
                break;
        }
        if(f!=null){
            //Convert list in serialize object
            ArrayList<Article> listSerialize = new ArrayList<>(listRes);
            //Send information to AllFragment
            getIntent().putExtra("listArticle",listSerialize);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,f).commit();
            item.setChecked(true);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        Log.i("onNaviSelec","Despues");
        //To select item when is triggered
        return false;

    }

    //This method allow manage the actions whenever any menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("onOptionsItemSelected","Antes");
        switch (item.getItemId()) {
            case android.R.id.home:
                // Abrir menu
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        Log.i("onOptionsItemSelected","Después");
        return super.onOptionsItemSelected(item);
    }

    //Method that permit access to the NewArticle class
    public void goNewsArticle(View view, int position) {
        Intent intentNewsArticle = new Intent(this, NewsArticle.class);

        //To send article to NewsArticle
        intentNewsArticle.putExtra("Article", listRes.get(position));
        startActivity(intentNewsArticle);
    }

    @Override
    public void onBackPressed() {
        Log.i("onBackPressed","Antes");
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        Log.i("onBackPressed","Después");
    }

    public void addInDb(List<Article> art)
    {
        for(Article r:art)
            ArticleDB.saveNewMessage(r);
    }

}