package com.example.duclinh1610.quanlithuvien;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.duclinh1610.adapters.Banner_Adapter;
import com.example.duclinh1610.muon_tra.MuonSach;
import com.example.duclinh1610.muon_tra.TraSach;
import com.example.duclinh1610.nguoidung.NguoiDungActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent i;
    private ViewPager viewPager1,viewPager2;
    private int[] arrImage = {R.raw.chuanhan, R.raw.giaitich1,R.raw.harrypotter,R.raw.hoahoc,R.raw.laptrinh,
    R.raw.lichsu, R.raw.tiengnhat,R.raw.toancaocap, R.raw.truyen, R.raw.vatly};
    Banner_Adapter banner_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager1 = (ViewPager) findViewById(R.id.viewPager1);
        viewPager2 = (ViewPager) findViewById(R.id.viewPager2);

        banner_adapter = new Banner_Adapter(MainActivity.this,arrImage);
        viewPager1.setAdapter(banner_adapter);
        viewPager2.setAdapter(banner_adapter);
        viewPager1.setCurrentItem(0);
        viewPager2.setCurrentItem(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_book) {
            i = new Intent(MainActivity.this,SachActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_people) {
            i = new Intent(MainActivity.this, NguoiDungActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_muon) {
            i =  new Intent(MainActivity.this, MuonSach.class);
            startActivity(i);
        } else if (id == R.id.nav_tra) {
            i =  new Intent(MainActivity.this, TraSach.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
