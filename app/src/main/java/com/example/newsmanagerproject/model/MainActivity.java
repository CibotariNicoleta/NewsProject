package com.example.newsmanagerproject.model;

import android.content.Intent;
import android.graphics.Color;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;


import com.example.newsmanagerproject.LoadArticlesTask;
import com.example.newsmanagerproject.Login;
import com.example.newsmanagerproject.MyArticleModel;
import com.example.newsmanagerproject.R;
import com.example.newsmanagerproject.database.ArticleDB;
import com.example.newsmanagerproject.database.ArticleDatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ListView recyclerView;
    private NewsAdapter myAdapter;
    private LoadArticlesTask loadArticlesTask;
    private FloatingActionButton loginButon;
    private Shared shared;
    private List<Article> listRes=null;
    private MyArticleModel model;

    ArticleDatabaseHelper dbHelper = new ArticleDatabaseHelper(getBaseContext());

    private ArticleDB dbArticle;
    //Variables for sideBar
    DrawerLayout drawerLayout;
    NavigationView navigationView;

   // public static boolean isLogged = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create FragmentManager
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //db conection
        dbArticle = new ArticleDB(this);

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


        if (savedInstanceState == null) {
            transaction.add(R.id.fragment, new AllFragment()).commit();
            transaction.addToBackStack(null);
            navigationView.setCheckedItem(R.id.category_all);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.i("onNaviSelec", "Antes");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment f = new Fragment();
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intentHome = new Intent(this, MainActivity.class);
                startActivity(intentHome);
                break;
            case R.id.nav_create:
                if(Shared.checkLogin){
                Intent intentAddArticle = new Intent(this, creatArticle.class);
                startActivity(intentAddArticle); } else item.setVisible(false);
                break;
            case R.id.category_national:
                f = new NationalFragment();
                break;
            case R.id.category_economy:
                f = new EconomyFragment();
                break;
            case R.id.category_sports:
                f = new SportsFragment();
                break;
            case R.id.category_technology:
                f = new TechnologyFragment();
                break;
            case R.id.category_all:
                f = new AllFragment();
                break;
            case R.id.nav_logout:
                shared.getEditor().putBoolean("b", false).apply();
                shared.firstTime();
                Shared.checkLogin = false;

                MenuItem logout = (MenuItem) findViewById(R.id.nav_logout);
                item.setVisible(false);

                MenuItem create = (MenuItem) findViewById(R.id.nav_create);
                item.setVisible(false);

                //loginButon.setVisibility(View.VISIBLE);
                NewsAdapter.deleteButton.setVisibility(View.GONE);
                NewsAdapter.modifyButton.setVisibility(View.GONE);


                /// WE HAVE TO IMPLEMENT LOGOUT FUNCTION
                break;
        }
        if (f != null) {
            transaction.replace(R.id.fragment, f);
            transaction.addToBackStack(null);
            transaction.commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        //To select item when is triggered
        return false;

    }

    //This method allow manage the actions whenever any menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // Abrir menu
                finish();
                return true;
        }
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
        Log.i("onBackPressed", "Antes");
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        Log.i("onBackPressed", "Después");
    }


    protected void onStart() {
        super.onStart();
        shared = new Shared(getApplicationContext());
        //to check b is true or false

        shared.firstTime();
    }

}