package com.little_bird.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.little_bird.movieapp.adapter.FragmentAdapter;
import com.little_bird.movieapp.adapter.ItemRecyclerAdapter;
import com.little_bird.movieapp.model.AllCategory;
import com.little_bird.movieapp.model.CategoryItem;
import com.little_bird.movieapp.adapter.MainRecyclerAdapter;
import com.little_bird.movieapp.model.ListOfMovie;
import com.little_bird.movieapp.model.MovieResults;
import com.little_bird.movieapp.model.Result;
import com.little_bird.movieapp.model.Slider;
import com.little_bird.movieapp.adapter.SliderAdapter;
import com.little_bird.movieapp.retrofit.Client;
import com.little_bird.movieapp.retrofit.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    FragmentAdapter fragmentAdapter;
    TabLayout tableLayout;

    BottomNavigationView bottomNavigation;

    List<Result> mhomeslider, mTVslider,mKidSlider;
    ViewPager viewSlider;

    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView recyclerMain;

    List<AllCategory> mCategoryList;
    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;


    List<MovieResults> listOfMovies = new ArrayList<>();

    String API_KEY ="cbd41d05314adcd66369942fe63ab586";

//    public static int PAGE=1;
    static String CATEGORY="popular";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_main);


        recyclerMain = findViewById(R.id.main_recycler);

        nestedScrollView = findViewById(R.id.nest_scroll);
        appBarLayout = findViewById(R.id.appbar);
        tableLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        bottomNavigation = findViewById(R.id.bottom_nav);
        viewSlider = findViewById(R.id.slider);


        mhomeslider= new ArrayList<>();
        mKidSlider = new ArrayList<>();
        mTVslider = new ArrayList<>();

//        loadJson();

        List<CategoryItem> mItemList = new ArrayList<>();


        List<CategoryItem> mItemList2 = new ArrayList<>();

//        List<CategoryItem> mItemList3 = new ArrayList<>();
//        mItemList3.add(new CategoryItem(1,"","https://s199.imacdn.com/vg/2021/09/29/819d6a79cd97836d_6bc30ef29cbd18e5_173292163292978929674.jpg",""));

//        List<CategoryItem> mItemList4 = new ArrayList<>();
//        mItemList4.add(new CategoryItem(1,"Re-Main","https://s199.imacdn.com/vg/2021/07/04/a5170068324adfe7_4faaa8d61c4e3eef_5606816253324504118684.jpg",""));

//        List<CategoryItem> mItemList5 = new ArrayList<>();
//        mItemList5.add(new CategoryItem(1,"Boruto: Naruto Next Generation","https://s199.imacdn.com/vg/2021/05/13/e3c93e9e3ae4c4d1_9a3a2fe806b4ffc6_3775516209096674118684.jpg",""));

//
//        List<CategoryItem> mItemList6 = new ArrayList<>();
//        mItemList6.add(new CategoryItem(1,"SpyxFamily","https://s199.imacdn.com/vg/2021/11/02/0ee7d821c8addef0_efd42581f43422d0_26161163587030799674.jpg",""));
//
        mCategoryList = new ArrayList<>();

//        mCategoryList.add(new AllCategory("TẤT CẢ ANIME",7,mItemList6));




//        SetRecycleMain(listOfMovies);
        callPopUpSignIn();

        addBottomNav();
        addViewPager2();
        loadJson("popular");


       loadMainRecyclerView();

    }

    private void setNestedScrollDefault(){
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);
    }


    //TITLE AND ITEM OF CATEGORY
    private void SetRecycleMain(List<MovieResults> mAllCategory){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerMain.setLayoutManager(layoutManager);

        mainRecyclerAdapter = new MainRecyclerAdapter(this,mAllCategory);
        recyclerMain.setAdapter(mainRecyclerAdapter);

    }

    // POP UP FOR SIGN UP ACCOUNT -> USER CAN CHOOSE
    private void callPopUpSignIn (){
        Dialog mdialog = new Dialog(this);
        mdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mdialog.setContentView(R.layout.pop_up_sign_in);
        mdialog.setCancelable(true);


        Button btnContinue = mdialog.findViewById(R.id.btnContinue);
        EditText edtNumber = mdialog.findViewById(R.id.edtNumber);
        ImageButton btnClose = mdialog.findViewById(R.id.btnClose);


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

//        FragmentManager fm = getSupportFragmentManager();
//        fragmentAdapter = new FragmentAdapter(fm,getLifecycle());


        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tableLayout.getSelectedTabPosition()){
                    case 1:
                        setNestedScrollDefault();
                        loadJson("now_playing");


                        return ;
                    case 2:
                        setNestedScrollDefault();
                        loadJson("top_rated");

                        return ;

                    default:
                        setNestedScrollDefault();
                        loadJson("popular");

                }
//                viewPager2.setCurrentItem(tab.getPosition());
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


        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        MainActivity.super.onBackPressed();
                        return true;

//                    case R.id.search_bar:
//                        new Intent(getApplicationContext(),Search_Movie.class);
//                        return true;

//                    case R.id.user:
//                        startActivity( new Intent(getApplicationContext(),SetPassword.class));
//
//                        return true;

                    case R.id.settings:

                        startActivity( new Intent(getApplicationContext(),Search_Movie.class));
                        return true;
                }

                return false;
            }
        });
    }


    private void slider_img(List<Result> mlistSlider){


        SliderAdapter adapter = new SliderAdapter(mlistSlider,this);
//        tabIndicator = findViewById(R.id.tabIndicator);

        viewSlider.setAdapter(adapter);
//        tabIndicator.setupWithViewPager(viewSlider);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new AutoSlider(),4000,6000);
//        tabIndicator.setupWithViewPager(viewSlider,true);

    }


    //search_bar


    class AutoSlider extends TimerTask{

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewSlider.getCurrentItem() < mhomeslider.size() -1){
                        viewSlider.setCurrentItem(viewSlider.getCurrentItem()+1);
                    }
                    else {
                        viewSlider.setCurrentItem(0);
                    }
                }
            });
        }
    }


    public void loadJson(String category){

        Client client = new Client();
        Service service = client.getclient().create(Service.class);

        Call<MovieResults> call = service.getAllSlider(category,API_KEY);
        Log.d("check_link",call.toString());



        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results= response.body();
                List<Result> movieList = results.getResults();
                slider_img(movieList);

            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }
        });


    }

    public void loadMainRecyclerView(){
        Client client = new Client();
        Service service = client.getclient().create(Service.class);

        Call<MovieResults> call = service.getAllSlider("top_rated",API_KEY);

        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<Result> list = results.getResults();
                listOfMovies.add(new MovieResults("top_rated",results.getPage(),list,10,100));
                SetRecycleMain(listOfMovies);

            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }
        });

        Call<MovieResults> call1 = service.getAllSlider("now_playing",API_KEY);

        call1.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<Result> list = results.getResults();
                listOfMovies.add(new MovieResults("now_playing",1,list,10,100));
                SetRecycleMain(listOfMovies);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }
        });

        Call<MovieResults> call2 = service.getAllSlider("popular",API_KEY);

        call2.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<Result> list = results.getResults();
                listOfMovies.add(new MovieResults("popular",1,list,10,100));
                SetRecycleMain(listOfMovies);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }
        });

        Call<MovieResults> call3 = service.getAllSlider("upcoming",API_KEY);

        call3.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults results = response.body();
                List<Result> list = results.getResults();
                listOfMovies.add(new MovieResults("upcoming",1,list,10,100));
                SetRecycleMain(listOfMovies);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {

            }
        });



    }


}