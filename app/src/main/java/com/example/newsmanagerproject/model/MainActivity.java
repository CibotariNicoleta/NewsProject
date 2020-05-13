package com.example.newsmanagerproject.model;

import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.example.newsmanagerproject.LoadArticlesTask;
import com.example.newsmanagerproject.Login;
import com.example.newsmanagerproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private ListView recyclerView;
    private NewsAdapter myAdapter;
    private LoadArticlesTask loadArticlesTask;
    private FloatingActionButton loginButon;
    private List<Article> listRes;


    //Variables for sideBar
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    public static boolean isLogged=false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SideBar
        Toolbar toolbar=findViewById(R.id.toolbarPerfect);
        setSupportActionBar(toolbar);

        // DrawerLayOut
        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_controller_view_tag);



        if(!isLogged){
            Log.i("Tag","No está logueado");
        }

        //Displays the menu actions
        navigationView.setNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Intent intentHome = new Intent(this, MainActivity.class);
                    startActivity(intentHome);
                    break;
                case R.id.nav_create:
                    Intent intentAddArticle = new Intent(this, creatArticle.class);
                    startActivity(intentAddArticle);
                    break;
                case R.id.nav_logout:
                    /// WE HAVE TO IMPLEMENT LOGOUT FUNCTION
                    break;
            }
            return false;
        });

        // This part will show a list of articles
        recyclerView =  findViewById(R.id.list);

        //Call the function to get the Article from server
        loadArticlesTask = new LoadArticlesTask(this);
        listRes = null;
        try {
            listRes = loadArticlesTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Create adapater to display data in the user screen
        myAdapter = new NewsAdapter(this, listRes);
        recyclerView.setAdapter(myAdapter);

        // This let us set every item clickable
        recyclerView.setClickable(true);
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //When we clicked any  item of the list view. This action will ocurre
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Snackbar.make(view, "Element clicked -> " + position, Snackbar.LENGTH_LONG).show();
//                Log.i("Click", "click en el elemento " + position + " de mi ListView");
                goNewsArticle(view, position);
            }
        });

        //BUTTONS Action
        loginButon = (FloatingActionButton) findViewById(R.id.loginButton);
        loginButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    //This method allow manage the actions whenever any menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Abrir menu
                drawerLayout.openDrawer(GravityCompat.START);
                return false;
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
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
}