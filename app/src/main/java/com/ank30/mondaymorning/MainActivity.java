package com.ank30.mondaymorning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static boolean loadImage, loginStatus;
    public static String username;
    TabLayout firstTabLayout;
    ViewPager firstViewPager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("MMPrefs", Context.MODE_PRIVATE);
        loadImage = sharedPreferences.getBoolean("loadImage", true);
        loginStatus = sharedPreferences.getBoolean("loginStatus", false);

        if (loginStatus) {
            username = sharedPreferences.getString("username", "MM");
            //ImageView navImage = findViewById(R.id.navImageView);
            //Glide
            //        .with(getApplicationContext())
            //        .load("url")
            //        .into(navImage);
            //Button navLoginButton = findViewById(R.id.navButtonLogin);
            //navLoginButton.setText(username);
        }

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        firstTabLayout = findViewById(R.id.toolbarTabLayout);
        firstTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        firstTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (Build.VERSION.SDK_INT >= 21) {
                    if (firstTabLayout.getSelectedTabPosition() == 0) {
                        toolbar.setBackgroundColor(getResources().getColor(R.color.tab0Color));
                        getWindow().setNavigationBarColor(getResources().getColor(R.color.tab0Color));
                        getWindow().setStatusBarColor(getResources().getColor(R.color.tab0Color));
                        Log.i("tabSelected", Integer.toString(0));
                    } else if (firstTabLayout.getSelectedTabPosition() == 1) {
                        toolbar.setBackgroundColor(getResources().getColor(R.color.tab1Color));
                        getWindow().setNavigationBarColor(getResources().getColor(R.color.tab1Color));
                        getWindow().setStatusBarColor(getResources().getColor(R.color.tab1Color));
                        Log.i("tabSelected", Integer.toString(1));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        firstViewPager = findViewById(R.id.mainViewPager);
        setupMainPager(firstViewPager);

        firstTabLayout.setupWithViewPager(firstViewPager);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                if (Build.VERSION.SDK_INT >= 21) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                if (Build.VERSION.SDK_INT >= 21) {
                    if (firstTabLayout.getSelectedTabPosition() == 0) {
                        getWindow().setStatusBarColor(getResources().getColor(R.color.tab0Color));
                    } else if (firstTabLayout.getSelectedTabPosition() == 1) {
                        getWindow().setStatusBarColor(getResources().getColor(R.color.tab1Color));
                    }
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void setupMainPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ThisWeekFragment(), " ");
        viewPagerAdapter.addFragment(new CategoriesFragment(), " ");
        viewPager.setAdapter(viewPagerAdapter);
    }

    public void loginButtonPressed(View view) {
        if (!loginStatus) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent;

        if (id == R.id.nav_favorites) {
            intent = new Intent(getApplicationContext(), Favorites.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            intent = new Intent(getApplicationContext(), Settings.class);
            startActivity(intent);
        } else if (id == R.id.nav_healthEmergency) {
            intent = new Intent(getApplicationContext(), HealthCenter.class);
            startActivity(intent);
        } else if (id == R.id.nav_hallOfResidence) {
            intent = new Intent(getApplicationContext(), HallOfResidence.class);
            startActivity(intent);
        } else if (id == R.id.nav_studentActivityCenter){
            intent = new Intent(getApplicationContext(), StudentActivityCentre.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
