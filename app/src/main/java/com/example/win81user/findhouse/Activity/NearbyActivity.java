package com.example.win81user.findhouse.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.win81user.findhouse.API.RetrofitMaps;
import com.example.win81user.findhouse.Adapter.NearbyAdapter;
import com.example.win81user.findhouse.Model.ItemModel;
import com.example.win81user.findhouse.Model.Property;
import com.example.win81user.findhouse.R;
import com.example.win81user.findhouse.Utility.ClickListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Win81 User on 9/3/2560.
 */

public class NearbyActivity extends AppCompatActivity
        implements Callback<ItemModel>,LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,ClickListener,View.OnClickListener {
    Retrofit retrofit;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView recyclerView;
    private NearbyAdapter dataAdapter;
    private ArrayList<Property> data;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;
    /*   private double latitude;
       private double longitude;*/
    private LocationRequest mLocationRequest;
    private Marker mCurrLocationMarker;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private int PROXIMITY_RADIUS = 1;
    private Toolbar toolbar;
    /*   double mylat = TestMap.latitude;
       double mylng = TestMap.longitude;*/
    private double latitude;
    private double longitude;
    private Button btn;

    /* String API = "http://192.168.25.2:8181/FindHouse/webservice/";*/
    String API = "http://www.tnfindhouse.com/service/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_swipe_recyleview);

        setuprecycleview();
        retrofitlog();
        buildGoogleApiClient();

//        initview();


    }

   /* private void initview() {
        toolbar = (Toolbar)findViewById(R.id.toolbarnearby);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_black_36dp);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NearbyActivity.this, ActivityDrawer.class));
            }
        });

    }*/

    private void setuprecycleview(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_nearby);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        toolbar = (Toolbar) findViewById(R.id.toolbardd1);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_black_36dp);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    private void retrofitlog() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Log.e("OkHttpClient","connected"+client);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

//        Log.e("retrofit2","connected"+API);
        apiCall(retrofit);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                apiCall(retrofit);
            }
        });

        Log.e("apicall","connected"+retrofit);

    }

    private void apiCall(Retrofit retrofit) {


        RetrofitMaps service = retrofit.create(RetrofitMaps.class);
        Call<ItemModel> call = service.getNearbyPlaces(latitude + "," +longitude, PROXIMITY_RADIUS);
        Toast.makeText(this,"apiCall !"+latitude+"\t"+longitude,Toast.LENGTH_LONG).show();
        Log.e("datatoservice","datatoservice"+latitude);
       /* Call<ItemModel> call = service.getNearbyPlaces(13.745112 + "," + 100.537323, PROXIMITY_RADIUS);*/
        call.enqueue(this);
        Log.e("apiCall","Success Calllat");
    }

    @Override
    public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
        ItemModel itemModel = response.body();
        data = new ArrayList<>(Arrays.asList(itemModel.getProperty()));
        NearbyAdapter adapter = new NearbyAdapter(context,data);
        mRecyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
       /* Log.e("data",itemModel.getProperty()+"");
        dataAdapter = new NearbyAdapter(context,data);
        recyclerView.setAdapter(dataAdapter);*/

    }

    @Override
    public void onFailure(Call<ItemModel> call, Throwable t) {
        Toast.makeText(getApplicationContext(),"boom !",Toast.LENGTH_LONG).show();
    }
    protected synchronized void buildGoogleApiClient() {
       /* Toast.makeText(this,"buildGoogleApiClient !",Toast.LENGTH_LONG).show();*/
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        Log.e("plsconnect","plsconnect"+mLocationRequest);
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
      /*  latitude = mLastLocation.getLatitude();
        longitude = mLastLocation.getLongitude();
        Log.d("LastLocation2","LastLocation2"+latitude+"/t"+longitude);
*/
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private boolean checkPermission() {
        Log.d("checkPermission", "checkPermission()");
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED );
    }
    @Override
    public void onLocationChanged(Location mylocation) {
        Log.d("onLocationChanged1", "entered");
        latitude = mylocation.getLatitude();
        longitude = mylocation.getLongitude();

    /*    mLastLocation = mylocation;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        latitude = mylocation.getLatitude();
        longitude = mylocation.getLongitude();
        LatLng latLng = new LatLng(mylocation.getLatitude(), mylocation.getLongitude());
        Log.e("Currentp","Currrentp"+latLng);


        Log.d("onLocationChanged", String.format("latitude:%.3f longitude:%.3f", latitude, longitude));

        Log.d("onLocationChanged", "Exit");*/
    }


    @Override
    public void itemClicked(View view, int position) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("ItemPosition", position);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this,"Failed !",Toast.LENGTH_LONG).show();
    }
}
