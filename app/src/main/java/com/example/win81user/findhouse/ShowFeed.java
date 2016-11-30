package com.example.win81user.findhouse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.win81user.findhouse.API.MyApi;
import com.example.win81user.findhouse.Adapter.FeedAdapter;
import com.example.win81user.findhouse.Model.ItemModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ShowFeed extends android.support.v4.app.Fragment implements Callback<ItemModel>,ClickListener {

    public final static String ITEMS_COUNT_KEY = "PartThreeFragment$ItemsCount";
    SwipeRefreshLayout swipeRefreshLayout;
    Retrofit retrofit;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private FeedAdapter dataAdapter;
    //http://192.168.25.2:8181/weerawat/ https://weerawatcomsci.github.io/feed/
    String API = "http://192.168.25.2:8181/FindHouse/webservice/";

    public static ShowFeed createInstance(int itemsCount) {
        ShowFeed partThreeFragment = new ShowFeed();
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMS_COUNT_KEY, itemsCount);
        partThreeFragment.setArguments(bundle);
        return partThreeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_show_feed, container, false);
        setupRecyclerView(recyclerView);
        recyclerView.setAdapter(dataAdapter);
        return recyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swiperefresh);
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
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(dataAdapter);


        apiCall(retrofit);
        Log.e("apicall","connected"+retrofit);
      /*  swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                apiCall(retrofit);
                Log.e("onRefresh","Success Refresh");
            }
        });*/
    }

    private void apiCall(Retrofit retrofit) {
        MyApi myApi = retrofit.create(MyApi.class);
        Call<ItemModel> call = myApi.getShout();
        call.enqueue(this);
        Log.e("apiCall","Success Call");
    }
    @Override
    public void onResponse(Call<ItemModel> call, Response<ItemModel> response) {
        ItemModel itemModel = response.body();
        FeedAdapter adapter = new FeedAdapter(itemModel);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
//        swipeRefreshLayout.setRefreshing(false);
        Log.e("OnResponse","Response");
    }

    @Override
    public void onFailure(Call<ItemModel> call, Throwable t) {
        Toast.makeText(getActivity(),"Failed !",Toast.LENGTH_LONG).show();
    }

    public void showdialog(){

    }

    @Override
    public void itemClicked(View view, int position) {

      /*  Intent intent = new Intent(ShowFeed.this, MainActivity.class);
        intent.putExtra("ItemPosition", position);

        startActivity(intent);*/
    }
}