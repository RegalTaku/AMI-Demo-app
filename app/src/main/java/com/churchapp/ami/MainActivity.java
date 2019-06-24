package com.churchapp.ami;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.churchapp.ami.menufragments.Connect;
import com.churchapp.ami.menufragments.Contact;
import com.churchapp.ami.menufragments.EventsFragment;
import com.churchapp.ami.menufragments.GalleryFragment;
import com.churchapp.ami.menufragments.HomeCells;
import com.churchapp.ami.menufragments.HomeFragment;
import com.churchapp.ami.menufragments.MinistriesFragment;
import com.churchapp.ami.menufragments.Videos;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.contact_developer) {
            Intent intent = new Intent(MainActivity.this, Developer.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.facebook){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/alphlukau01/"));
            startActivity(intent);
            return true;
        }else if(id == R.id.twitter){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/AlphLukau"));
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.home) {
            fragment = new HomeFragment();

        } else if (id == R.id.gallery) {
            fragment = new GalleryFragment();

        } else if (id == R.id.ministries) {
            fragment = new MinistriesFragment();

        } else if (id == R.id.homeCells) {
            fragment = new HomeCells();

        } else if (id == R.id.events) {
            fragment = new EventsFragment();

        } else if (id == R.id.connect){
            fragment = new Connect();

        } else if (id == R.id.contact){
            fragment = new Contact();

        } else if(id == R.id.videos){
            fragment = new Videos();
        }

        if( fragment != null ){
           effectContent(fragment);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void effectContent(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentArea, fragment);
        fragmentTransaction.commit();
    }

}
