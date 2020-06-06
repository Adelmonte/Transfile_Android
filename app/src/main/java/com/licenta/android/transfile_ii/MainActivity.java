package com.licenta.android.transfile_ii;

import android.Manifest;
import android.content.Context;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.licenta.android.transfile_ii.frontend.fragments.FileDown;
import com.licenta.android.transfile_ii.frontend.fragments.FileUp;
import com.licenta.android.transfile_ii.frontend.fragments.Home;
import com.licenta.android.transfile_ii.frontend.fragments.Settings;

import static android.support.design.widget.NavigationView.*;


public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {

    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.cererePermisiuni();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView nv = findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState==null) // cand rotim ecranul savedStaceInstance nu va fi null
        {
        // pentru a evita afisarea la rotirea ecranului a paginii de start
            // la rotirea ecranului fragmentul se distruge si se creaza altul pentru ecranul
            // in pozitia actuala
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                   new Home()).commit();
            nv.setCheckedItem(R.id.nav_home);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            // selecteaza si apeleaza constructorul clasei asociate butonului din NavDrawer
            case R.id.nav_home:
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Home()).commit();
                } break;

            case R.id.nav_file_rec:
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FileDown()).commit();
                } break;

            case R.id.nav_file_send:
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new FileUp()).commit();
                } break;

            case R.id.nav_settings:
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Settings()).commit();
                } break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true; // selecteaza intrarea in navigationDrawer
    }

    @Override
    //permite ca la apasarea butonului de intoarcere sa se inchida NavigationDrawer, ci nu aplicatia
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
            //             drawer.closeDrawer(GravityCompat.END); pentru partea dreapta
        }
        else
            super.onBackPressed();
    }

    /*
    Cere de la User, permisuni de cirire si scriere a memoriei interne a dispozitivului
     */
    private void cererePermisiuni()
    {
        Context myContext = getApplicationContext();
        int YourRequestCode = 1;
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    YourRequestCode);
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    YourRequestCode);
            //
        }
    }

}
