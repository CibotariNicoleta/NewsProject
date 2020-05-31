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
import androidx.constraintlayout.widget.Group;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
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
import java.util.Observable;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Shared shared;
    private MyArticleModel model;

    public static FloatingActionButton loginButton;

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
        if (savedInstanceState == null) {
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

            loginButton = findViewById(R.id.loginButton);
            if (Shared.checkLogin) {
                loginButton.setVisibility(View.INVISIBLE);
//                MenuItem createItem = findViewById(R.id.nav_create);
//                MenuItem logOutItem = findViewById(R.id.nav_logout);
//                createItem.setVisible(true);
//                logOutItem.setVisible(true);
            }
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), Login.class);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment f = new Fragment();
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
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                //loginButon.setVisibility(View.VISIBLE);
//                NewsAdapter.deleteButton.setVisibility(View.GONE);
//                NewsAdapter.modifyButton.setVisibility(View.GONE);
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


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    protected void onStart() {
        super.onStart();
        shared = new Shared(getApplicationContext());
        //to check b is true or false

        shared.firstTime();
    }

}