package com.example.win81user.findhouse.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.win81user.findhouse.API.MyApi;
import com.example.win81user.findhouse.Adapter.ViewPagerAdapter;
import com.example.win81user.findhouse.Common.BaseActivity;
import com.example.win81user.findhouse.Constants.Constants;
import com.example.win81user.findhouse.Fragment.LoginFragment;
import com.example.win81user.findhouse.Fragment.PrimaryFragment;
import com.example.win81user.findhouse.Map.TestMap;
import com.example.win81user.findhouse.Model.ItemModel;
import com.example.win81user.findhouse.Model.Property;
import com.example.win81user.findhouse.R;
import com.example.win81user.findhouse.ShowFeed;
import com.example.win81user.findhouse.Utility.NextzyUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityDrawer extends BaseActivity implements Callback<ItemModel> {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    //    private FloatingActionButton fab;
    public static ViewPager viewPager;
    public static int int_items = 3 ;
    private int[] tabIcons = {
            R.drawable.cityscape,
            R.drawable.home,
            R.drawable.placeholder

    };
    private static final String urlNavHeaderBg ="http://192.168.25.2:8181/FindHouse/uploads/userimg/tom.png";
    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "movies";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;
    private MaterialSearchView searchView;
    Retrofit retrofit;
    private ItemModel itemModel;
    private ArrayList<Property> data;
    String API = "http://192.168.25.2:8181/FindHouse/webservice/";

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private SharedPreferences pref;
    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int USER_OPTIONS_ANIMATION_DELAY = 300;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());


        pref = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        Log.d("DDDDDDDDDDDD","DDDDDDDDDDDDdd"+"\t"+pref.getString(Constants.EMAIL,""+"\t"+pref));

        initdrawer();
        initialview();
//        preparesearch();
        loadNavHeader();
        startIntroAnimation();
        setupWindowAnimations();
        showUp();
        prepareservice();
        // initializing navigation menu
        setUpNavigationView();

        mHandler = new Handler();

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                tabLayout.getTabAt(2).setIcon(tabIcons[2]);

            }
        });


        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }


    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    private void setupWindowAnimations() {
        // Re-enter transition is executed when returning to this activity
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(500);
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    private void startIntroAnimation() {


        int actionbarSize = dpToPx(56);
        toolbar.setTranslationY(-actionbarSize);
      /*  getIvLogo().setTranslationY(-actionbarSize);
        getInboxMenuItem().getActionView().setTranslationY(-actionbarSize);
*/
        toolbar.animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)
     /*   getIvLogo().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(400);
        getInboxMenuItem().getActionView().animate()
                .translationY(0)
                .setDuration(ANIM_DURATION_TOOLBAR)
                .setStartDelay(500)*/
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }
                })
                .start();
    }

    private void initdrawer(){

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);


    }

    private void initialview(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(viewPager);
    }

/*    private void preparesearch(ArrayList<Property> dataa){
        Log.e("dataa","dataa"+dataa);
        String[] getsearchprop = new String[dataa.size()];

        RecyclerView rv= (RecyclerView) findViewById(R.id.recyclerView);
        for(int i=0;i<dataa.size();i++){
            getsearchprop[i] = dataa.get(i).getPropertyname();
        }
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setEllipsize(true);
        searchView.setSuggestions(getsearchprop);
        //SET ITS PROPETRIES
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        //ADAPTER
        final FeedAdapter adapter =new FeedAdapter(this,dataa);
        rv.setAdapter(adapter);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Snackbar.make(findViewById(R.id.containerq), "Query: " + query, Snackbar.LENGTH_LONG)
                        .show();
                //Do some magic
             *//*   Intent i = new Intent(ActivityDrawer.this, MapsActivity.class);
                startActivity(i);*//*
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic


//                Toast.makeText(getApplicationContext(), "Search!", Toast.LENGTH_LONG).show();
                adapter.getFilter().filter(newText);

                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });


    }*/
    private void loadNavHeader() {

        txtName.setText(pref.getString(Constants.NAME,""));
        txtWebsite.setText(pref.getString(Constants.EMAIL,""));
        Log.d("ppppppppppppp","ppppppppppp"+pref.getString(Constants.EMAIL,""));


        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);


        Glide.with(this).load(pref.getString(Constants.USERIMAGE,""))
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // showing dot next to notifications label
//        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }


    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }

/*        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.viewpager, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };


        }*/
        // show or hide the fab button
//        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:

                PrimaryFragment homeFragment = new PrimaryFragment();
                return homeFragment;
            case 1:
                // photos
//                SocialFragment photosFragment = new SocialFragment();
//                return photosFragment;

            default:
                return null;
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle("FindHouse");

    }
    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_item_inbox:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_item_sent:

                        startActivity(new Intent(ActivityDrawer.this, NearbyActivity.class));




                        drawer.closeDrawers();
                        break;
                    case R.id.action_notification:

                        startActivity(new Intent(ActivityDrawer.this, NotificationActivity.class));


                        drawer.closeDrawers();
                        break;
                    case R.id.logout:
                        logout();
                        drawer.closeDrawers();
                        break;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }
      /*  if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        }
*/
        super.onBackPressed();
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 1) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }
*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
//            searchView.setMenuItem(item);
        startActivity(new Intent(ActivityDrawer.this, NearbyActivity.class));
        }
        if(id == R.id.action_condo){
            startActivity(new Intent(ActivityDrawer.this, NearbyActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }



    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ShowFeed(), "");
        adapter.addFragment(new ShowFeed(), "");
        adapter.addFragment(new TestMap(), "");
        viewPager.setAdapter(adapter);

    }


    private void logout() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN,false);
        editor.putString(Constants.EMAIL,"");
        editor.putString(Constants.NAME,"");
        editor.putString(Constants.USERIMAGE,"");
        editor.commit();
        goToLogin();
    }
    private void goToLogin(){
        Intent i = new Intent(this,LoginFragment.class);
        startActivity(i);

    }
    public void showUp() {
        NextzyUtil.startAnimatorSet(this,tabLayout,R.animator.animator_content_show_by_slide_up,null);
        Log.d("ssss","showup");
    }
    private void prepareservice() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Log.e("retrofit2", "connected" + API);


        apiCall(retrofit);


    }

    private void apiCall(Retrofit retrofit) {
        MyApi myApi = retrofit.create(MyApi.class);
        Call<ItemModel> call = myApi.getShout();
        call.enqueue(this);
        Log.e("apiCall", "Success Call");
    }

    @Override
    public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
        itemModel = response.body();
        data = new ArrayList<>(Arrays.asList(itemModel.getProperty()));
        Log.d("KUYYYYYYYYY","Kuy"+data);
//        preparesearch(data);

    }

    @Override
    public void onFailure(Call<ItemModel> call, Throwable t) {

    }
}
