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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.little_bird.movieapp.fragments.FragmentAdapter;
import com.little_bird.movieapp.model.AllCategory;
import com.little_bird.movieapp.model.CategoryItem;
import com.little_bird.movieapp.model.MainRecyclerAdapter;
import com.little_bird.movieapp.model.Slider;
import com.little_bird.movieapp.model.SliderAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    FragmentAdapter fragmentAdapter;
    TabLayout tableLayout, tabIndicator;

    BottomNavigationView bottomNavigation;

    List<Slider> mhomeslider, mTVslider,mKidSlider;
    ViewPager viewSlider;

    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView recyclerMain;

    List<AllCategory> mCategoryList;
    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getWindow().setFlags( WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        callPopUpSignIn();

        addBottomNav();
        addViewPager2();

        nestedScrollView = findViewById(R.id.nest_scroll);
        appBarLayout = findViewById(R.id.appbar);




        mhomeslider= new ArrayList<>();
        mhomeslider.add(new Slider(1,"Josee, khi nàng thơ yêu","https://scontent-hkg4-1.xx.fbcdn.net/v/t39.30808-6/257785106_3127306174258374_6756045239501824803_n.jpg?_nc_cat=100&ccb=1-5&_nc_sid=09cbfe&_nc_ohc=l1707jvP7FQAX-AEbof&_nc_ht=scontent-hkg4-1.xx&oh=0370d29f91f536d4df524836c30cbfd0&oe=61A99183",""));
        mhomeslider.add(new Slider(2,"Dáng hình âm thanh\"","https://scontent-hkg4-1.xx.fbcdn.net/v/t39.30808-6/257785106_3127306174258374_6756045239501824803_n.jpg?_nc_cat=100&ccb=1-5&_nc_sid=09cbfe&_nc_ohc=l1707jvP7FQAX-AEbof&_nc_ht=scontent-hkg4-1.xx&oh=0370d29f91f536d4df524836c30cbfd0&oe=61A99183",""));
        mhomeslider.add(new Slider(3,"","https://scontent-hkg4-1.xx.fbcdn.net/v/t39.30808-6/257785106_3127306174258374_6756045239501824803_n.jpg?_nc_cat=100&ccb=1-5&_nc_sid=09cbfe&_nc_ohc=l1707jvP7FQAX-AEbof&_nc_ht=scontent-hkg4-1.xx&oh=0370d29f91f536d4df524836c30cbfd0&oe=61A99183"," "));
        mhomeslider.add(new Slider(4,"","https://scontent-hkg4-1.xx.fbcdn.net/v/t39.30808-6/257785106_3127306174258374_6756045239501824803_n.jpg?_nc_cat=100&ccb=1-5&_nc_sid=09cbfe&_nc_ohc=l1707jvP7FQAX-AEbof&_nc_ht=scontent-hkg4-1.xx&oh=0370d29f91f536d4df524836c30cbfd0&oe=61A99183",""));


        mKidSlider = new ArrayList<>();
        mKidSlider.add(new Slider(1,"","http://photos.q00gle.com/storage/files/images-2021/images-movies/09/61a20d37bce0e.jpg","Josee, khi nàng thơ yêu"));


        mTVslider = new ArrayList<>();
        mTVslider.add(new Slider(1,"","https://scontent.fsgn5-4.fna.fbcdn.net/v/t39.30808-6/261331455_1255122574963155_7721175309323475817_n.jpg?_nc_cat=102&ccb=1-5&_nc_sid=0debeb&_nc_ohc=k_Yiyq_d-1YAX_sU9N8&_nc_ht=scontent.fsgn5-4.fna&oh=4803170afdcc02255e9641a5543f6a1f&oe=61A94F39","My school"));
        slider_img(mhomeslider);

        List<CategoryItem> mItemList = new ArrayList<>();
        mItemList.add(new CategoryItem(1,"Megaton-kyuu Musashi","https://s199.imacdn.com/vg/2021/11/27/7dc6fc3880890b18_82acbad826335120_11870216380152647756547.jpg ","https://scontent.cdninstagram.com/v/t42.27313-2/10000000_906899969962302_2093511736319333032_n.mp4?_nc_cat=107&vs=6bfda417c612d339&_nc_vs=HBksFQAYJEdJQ1dtQUFfY1k0YTBqZ0RBS2pTYkJEVnBRMGRickZxQUFBRhUAAsgBABUAGCRHSUNXbUFBZmdwbnNmNVVEQUVzRDBTNDl5ZVFTYnJGcUFBQUYVAgLIAQBLBogScHJvZ3Jlc3NpdmVfcmVjaXBlATENc3Vic2FtcGxlX2ZwcwAQdm1hZl9lbmFibGVfbnN1YgAgbWVhc3VyZV9vcmlnaW5hbF9yZXNvbHV0aW9uX3NzaW0AKGNvbXB1dGVfc3NpbV9vbmx5X2F0X29yaWdpbmFsX3Jlc29sdXRpb24AEWRpc2FibGVfcG9zdF9wdnFzABUAJQAcAAAmkIrju9alaRWQTigCQzMYC3Z0c19wcmV2aWV3HBdAl3ZXCj1wpBgnZGFzaF9nZW4zYmFzaWNfNXNlY2dvcF9ocTJfZnJhZ18yX3ZpZGVvEgAYGHZpZGVvcy52dHMuY2FsbGJhY2sucHJvZBkcFQAVoLYEACgSVklERU9fVklFV19SRVFVRVNUGw2IFW9lbV90YXJnZXRfZW5jb2RlX3RhZwZvZXBfaGQTb2VtX3JlcXVlc3RfdGltZV9tcw0xNjM4MzcwNTE1MTQ5DG9lbV9jZmdfcnVsZQd1bm11dGVkE29lbV9yb2lfcmVhY2hfY291bnQBMAxvZW1fcm9pX25vdGULcHJvZ3Jlc3NpdmURb2VtX3JvaV91c2VyX3RpZXIAHm9lbV9yb2lfcHJlZGljdGVkX3dhdGNoX3RpbWVfcwEwFm9lbV9yb2lfcmVjaXBlX2JlbmVmaXQFMC4wMDAlb2VtX3JvaV9zdGF0aWNfYmVuZWZpdF9jb3N0X2V2YWx1YXRvcgtwcm9ncmVzc2l2ZQxvZW1fdmlkZW9faWQPMjMxNTQ0NzA5MDYxNjgyEm9lbV92aWRlb19hc3NldF9pZA8yMzE1NDQ3MDU3MjgzNDkVb2VtX3ZpZGVvX3Jlc291cmNlX2lkDzIzMTU0NDcwMjM5NTAxNhxvZW1fc291cmNlX3ZpZGVvX2VuY29kaW5nX2lkDzQ0MTg2OTg1NzM4MzI4MyUCHBwcFfDmFxsBVQACGwFVAAIcFQIAAAAWgLq3AwAlxAEbB4gBcwMzNjACY2QKMjAyMS0xMi0wMQNyY2IBMANhcHAGVmlkZW9zAmN0GUNPTlRBSU5FRF9QT1NUX0FUVEFDSE1FTlQTb3JpZ2luYWxfZHVyYXRpb25fcwgxNTAxLjU2NgJ0cw9vZXBfcHJvZ3Jlc3NpdmUA&ccb=1-5&_nc_sid=41a7d5&efg=eyJ2ZW5jb2RlX3RhZyI6Im9lcF9oZCIsInNvdXJjZV9zZWdtZW50X2hhbmRsZSI6bnVsbH0%3D&_nc_ohc=dbxUrWiBvIQAX_SRMLj&_nc_ht=video-ams4-1.xx&edm=APRAPSkEAAAA&oh=9deac93901049c58046e49551cac9f69&oe=61A95D1B&_nc_rid=fb04ae64c36c42e"));
        mItemList.add(new CategoryItem(2,"Kimetsu no Yaiba","https://s199.imacdn.com/vg/2021/11/28/3f48618c1e3ddc5b_69d36fa5494bf6d2_3300916381180884118684.jpg ","https://scontent.cdninstagram.com/v/t66.36240-6/10000000_443422653843122_5098392433712180615_n.mp4?_nc_cat=108&ccb=1-5&_nc_sid=985c63&efg=eyJybHIiOjE1MDAsInJsYSI6NDA5NiwidmVuY29kZV90YWciOiJvZXBfaGQifQu00253Du00253D&_nc_ohc=snZV3pzEY-UAX92lE8p&rl=1500&vabr=961&_nc_ht=scontent-frt3-1.xx&edm=APRAPSkEAAAA&oh=878f780535eda5d45e06c3f32113d5db&oe=61A8CC68"));
        mItemList.add(new CategoryItem(3,"Lupin III"," https://s199.imacdn.com/vg/2021/12/01/2d9b212a36c9af68_24bf50c6356e1b96_2278216382935599118684.jpg","https://scontent.cdninstagram.com/v/t66.36240-6/10000000_115066230852810_840480315024773262_n.mp4?_nc_cat=101&ccb=1-5&_nc_sid=985c63&efg=eyJybHIiOjE1MDAsInJsYSI6NDA5NiwidmVuY29kZV90YWciOiJvZXBfaGQifQu00253Du00253D&_nc_ohc=-443j6y2990AX-d1MQD&_nc_oc=AQkIFQvv2FfbRpTOJhIP-rEFW0KVhwgXsHsTOFZqAh9b27XyyQDmNcLlq0tXJEeAr4E&rl=1500&vabr=682&_nc_ht=scontent-amt2-1.xx&edm=APRAPSkEAAAA&oh=29a50d30ed94747f9dce67f944157e35&oe=61AAB670"));

        List<CategoryItem> mItemList2 = new ArrayList<>();
        mItemList2.add(new CategoryItem(1,"Chào Alberto","https://s198.imacdn.com/ff/2021/11/15/be96f2951598a3bb_0d0573e37581b651_443501636969273416068.jpg","https://mehoathinh.com/chao-alberto"));
        mItemList2.add(new CategoryItem(2,"Arcane","https://s198.imacdn.com/ff/2021/11/07/6590694a10b53f56_36c202825bfc2fef_525541636280209216068.jpg","https://mehoathinh.com/arcane-lien-minh-huyen-thoai/tap-1-chao-mung-den-san-choi"));
        mItemList2.add(new CategoryItem(3,"Kính Song Thành","https://s198.imacdn.com/ff/2021/11/29/b631dd1ac1d6ce13_0f76a6af35ee7157_52721163816339843816.jpg","https://mehoathinh.com/kinh-song-thanh-2021-the-mirror-twin-cities/tap-1"));
        mItemList2.add(new CategoryItem(4,"Phàm Nhân Tu Tiên","https://s198.imacdn.com/ff/2021/11/14/1fd0c068ae17c86c_9e2c59bb15e9461b_351141636876960516068.jpg","https://mehoathinh.com/pham-nhan-tu-tien-ma-dao-tranh-phong"));

        List<CategoryItem> mItemList3 = new ArrayList<>();
        mItemList3.add(new CategoryItem(1,"","https://s199.imacdn.com/vg/2021/09/29/819d6a79cd97836d_6bc30ef29cbd18e5_173292163292978929674.jpg",""));
        mItemList3.add(new CategoryItem(2,"","https://s199.imacdn.com/vg/2021/06/22/ba2d1585b27d5133_34047b8f19e96ceb_3441016243409554118684.jpg",""));
        mItemList3.add(new CategoryItem(3,"","https://s199.imacdn.com/vg/2021/03/11/c844ebf7859817dc_b52043c81540fbde_121512161546266181.jpg",""));
        mItemList3.add(new CategoryItem(4,"","https://s199.imacdn.com/vg/2020/12/30/dcc15e6832075a28_5bfe69c7f0a61257_121289160932188889674.jpg",""));

        List<CategoryItem> mItemList4 = new ArrayList<>();
        mItemList4.add(new CategoryItem(1,"Re-Main","https://s199.imacdn.com/vg/2021/07/04/a5170068324adfe7_4faaa8d61c4e3eef_5606816253324504118684.jpg",""));
        mItemList4.add(new CategoryItem(2,"Chaos Head","https://s199.imacdn.com/vg/2015/05/chaos-head-large-1432285073.jpg",""));
        mItemList4.add(new CategoryItem(3,"Cao thủ tennis","https://s199.imacdn.com/vg/2019/09/08/25942ef8c724d6a0_2bd743ac75b3e569_2870615679311293185710.jpg",""));
        mItemList4.add(new CategoryItem(4,"Hồ lô biển","https://s199.imacdn.com/vg/2020/11/23/f5ac75132499e884_9476a17c3b2728c9_44862160611656619674.jpg",""));

        List<CategoryItem> mItemList5 = new ArrayList<>();
        mItemList5.add(new CategoryItem(1,"Boruto: Naruto Next Generation","https://s199.imacdn.com/vg/2021/05/13/e3c93e9e3ae4c4d1_9a3a2fe806b4ffc6_3775516209096674118684.jpg",""));
        mItemList5.add(new CategoryItem(2,"Naruto Shippuuden","https://s199.imacdn.com/vg/2016/07/01/7c868aa86063686d_d749742c8f567c0c_44135146734386613.jpg",""));
        mItemList5.add(new CategoryItem(3,"Conan the movie","\thttps://s199.imacdn.com/vg/2019/07/26/6c040a27770ec024_ded1b5147578648e_5019215641124505185710.jpg",""));
        mItemList5.add(new CategoryItem(4,"Takt Op.Destiny","\thttps://s199.imacdn.com/vg/2021/10/05/7c27d48b6b17bb4e_9bd8d90bbc5bffd4_4180416334505744118684.jpg",""));


        List<CategoryItem> mItemList6 = new ArrayList<>();
        mItemList6.add(new CategoryItem(1,"SpyxFamily","https://s199.imacdn.com/vg/2021/11/02/0ee7d821c8addef0_efd42581f43422d0_26161163587030799674.jpg",""));
        mItemList6.add(new CategoryItem(2,"Orient","https://s199.imacdn.com/vg/2021/10/23/c3bbf243adc1dddc_e3c0451c63eda601_38305163498231359674.jpg",""));
        mItemList6.add(new CategoryItem(3,"Super Crooks","https://s199.imacdn.com/vg/2021/11/25/9804ff507acbeaf2_1a3746430cf6d7e5_3740416378257066118684.jpg",""));
        mItemList6.add(new CategoryItem(4,"Arcane : Liên minh huyền thoại","https://s199.imacdn.com/vg/2021/11/08/aa2aa26d1a427ba4_dbec529c9e53151b_3800916363637754118684.jpg",""));

        mCategoryList = new ArrayList<>();
        mCategoryList.add(new AllCategory("TẬP MỚI NHẤT",1,mItemList));
        mCategoryList.add(new AllCategory("PHIM HOẠT HÌNH",2,mItemList2));
        mCategoryList.add(new AllCategory("BỘ SƯU TẬP",3,mItemList3));
        mCategoryList.add(new AllCategory("BỘ SƯU TẬP",4,mItemList4));
        mCategoryList.add(new AllCategory("BẢNG XẾP HẠNG",5,mItemList5));
        mCategoryList.add(new AllCategory("TẤT CẢ ANIME",6,mItemList6));
        SetRecycleMain(mCategoryList);

    }

    private void setNestedScrollDefault(){
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);
    }

    private void SetRecycleMain(List<AllCategory> mAllCategory){
        recyclerMain = findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerMain.setLayoutManager(layoutManager);

        mainRecyclerAdapter = new MainRecyclerAdapter(this,mAllCategory);
        recyclerMain.setAdapter(mainRecyclerAdapter);

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

//        tableLayout.addTab(tableLayout.newTab().setText("Anime"));
//        tableLayout.addTab(tableLayout.newTab().setText("BXH"));

        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tableLayout.getSelectedTabPosition()){
                    case 1:
                        setNestedScrollDefault();
                        slider_img(mTVslider);

                        return ;
                    case 2:
                        setNestedScrollDefault();
                        slider_img(mKidSlider);

                        return ;

                    default:
                        setNestedScrollDefault();
                        slider_img(mhomeslider);
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


    private void slider_img(List<Slider> mlistSlider){

        viewSlider = findViewById(R.id.slider);
        SliderAdapter adapter = new SliderAdapter(mlistSlider,this);
//        tabIndicator = findViewById(R.id.tabIndicator);

        viewSlider.setAdapter(adapter);
//        tabIndicator.setupWithViewPager(viewSlider);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new AutoSlider(),4000,6000);
//        tabIndicator.setupWithViewPager(viewSlider,true);

    }


    //search_bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_bar,menu);

        MenuItem item = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

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
}