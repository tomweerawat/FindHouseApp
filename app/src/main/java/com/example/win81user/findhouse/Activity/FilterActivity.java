package com.example.win81user.findhouse.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.win81user.findhouse.API.MyApi;
import com.example.win81user.findhouse.Adapter.FeedAdapter;
import com.example.win81user.findhouse.Adapter.FilterAdapter;
import com.example.win81user.findhouse.Model.ItemModel;
import com.example.win81user.findhouse.Model.Property;
import com.example.win81user.findhouse.R;
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
 * Created by Win81 User on 17/5/2560.
 */

public class FilterActivity extends AppCompatActivity implements Callback<ItemModel> {
    private TextView tv;
    Retrofit retrofit;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private FeedAdapter dataAdapter;
    private ArrayList<Property> data;
    private RecyclerView.Adapter mAdapter;
    Context context;
    String API = "http://www.tnfindhouse.com/service/";
    String datafilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_show_feed);
        Bundle bundle = getIntent().getExtras();
        String text = bundle.getString("filter");
        datafilter = text;
        Log.d("datafilter","datafilter"+datafilter);
        initview();
        setupRecyclerView();

    }

    private void initview() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

    }
    private void setupRecyclerView() {

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
        Log.e("retrofit2","connected"+API);
        apiCall(retrofit);
        Log.e("apicall","connected"+retrofit);

    }

    private void apiCall(Retrofit retrofit) {
        MyApi myApi = retrofit.create(MyApi.class);
        Call<ItemModel> call = myApi.getFilter(datafilter);
        Log.d("filtercall","filtercall"+call);
        call.enqueue(this);
        Log.e("apiCall","Success Call");
    }


    @Override
    public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
        ItemModel itemModel = response.body();
        data = new ArrayList<>(Arrays.asList(itemModel.getProperty()));
        FilterAdapter adapter = new FilterAdapter(context,data);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFailure(Call<ItemModel> call, Throwable t) {
        Toast.makeText(getApplicationContext(),"boom !",Toast.LENGTH_LONG).show();
    }
}
