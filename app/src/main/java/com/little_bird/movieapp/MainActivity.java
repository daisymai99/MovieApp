package com.little_bird.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.little_bird.movieapp.fragments.FragmentAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    FragmentAdapter fragmentAdapter;
    TabLayout tableLayout;

    BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        callPopUpSignIn();

        addBottomNav();
        addViewPager2();
    }

    private void callPopUpSignIn (){
        Dialog mdialog = new Dialog(this);
        mdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mdialog.setContentView(R.layout.pop_up_sign_in);
        mdialog.setCancelable(true);


        Button btnContinue = mdialog.findViewById(R.id.btnContinue);
        EditText edtNumber = mdialog.findViewById(R.id.edtNumber);
        ImageButton btnClose =(ImageButton) mdialog.findViewById(R.id.btnClose);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.dismiss();
            }
        });
//
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"dialog",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Sms.class).putExtra("User_Number",edtNumber.getText().toString().trim()));
            }
        });
        mdialog.show();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mdialog.dismiss();
            }
        }, 10000);
    }


    private void addViewPager2(){
        tableLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);

        FragmentManager fm = getSupportFragmentManager();
        fragmentAdapter = new FragmentAdapter(fm,getLifecycle());

        tableLayout.addTab(tableLayout.newTab().setText("Anime"));
        tableLayout.addTab(tableLayout.newTab().setText("BXH"));

        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tableLayout.selectTab(tableLayout.getTabAt(position));
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
    }

    private void addBottomNav(){
        bottomNavigation = findViewById(R.id.bottom_nav);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        MainActivity.super.onBackPressed();
                        return true;
                    case R.id.user:
                        new Intent(getApplicationContext(),SetPassword.class);
                        return true;

                    case R.id.settings:

                        new Intent(getApplicationContext(),Sms.class);
                        return true;
                }

                return false;
            }
        });
    }



}